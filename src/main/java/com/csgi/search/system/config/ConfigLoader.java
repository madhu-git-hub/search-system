package com.csgi.search.system.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads application settings from properties file.
 */
public class ConfigLoader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("Configuration file not found!");
                throw new IOException("Configuration file not found");
            }
            properties.load(input);
            logger.info("Successfully loaded configuration properties.");
        } catch (IOException e) {
            logger.error("Failed to load configuration: {}", e.getMessage());
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Requested property '{}' not found in config.properties", key);
        }
        return value;
    }
}
