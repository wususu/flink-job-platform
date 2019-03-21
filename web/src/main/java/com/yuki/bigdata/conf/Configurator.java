package com.yuki.bigdata.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author janke
 * @date 2018/12/11.
 */
public class Configurator {
    public static String PUBLIC_CONFIG;

    protected static Logger logger = LoggerFactory.getLogger(Configurator.class);
    protected static ResourceBundle resourceBundle;

    static{
        load();
    }

    public static void load() {
        String env = System.getProperty("DWENV");
        if ("prod".equals(env)) {
            PUBLIC_CONFIG = "app-" + env + "";
        } else {
            PUBLIC_CONFIG = "app-" + "test" + "";
        }
        try {
            InputStreamReader is;
            // PRIVATE
            resourceBundle = ResourceBundle.getBundle(PUBLIC_CONFIG);
        } catch (Exception ex) {
            throw (new RuntimeException(ex.getMessage(), ex));
        }
    }

    public static String get(String key) {
        return resourceBundle.getString(key);
    }

    public static Integer getInt(String key) {
        String value = get(key);
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            logger.warn(e.toString());
            return null;
        }
    }

    public static Long getLong(String key) {
        String value = get(key);
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            logger.warn(e.toString());
            return null;
        }
    }

    public static Boolean getBoolean(String key) {
        String value = get(key);
        try {
            return Boolean.valueOf(value);
        } catch (Exception e) {
            logger.warn(e.toString());
            return null;
        }
    }

}
