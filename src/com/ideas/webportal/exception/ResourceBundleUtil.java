package com.ideas.webportal.exception;

import java.util.Enumeration;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author shyam paril
 *
 */

public final class ResourceBundleUtil {
    
    private static final Log LOG = LogFactory.getLog(ResourceBundleUtil.class.getName());

    private ResourceBundleUtil() {
    }
    
    private static ResourceBundle getResourceBundle(String baseName, Locale locale) {

        Locale temp = (locale == null) ? Locale.getDefault() : locale;

        ResourceBundle resource = ResourceBundle.getBundle(baseName, temp);

        return resource;
    }

    /**
     * @param code -
     *            code for which the message should be returned.
     * @param baseName -
     *            Base resource name from which the messages should be retrieved.
     * @param locale .
     * @return - Message for the code
     */
    public static String getMessage(String code, String baseName, Locale locale) {

        String message = null;

        try {
            ResourceBundle resource = getResourceBundle(baseName, locale);
            message = resource.getString(code);
        } catch (Exception ex) {
            LOG.info("Error while reading property for code [" + code + "] returning null");
            return message;
        }

        return message;
    }

    /**
     * Returns all the keys present in the property file.
     * @param baseName -
     *            Base resource name from which the messages should be retrieved.
     * @param locale .
     * @return - Keys in the resource.
     */
    public static Enumeration<String> getKeys(String baseName, Locale locale) {

        Enumeration<String> keys = null;

        try {
            ResourceBundle resource = getResourceBundle(baseName, locale);
            keys = resource.getKeys();
        } catch (Exception ex) {
            return keys;
        }
        return keys;
    }
}
