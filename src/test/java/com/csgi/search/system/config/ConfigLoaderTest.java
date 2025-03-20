package com.csgi.search.system.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.Test;

class ConfigLoaderTest {
	private static final String WORD_STARTS_WITH = "rule.word.startsWith";
	private static final String WORD_MIN_LENGTH = "rule.word.minLength";
	private static final String WORD_END_WITH = "rule.word.endsWith";


    @Test
    void when_PropertyExists_then_ReturnsCorrectValue() {
        assertEquals("M", ConfigLoader.getProperty(WORD_STARTS_WITH), "Property 'rule.word.startsWith' should return 'M'");
        assertEquals("5", ConfigLoader.getProperty(WORD_MIN_LENGTH), "Property 'rule.word.minLength' should return '5'");
        assertEquals("E", ConfigLoader.getProperty(WORD_END_WITH), "Property 'rule.word.endsWith' should return 'E'");
    }

    @Test
    void when_PropertyDoesNotExist_then_ReturnsNull() {
        assertNull(ConfigLoader.getProperty("unknown.property"), "Unknown property should return null");
    }

    @Test
    void when_ConfigFileLoads_then_PropertiesAreAvailable() {
        Properties testProperties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            assertNotNull(input, "config.properties file should be found in test resources");
            testProperties.load(input);
        } catch (IOException e) {
            fail("Failed to load config.properties: " + e.getMessage());
        }

        assertEquals("M", testProperties.getProperty(WORD_STARTS_WITH));
        assertEquals("5", testProperties.getProperty(WORD_MIN_LENGTH));
        assertEquals("E", testProperties.getProperty(WORD_END_WITH));
    }
}