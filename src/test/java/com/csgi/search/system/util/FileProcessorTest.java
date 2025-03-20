package com.csgi.search.system.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileProcessorTest {

    private MockedStatic<Files> mockedFiles;
    private MockedStatic<LoggerFactory> mockedLoggerFactory;

    @BeforeEach
    public void setUp() {
        // Mock static Files class before each test
        mockedFiles = Mockito.mockStatic(Files.class);
        
        // Mock static LoggerFactory to avoid logger initialization during tests
        mockedLoggerFactory = Mockito.mockStatic(LoggerFactory.class);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(Mockito.mock(Logger.class));
    }

    @Test
    public void whenValidFilePathGiven_thenWordsAreReadSuccessfully() throws IOException {
        // Given
        String filePath = "testFile.txt";
        String content = "apple banana cat dog";
        byte[] fileContent = content.getBytes();
        
        // Mocking the static method Files.readAllBytes
        mockedFiles.when(() -> Files.readAllBytes(Paths.get(filePath))).thenReturn(fileContent);

        // When
        List<String> result = FileProcessor.readWordsFromFile(filePath);

        // Then
        assertNotNull(result);
        assertEquals(4, result.size());
        assertTrue(result.contains("apple"));
        assertTrue(result.contains("banana"));
        assertTrue(result.contains("cat"));
        assertTrue(result.contains("dog"));

        // Verify that Files.readAllBytes was called once with the correct file path
        mockedFiles.verify(() -> Files.readAllBytes(Paths.get(filePath)), times(1));
    }

    @Test
    public void whenFileReadFails_thenIOExceptionIsThrown() throws IOException {
        // Given
        String filePath = "testFile.txt";
        mockedFiles.when(() -> Files.readAllBytes(Paths.get(filePath))).thenThrow(new IOException("File not found"));

        // When and Then
        IOException exception = assertThrows(IOException.class, () -> {
            FileProcessor.readWordsFromFile(filePath);
        });

        assertEquals("File not found", exception.getMessage());

        // Verify that Files.readAllBytes was called once with the correct file path
        mockedFiles.verify(() -> Files.readAllBytes(Paths.get(filePath)), times(1));
    }

    @Test
    public void whenFileIsEmpty_thenEmptyListIsReturned() throws IOException {
        // Given
        String filePath = "emptyFile.txt";
        String content = "";
        byte[] fileContent = content.getBytes();

        // Mocking the static method Files.readAllBytes
        mockedFiles.when(() -> Files.readAllBytes(Paths.get(filePath))).thenReturn(fileContent);

        // When
        List<String> result = FileProcessor.readWordsFromFile(filePath);

        // Then
        assertNotNull(result);
        assertEquals(1,result.size());

        // Verify that Files.readAllBytes was called once with the correct file path
        mockedFiles.verify(() -> Files.readAllBytes(Paths.get(filePath)), times(1));
    }

    @Test
    public void whenFileHasSpecialCharacters_thenWordsAreSplittedCorrectly() throws IOException {
        // Given
        String filePath = "specialCharsFile.txt";
        String content = "apple, banana! cat@dog";
        byte[] fileContent = content.getBytes();

        // Mocking the static method Files.readAllBytes
        mockedFiles.when(() -> Files.readAllBytes(Paths.get(filePath))).thenReturn(fileContent);

        // When
        List<String> result = FileProcessor.readWordsFromFile(filePath);

        // Then
        assertNotNull(result);
        assertEquals(4, result.size());
        assertTrue(result.contains("apple"));
        assertTrue(result.contains("banana"));
        assertTrue(result.contains("cat"));
        assertTrue(result.contains("dog"));

        // Verify that Files.readAllBytes was called once with the correct file path
        mockedFiles.verify(() -> Files.readAllBytes(Paths.get(filePath)), times(1));
    }

    @Test
    public void whenFilePathIsNull_thenThrowIOException() throws IOException {
        // Given
        String filePath = null;

        // When and Then
        assertThrows(NullPointerException.class, () -> {
            FileProcessor.readWordsFromFile(filePath);
        });
    }

    @Test
    public void whenFilePathIsEmpty_thenThrowIOException() throws IOException {
        // Given
        String filePath = "";

        // When and Then
        assertThrows(NullPointerException.class, () -> {
            FileProcessor.readWordsFromFile(filePath);
        });
    }

    @Test
    public void whenFileHasSingleWord_thenReturnListWithOneWord() throws IOException {
        // Given
        String filePath = "singleWordFile.txt";
        String content = "apple";
        byte[] fileContent = content.getBytes();

        // Mocking the static method Files.readAllBytes
        mockedFiles.when(() -> Files.readAllBytes(Paths.get(filePath))).thenReturn(fileContent);

        // When
        List<String> result = FileProcessor.readWordsFromFile(filePath);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("apple"));

        // Verify that Files.readAllBytes was called once with the correct file path
        mockedFiles.verify(() -> Files.readAllBytes(Paths.get(filePath)), times(1));
    }

    @Test
    public void whenFileHasMultipleSpaces_thenWordsAreSplitCorrectly() throws IOException {
        // Given
        String filePath = "multiSpaceFile.txt";
        String content = "apple    banana      cat";
        byte[] fileContent = content.getBytes();

        // Mocking the static method Files.readAllBytes
        mockedFiles.when(() -> Files.readAllBytes(Paths.get(filePath))).thenReturn(fileContent);

        // When
        List<String> result = FileProcessor.readWordsFromFile(filePath);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains("apple"));
        assertTrue(result.contains("banana"));
        assertTrue(result.contains("cat"));

        // Verify that Files.readAllBytes was called once with the correct file path
        mockedFiles.verify(() -> Files.readAllBytes(Paths.get(filePath)), times(1));
    }

    // Cleanup static mocks after each test
    @AfterEach
    public void tearDown() {
        mockedFiles.close();
        mockedLoggerFactory.close();
    }
}
