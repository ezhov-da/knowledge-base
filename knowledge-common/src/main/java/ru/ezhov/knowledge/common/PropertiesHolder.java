package ru.ezhov.knowledge.common;

import ru.ezhov.propertiesReader.Properties;
import ru.ezhov.propertiesReader.PropertiesFactory;

public class PropertiesHolder {
    public static final String NAME_PROPERTIES_FILE = "knowledge-service.properties";
    private Properties<String, String> properties;

    public String getToken() {
        initPropertiesHolder();
        return properties.getProperty("git.token");
    }

    public String getUser() {
        initPropertiesHolder();
        return properties.getProperty("git.user");
    }

    public long getTimeoutUpdate() {
        initPropertiesHolder();
        return Long.parseLong(properties.getProperty("git.update.milliseconds"));
    }

    private void initPropertiesHolder() {
        if (properties == null) {
            properties = PropertiesFactory.getPropertiesFromUserDirectory(NAME_PROPERTIES_FILE);
        }
    }

    public int getPort() {
        initPropertiesHolder();
        return Integer.parseInt(properties.getProperty("port"));
    }

    public boolean isHttpsEnable() {
        initPropertiesHolder();
        return Boolean.parseBoolean(properties.getProperty("https.enable"));
    }

    public String getHttpsKeyStorePath() {
        initPropertiesHolder();
        return properties.getProperty("https.keyStorePath");
    }

    public String getHttpKeyStorePassword() {
        initPropertiesHolder();
        return properties.getProperty("https.keyStorePassword");
    }

    public String getHost() {
        initPropertiesHolder();
        return properties.getProperty("host");
    }

    public String getPassword() {
        initPropertiesHolder();
        return properties.getProperty("password");
    }

    public String getURL() {
        initPropertiesHolder();
        return getHost() + ":" + getPort();
    }
}
