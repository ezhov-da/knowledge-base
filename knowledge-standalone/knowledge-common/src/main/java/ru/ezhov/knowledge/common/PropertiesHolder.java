package ru.ezhov.knowledge.common;

import java.io.FileReader;
import java.util.Properties;

public class PropertiesHolder {
    public static final String NAME_PROPERTIES_FILE = "knowledge-service.properties";
    public static PropertiesHolder propertiesHolder;
    private static Properties properties;

    private PropertiesHolder() {
    }

    public static String getToken() {
        return properties.getProperty("git.token");
    }

    public static String getUser() {
        return properties.getProperty("git.user");
    }

    public static long getTimeoutUpdate() {
        return Long.parseLong(properties.getProperty("git.update.milliseconds"));
    }

    public static void initPropertiesHolder() throws Exception {
        if (propertiesHolder == null) {
            properties = new Properties();
            try (FileReader fileReader =
                         new FileReader(System.getProperty("user.home") + "/" + NAME_PROPERTIES_FILE)) {
                properties.load(fileReader);
                propertiesHolder = new PropertiesHolder();
            }
        }
    }

    public static int getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }

    public static boolean isHttpsEnable() {
        return Boolean.parseBoolean(properties.getProperty("https.enable"));
    }

    public static String getHttpsKeyStorePath() {
        return properties.getProperty("https.keyStorePath");
    }

    public static String getHttpKeyStorePassword() {
        return properties.getProperty("https.keyStorePassword");
    }

    public static String getHost() {
        return properties.getProperty("host");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }

    public static String getURL() {
        return getHost() + ":" + getPort();
    }
}
