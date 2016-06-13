package de.hrw.java.src;

import org.apache.commons.codec.binary.Base64;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by bayer on 17.07.2015.
 */
public abstract class RESTConnection {

    public static final String HTTP_METHOD_PUT = "PUT";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HTTP_HEADER_ACCEPT = "Accept";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_ATOM_XML = "application/atom+xml";
    public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    public static final String METADATA = "$metadata";
    public static final String FILTER = "$filter";
    public static final String INDEX = "/index.jsp";
    public static final String SEPARATOR = "/";
    public static final boolean PRINT_RAW_CONTENT = true;
    private static final String HTTP_METHOD_DELETE = "DELETE";
    private static final String LOGGER_PREFIX = "de.sbpi.rest";

    protected static Logger logger = Logger.getLogger(LOGGER_PREFIX);
    protected String serviceUrl;
    protected URL serviceURL;
    protected Edm edm;
    protected String user, password;

    public RESTConnection(String serviceUrl) throws IOException, ODataException {
        serviceURL = new URL(serviceUrl);
        edm = null;
        this.serviceUrl = serviceUrl;
    }

    // -- public -------------------------------------------------------------------------------------------------------

    private static void print(String content) {
        System.out.println(content);
    }

    public Edm readEdm() throws IOException, ODataException {
        if (this.edm == null) {
            InputStream content = execute(serviceUrl + SEPARATOR + METADATA, APPLICATION_XML, HTTP_METHOD_GET);
            this.edm = EntityProvider.readMetadata(content, false);
        }
        return this.edm;
    }

    public ODataFeed readFeed(String contentType, String entitySetName, String filter) throws IOException, ODataException {
        EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
        String absolutUri = createUri(serviceUrl, entitySetName, null);

        String relativeUri = absolutUri + filter;

        InputStream content = execute(relativeUri, contentType, HTTP_METHOD_GET);
        return EntityProvider.readFeed(contentType, entityContainer.getEntitySet(entitySetName), content, EntityProviderReadProperties.init().build());
    }

    public ODataEntry readEntry(String contentType, String entitySetName, String keyValue) throws IOException, ODataException {
        return readEntry(contentType, entitySetName, keyValue, null);
    }

    public ODataEntry readEntry(String contentType, String entitySetName, String keyValue, String expandRelationName) throws IOException, ODataException {
        // working with the default entity container
        EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
        // create absolute uri based on service uri, entity set name with its key property value and optional expanded relation name
        String absolutUri = createUri(serviceUrl, entitySetName, keyValue, expandRelationName);
        InputStream content = execute(absolutUri, contentType, HTTP_METHOD_GET);

        return EntityProvider.readEntry(contentType, entityContainer.getEntitySet(entitySetName), content, EntityProviderReadProperties.init().build());
    }

    public ODataEntry createEntry(String contentType,
                                  String entitySetName, Map<String, Object> data) throws EdmException, URISyntaxException, EntityProviderException, IOException {
        String absolutUri = createUri(serviceUrl, entitySetName, null);
        return writeEntity(edm, absolutUri, entitySetName, data, contentType, HTTP_METHOD_POST);
    }

    public ODataEntry writeEntry(String contentType, String entitySetName, ODataEntry entry) throws EdmException, URISyntaxException, EntityProviderException, IOException {
        return createEntry(contentType, entitySetName, entry.getProperties());
    }

    // -- internal------------------------------------------------------------------------------------------------------

    public Edm getEdm() {
        return edm;
    }

    private InputStream execute(String relativeUri, String contentType, String httpMethod) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);

        logger.info(relativeUri);

        switch (httpMethod) {
            case HTTP_METHOD_GET:
                prepareGET(connection);
                break;
            case HTTP_METHOD_POST:
                preparePOST(connection);
                break;
            case HTTP_METHOD_PUT:
                preparePUT(connection);
                break;
            case HTTP_METHOD_DELETE:
                prepareDELETE(connection);
                break;
            default:
                prepareDefault(connection);
                break;
        }
        connection.connect();
        checkStatus(connection);

        System.out.println("Token " + connection.getHeaderField("X-CSRF-Token"));
        InputStream content = connection.getInputStream();
        content = logRawContent(httpMethod + " request on uri '" + relativeUri + "' with content:\n  ", content, "\n");
        return content;
    }

    protected abstract void prepareGET(HttpURLConnection connection);

    protected abstract void postProcessGET(HttpURLConnection connection);

    protected abstract void preparePOST(HttpURLConnection connection);

    protected abstract void preparePUT(HttpURLConnection connection);

    protected abstract void prepareDELETE(HttpURLConnection connection);

    protected abstract void prepareDefault(HttpURLConnection connection);

    private HttpURLConnection connect(String relativeUri, String contentType, String httpMethod) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);

        connection.connect();
        checkStatus(connection);

        return connection;
    }

    private HttpURLConnection initializeConnection(String absolutUri, String contentType, String httpMethod) throws IOException {
        URL url = new URL(absolutUri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String authorization = "Basic ";
        authorization += new String(Base64.encodeBase64((this.user + ":" + this.password).getBytes()));
        connection.setRequestProperty("Authorization", authorization);

        connection.setRequestMethod(httpMethod);
        connection.setRequestProperty(HTTP_HEADER_ACCEPT, contentType);
        if (HTTP_METHOD_POST.equals(httpMethod) || HTTP_METHOD_PUT.equals(httpMethod)) {
            connection.setDoOutput(true);
            connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, contentType);
        }
        return connection;
    }

    private HttpStatusCodes checkStatus(HttpURLConnection connection) throws IOException {
        HttpStatusCodes httpStatusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
        if (400 <= httpStatusCode.getStatusCode() && httpStatusCode.getStatusCode() <= 599) {
            throw new RuntimeException("Http Connection failed with status " + httpStatusCode.getStatusCode() + " " + httpStatusCode.toString());
        }
        return httpStatusCode;
    }

    private String createUri(String serviceUri, String entitySetName, String id) {
        return createUri(serviceUri, entitySetName, id, null);
    }

    private String createUri(String serviceUri, String entitySetName, String id, String expand) {
        final StringBuilder absolutUri = new StringBuilder(serviceUri).append(SEPARATOR).append(entitySetName);
        if (id != null) {
            absolutUri.append("(").append(id).append(")");
        }
        if (expand != null) {
            absolutUri.append("/?$expand=").append(expand);
        }
        return absolutUri.toString();
    }

    private InputStream logRawContent(String prefix, InputStream content, String postfix) throws IOException {
        if (PRINT_RAW_CONTENT) {
            byte[] buffer = streamToArray(content);
            print(prefix + new String(buffer) + postfix);
            return new ByteArrayInputStream(buffer);
        }
        return content;
    }

    private byte[] streamToArray(InputStream stream) throws IOException {
        byte[] result = new byte[0];
        byte[] tmp = new byte[8192];
        int readCount = stream.read(tmp);
        while (readCount >= 0) {
            byte[] innerTmp = new byte[result.length + readCount];
            System.arraycopy(result, 0, innerTmp, 0, result.length);
            System.arraycopy(tmp, 0, innerTmp, result.length, readCount);
            result = innerTmp;
            readCount = stream.read(tmp);
        }
        stream.close();
        return result;
    }

    /**
     * Check COde
     * ToDo: Check code and integrate it!
     */



    public void updateEntry(Edm edm, String serviceUri, String contentType, String entitySetName,
                            String id, Map<String, Object> data) throws Exception {
        String absolutUri = createUri(serviceUri, entitySetName, id);
        writeEntity(edm, absolutUri, entitySetName, data, contentType, HTTP_METHOD_PUT);
    }

    public HttpStatusCodes deleteEntry(String serviceUri, String entityName, String id) throws IOException {
        String absolutUri = createUri(serviceUri, entityName, id);
        HttpURLConnection connection = connect(absolutUri, APPLICATION_XML, HTTP_METHOD_DELETE);
        return HttpStatusCodes.fromStatusCode(connection.getResponseCode());
    }

    private ODataEntry writeEntity(Edm edm, String absolutUri, String entitySetName,
                                   Map<String, Object> data, String contentType, String httpMethod)
            throws EdmException, MalformedURLException, IOException, EntityProviderException, URISyntaxException {

        HttpURLConnection connection = initializeConnection(absolutUri, contentType, httpMethod);

        // prepare method for sublcasses (e.g. CSRF-Token)
        preparePOST(connection);

        EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();

        EdmEntitySet entitySet = entityContainer.getEntitySet(entitySetName);
        URI rootUri = new URI(entitySetName);

        EntityProviderWriteProperties properties = EntityProviderWriteProperties.serviceRoot(rootUri).build();
        // serialize data into ODataResponse object
        ODataResponse response = EntityProvider.writeEntry(contentType, entitySet, data, properties);
        // get (http) entity which is for default Olingo implementation an InputStream
        Object entity = response.getEntity();
        if (entity instanceof InputStream) {
            byte[] buffer = streamToArray((InputStream) entity);
            // just for logging
            String content = new String(buffer);
            print(httpMethod + " request on uri '" + absolutUri + "' with content:\n  " + content + "\n");
            //
            connection.getOutputStream().write(buffer);
        }
        // if a entity is created (via POST request) the response body contains the new created entity
        ODataEntry entry = null;
        HttpStatusCodes statusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
        if (statusCode == HttpStatusCodes.CREATED) {
            // get the content as InputStream and de-serialize it into an ODataEntry object
            InputStream content = connection.getInputStream();
            content = logRawContent(httpMethod + " request on uri '" + absolutUri + "' with content:\n  ", content, "\n");
            entry = EntityProvider.readEntry(contentType,
                    entitySet, content, EntityProviderReadProperties.init().build());
        }

        //
        connection.disconnect();

        return entry;
    }
}
