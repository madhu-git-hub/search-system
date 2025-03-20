package com.csgi.search.system.indexer;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csgi.search.system.model.Result;
import com.csgi.search.system.util.FileProcessor;



/**
 * Main application that processes a text file based on business rules.
 */
public class WordIndexer {
    private static final Logger logger = LoggerFactory.getLogger(WordIndexer.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            logger.error("Invalid arguments. Usage: java -jar search-system-0.0.1-SNAPSHOT.jar <file-path>");
            System.exit(1);
        }

        String filePath = args[0];
    	//String filePath = "D:\\STS Workspace\\search-system\\src\\main\\resources\\sample.txt";
        logger.info("Starting word processing for file: {}", filePath);

        try {
            List<String> words = FileProcessor.readWordsFromFile(filePath);

            WordAnalyzer analyzer = new WordAnalyzer();
            Result result = analyzer.process(words);

            logger.info("Count of words starting with M/m: {}", result.getMWordCount());
            logger.info("Words longer than 5 characters: {}", result.getLongWords());
            logger.info("Count of words ending with E/e: {}", result.getSuffixWordCount());

        } catch (IOException e) {
            logger.error("Error reading file: {}", e.getMessage());
        }
    }
}
