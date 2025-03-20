package com.csgi.search.system.indexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.csgi.search.system.config.ConfigLoader;
import com.csgi.search.system.model.Result;

public class WordAnalyzerTest {

    private WordAnalyzer wordAnalyzer;

    @BeforeEach
    public void setUp() {
        // Mock static methods using MockedStatic with the when_then naming convention
        try (MockedStatic<ConfigLoader> mockedStatic = Mockito.mockStatic(ConfigLoader.class)) {
            // Mock the static method ConfigLoader.getProperty
            mockedStatic.when(() -> ConfigLoader.getProperty("rule.word.startsWith")).thenReturn("m");
            mockedStatic.when(() -> ConfigLoader.getProperty("rule.word.minLength")).thenReturn("5");
            mockedStatic.when(() -> ConfigLoader.getProperty("rule.word.endsWith")).thenReturn("E");

            // Initialize WordAnalyzer instance with mocked static methods
            wordAnalyzer = new WordAnalyzer();
        }
    }

    @Test
    public void whenProcessWithValidWords_thenReturnCorrectCounts() {
        List<String> words = Arrays.asList("mouse", "apple", "mat", "pen", "elephant");

        Result result = wordAnalyzer.process(words);

        // Test the count of words starting with 'm'
        assertEquals(2, result.getMWordCount());

        // Test the list of words longer than 5 characters
        assertEquals(1, result.getLongWords().size());
        assertEquals("elephant", result.getLongWords().get(0));

        // Test the count of words ending with 'e'
        assertEquals(2, result.getSuffixWordCount());
    }

    @Test
    public void whenCountWordsStartingWith_thenReturnCorrectCount() {
        List<String> words = Arrays.asList("mouse", "mat", "map", "pen");

        int count = wordAnalyzer.countWordsStartingWith(words);

        // Words starting with 'm' are "mouse", "mat", and "map"
        assertEquals(3, count);
    }

    @Test
    public void whenCountWordsEndingWith_thenReturnCorrectCount() {
        List<String> words = Arrays.asList("apple", "orange", "elephant", "pen");

        int count = wordAnalyzer.countWordsEndingWith(words);

        // Words ending with 'e' are "apple", "orange"
        assertEquals(2, count);
    }

    @Test
    public void whenGetWordsLongerThan_thenReturnCorrectWords() {
        List<String> words = Arrays.asList("apple", "mouse", "pen", "elephant");

        List<String> result = wordAnalyzer.getWordsLongerThan(words);

        // Words longer than 5 characters are "elephant"
        assertEquals(1, result.size());
        assertEquals("elephant", result.get(0));
    }

    @Test
    public void whenProcessWithEmptyWordList_thenReturnEmptyResult() {
        List<String> words = Arrays.asList();

        Result result = wordAnalyzer.process(words);

        // With an empty list, all counts should be 0
        assertEquals(0, result.getMWordCount());
        assertEquals(0, result.getSuffixWordCount());
        assertEquals(0, result.getLongWords().size());
    }
}
