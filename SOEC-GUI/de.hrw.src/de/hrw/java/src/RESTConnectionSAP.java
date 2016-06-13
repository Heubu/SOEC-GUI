package de.hrw.java.src;

import org.apache.olingo.odata2.api.exception.ODataException;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by bayer on 15.07.2015.
 */
public class RESTConnectionSAP extends RESTConnection {

    public static final String X_CSRF_TOKEN = "X-CSRF-Token";
    protected String CSRF_Token;

    public RESTConnectionSAP(String user, String password, String serviceUrl) throws IOException, ODataException {
        super(serviceUrl);
        this.user = user;
        this.password = password;
        readEdm();
    }

    @Override
    protected void prepareGET(HttpURLConnection connection) {
        connection.setRequestProperty(X_CSRF_TOKEN, "Fetch");
    }

    @Override
    protected void postProcessGET(HttpURLConnection connection) {
        CSRF_Token = connection.getHeaderField(X_CSRF_TOKEN);
    }

    @Override
    protected void preparePOST(HttpURLConnection connection) {
        connection.setRequestProperty(X_CSRF_TOKEN, CSRF_Token);
    }

    @Override
    protected void preparePUT(HttpURLConnection connection) {
        connection.setRequestProperty(X_CSRF_TOKEN, CSRF_Token);
    }

    @Override
    protected void prepareDELETE(HttpURLConnection connection) {
        connection.setRequestProperty(X_CSRF_TOKEN, CSRF_Token);
    }

    @Override
    protected void prepareDefault(HttpURLConnection connection) {

    }

}
