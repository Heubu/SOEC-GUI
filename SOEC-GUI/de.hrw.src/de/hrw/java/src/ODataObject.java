package de.hrw.java.src;

import java.util.List;
import java.util.Map;

/**
 * Created by bayer on 25.01.2016.
 */
public abstract class ODataObject {

    // constants
    protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd hh:mm:ss";
    protected static final String DATE_FORMAT = "yyyy-MM-dd";

    protected String className;
    // subclass shall provide keyAttributes for validation
    protected List<String> keyAttributes;
    // signals that instance contains a valid set of values (i.e., key attributes are provided)
    protected boolean isValid;
    protected Class<?> aClass; // must be set in concrete constructor

    public ODataObject(List<String> keyAttributes) {
        this.keyAttributes = keyAttributes;
    }

    public abstract boolean validate();

    public static void noMethod() throws UnsupportedOperationException {
        System.out.println("noMethod called ");
        throw new UnsupportedOperationException("No Method found");
    }
    public List<String> getKeyAttributes() {
        return keyAttributes;
    }

    public abstract Map<String, Object> toMap();
}