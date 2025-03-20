package com.csgi.search.system.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to read words from a file.
 */
public class FileProcessor {
    private static final Logger logger = LoggerFactory.getLogger(FileProcessor.class);

    public static List<String> readWordsFromFile(String filePath) throws IOException {
        logger.info("Attempting to read file: {}", filePath);
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            List<String> words = Arrays.asList(content.split("\\W+"));
            logger.info("Successfully read {} words from file: {}", words.size(), filePath);
            return words;
        } catch (IOException e) {
            logger.error("Error reading file '{}': {}", filePath, e.getMessage());
            throw e;
        }
    }
}
