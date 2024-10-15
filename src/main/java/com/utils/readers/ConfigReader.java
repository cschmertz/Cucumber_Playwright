package com.utils.readers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;
    private final String propertyFilePath = "configs/Configuration.properties";

    public ConfigReader() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(propertyFilePath)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath, e);
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property " + key + " not specified in the configuration.properties file");
        }
        return value;
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public String getBrowser() {
        return properties.getProperty("browser");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless"));
    }
}
