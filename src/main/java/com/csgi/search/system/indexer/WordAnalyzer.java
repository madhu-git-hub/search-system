package com.csgi.search.system.indexer;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csgi.search.system.config.ConfigLoader;
import com.csgi.search.system.model.Result;

/**
 * Analyze the file based on the rule.
 */
public class WordAnalyzer implements RuleProcessor {
	private static final Logger logger = LoggerFactory.getLogger(WordAnalyzer.class);

	private String startsWith;
	private String endsWith;
	private int minLength = 0;

	public WordAnalyzer() {
		try {
			this.startsWith = ConfigLoader.getProperty("rule.word.startsWith").toLowerCase();
			this.minLength = Integer.parseInt(ConfigLoader.getProperty("rule.word.minLength"));
			this.endsWith = ConfigLoader.getProperty("rule.word.endsWith").toLowerCase();
			logger.info("WordAnalyzer initialized with startsWith: {} and minLength: {} and endsWith {}", startsWith, minLength, endsWith);
		} catch (NumberFormatException e) {
			logger.error("Error loading configuration: {}", e.getMessage());
		}
	}

	@Override
	public Result process(List<String> words) {

		int countWordsStartingWithM = 0;
		int countWordsEndingWithE = 0;
		List<String> wordsLongerThanFive = null;
		long start = System.currentTimeMillis();
		logger.info("Starting process method with input: {}", start);
		try {
			logger.info("Processing {} words...", words.size());

			countWordsStartingWithM = countWordsStartingWith(words);
			logger.info("Count of words starting with '{}': {}", startsWith, countWordsStartingWithM);

			wordsLongerThanFive = getWordsLongerThan(words);
			logger.info("Found {} words longer than {} characters", wordsLongerThanFive.size(), minLength);

			countWordsEndingWithE = countWordsEndingWith(words);
			logger.info("Count of words ending with '{}': {}", endsWith, countWordsEndingWithE);
		} catch (Exception e) {
			logger.error("Exception while processing: {}", e.getMessage());
			throw e;
		}
		logger.info("Completed process method with input: {}", System.currentTimeMillis()-start);
		return new Result(countWordsStartingWithM, wordsLongerThanFive, countWordsEndingWithE);
	}

	/**
	 * Counts words starting with a specific prefix.
	 *
	 * @param words List of words to process.
	 * @return The count of words starting with the prefix.
	 */
	public int countWordsStartingWith(List<String> words) {
		return (int) words.stream().filter(word -> word.toLowerCase().startsWith(startsWith)).count();
	}

	/**
	 * Counts words ending with a specific suffix.
	 *
	 * @param words List of words to process.
	 * @return The count of words ending with the suffix.
	 */
	public int countWordsEndingWith(List<String> words) {
		return (int) words.stream().filter(word -> word.toLowerCase().endsWith(endsWith)).count();
	}

	/**
	 * Gets words longer than a specified length.
	 *
	 * @param words List of words to process.
	 * @return A list of words longer than the specified length.
	 */
	public List<String> getWordsLongerThan(List<String> words) {
		return words.stream().filter(word -> word.length() > minLength).collect(Collectors.toList());
	}

}