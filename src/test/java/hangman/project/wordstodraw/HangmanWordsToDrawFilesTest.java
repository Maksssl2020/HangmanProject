package hangman.project.wordstodraw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HangmanWordsToDrawFilesTest {

    private HangmanWordsToDrawFiles textFiles;
    private Map<String, Float> easyWords;
    private Map<String, Float> mediumWords;
    private Map<String, Float> hardWords;

    @BeforeEach
    void setUp() {
        textFiles = new HangmanWordsToDrawFiles();
        easyWords = textFiles.getWordsForGame("easyWords.txt");
        mediumWords = textFiles.getWordsForGame("mediumWords.txt");
        hardWords = textFiles.getWordsForGame("hardWords.txt");
    }

    @Test
    void testIsFileEasyWordsAvailable() {
        assertTrue(textFiles.isFileAvailable("easyWords.txt"));
    }

    @Test
    void testIsFileMediumWordsAvailable() {
        assertTrue(textFiles.isFileAvailable("mediumWords.txt"));
    }

    @Test
    void testIsFileHardWordsAvailable() {
        assertTrue(textFiles.isFileAvailable("hardWords.txt"));
    }

    @Test
    void testIsFileSimpleWordsAvailable() {
        assertFalse(textFiles.isFileAvailable("simpleWords.txt"));
    }

    @Test
    void testRemovingExistingEasyWordsFile() {
        String result = textFiles.removeFile("easyWords.txt");
        assertEquals("File easyWords.txt has been deleted!", result);
    }

    @Test
    void testRemovingExistingMediumWordsFile() {
        String result = textFiles.removeFile("mediumWords.txt");
        assertEquals("File mediumWords.txt has been deleted!", result);
    }

    @Test
    void testRemovingExistingHardWordsFile() {
        String result = textFiles.removeFile("hardWords.txt");
        assertEquals("File hardWords.txt has been deleted!", result);
    }

    @Test
    void testRemovingFileThatDoesNotExist() {
        String result = textFiles.removeFile("file.txt");
        assertEquals("File file.txt doesn't exist!", result);
    }

    @Test
    void testCreatingFileWithEasyWords() {
        textFiles.removeFile("easyWords.txt");
        textFiles.createWordsFiles("easyWords.txt");
        assertTrue(textFiles.isFileAvailable("easyWords.txt"));
    }

    @Test
    void testCreatingFileWithMediumWords() {
        textFiles.removeFile("mediumWords.txt");
        textFiles.createWordsFiles("mediumWords.txt");
        assertTrue(textFiles.isFileAvailable("mediumWords.txt"));
    }

    @Test
    void testCreatingFileUnknownFile() {
        textFiles.removeFile("unknownFile.txt");
        textFiles.createWordsFiles("unknownFile.txt");
        assertTrue(textFiles.isFileAvailable("unknownFile.txt"));
    }

    @Test
    void testCreatingFileWithHardWords() {
        textFiles.removeFile("hardWords.txt");
        textFiles.createWordsFiles("hardWords.txt");
        assertTrue(textFiles.isFileAvailable("hardWords.txt"));
    }

    @Test
    void testGettingEasyWordsFromFile() {
        Map<String, Float> easyWordsResult = textFiles.getWordsForGame("easyWords.txt");
        assertEquals(Set.of("cat", "dog", "city", "flower"), easyWordsResult.keySet());
    }

    @Test
    void testGettingMediumWordsFromFile() {
        Map<String, Float> mediumWordsResult = textFiles.getWordsForGame("mediumWords.txt");
        assertEquals(Set.of("building", "computer", "reflector", "difficulty"), mediumWordsResult.keySet());
    }

    @Test
    void testGettingHardWordsFromFile() {
        Map<String, Float> hardWordsResult = textFiles.getWordsForGame("hardWords.txt");
        assertEquals(Set.of("frizzled", "thumbscrew", "syndrome", "microwave"), hardWordsResult.keySet());
    }

    @Test
    void testGettingUnknownWordsFile() {
        Map<String, Float> unknownFileResult = textFiles.getWordsForGame("unknownFile.txt");
        assertEquals("[UnknownFile!]", unknownFileResult.keySet().toString());
    }

    @Test
    void testRestoringDefaultWordsToFileWithEasyWords() {
        String resultMessage = textFiles.restoreDefaultTextFileWords("easyWords.txt");
        Set<String> resultWords = easyWords.keySet();
        Set<String> expectedResult = Set.of("cat", "dog", "city", "flower");

        assertEquals(expectedResult, resultWords);
        assertEquals("Default data in easyWords.txt has been restored!", resultMessage);
    }

    @Test
    void testRestoringDefaultWordsToFileWithMediumWords() {
        String resultMessage = textFiles.restoreDefaultTextFileWords("mediumWords.txt");
        Set<String> resultWords = mediumWords.keySet();
        Set<String> expectedResult = Set.of("building", "computer", "reflector", "difficulty");

        assertEquals(expectedResult, resultWords);
        assertEquals("Default data in mediumWords.txt has been restored!", resultMessage);
    }

    @Test
    void testRestoringDefaultWordsToFileWithHardWords() {
        String resultMessage = textFiles.restoreDefaultTextFileWords("hardWords.txt");
        Set<String> resultWords = hardWords.keySet();
        Set<String> expectedResult = Set.of("frizzled", "thumbscrew", "syndrome", "microwave");

        assertEquals(expectedResult, resultWords);
        assertEquals("Default data in hardWords.txt has been restored!", resultMessage);
    }

    @Test
    void testPreparingNewWordToAdd() {
        String result = textFiles.prepareWordToAdd("money", 1.5f);
        assertEquals("money 1.5", result);
    }

    @Test
    void testAddingNewWordToEasyWordsFile() {
        String result = textFiles.addWordToCertainFile("easyWords.txt", "money", 1.5f);
        textFiles.restoreDefaultTextFileWords("easyWords.txt");
        assertEquals("Word (money 1.5) has been added to easyWords.txt!", result);
    }

    @Test
    void testAddingNewWordToMediumWordsFile() {
        String result = textFiles.addWordToCertainFile("mediumWords.txt", "accuracy", 2.5f);
        textFiles.restoreDefaultTextFileWords("mediumWords.txt");
        assertEquals("Word (accuracy 2.5) has been added to mediumWords.txt!", result);
    }

    @Test
    void testAddingNewWordToHardWordsFile() {
        String result = textFiles.addWordToCertainFile("hardWords.txt", "cliffhangers", 4.5f);
        textFiles.restoreDefaultTextFileWords("hardWords.txt");
        assertEquals("Word (cliffhangers 4.5) has been added to hardWords.txt!", result);
    }

    @Test
    void testAddingNewWordToFileWhichDoesNotExist() {
        String result = textFiles.addWordToCertainFile("anotherFile.txt", "money", 1.5f);
        assertEquals("There isn't file anotherFile.txt!", result);
    }

    @Test
    void testRemovingWordFromEasyWordsFile() {
        String resultMessage = textFiles.removeWordFromFile("easyWords.txt", "cat");
        Set<String> wordsResult = textFiles.getWordsForGame("easyWords.txt").keySet();

        textFiles.restoreDefaultTextFileWords("easyWords.txt");

        assertEquals("Word cat has been removed from easyWords.txt!", resultMessage);
        assertTrue(wordsResult.containsAll(Set.of("dog", "city", "flower")));
    }

    @Test
    void testRemovingWordFromMediumWordsFile() {
        String resultMessage = textFiles.removeWordFromFile("mediumWords.txt", "difficulty");
        Set<String> wordsResult = textFiles.getWordsForGame("mediumWords.txt").keySet();

        textFiles.restoreDefaultTextFileWords("mediumWords.txt");

        assertEquals("Word difficulty has been removed from mediumWords.txt!", resultMessage);
        assertTrue(wordsResult.containsAll(Set.of("building", "computer", "reflector")));
    }

    @Test
    void testRemovingWordFromHardWordsFile() {
        String resultMessage = textFiles.removeWordFromFile("hardWords.txt", "thumbscrew");
        Set<String> wordsResult = textFiles.getWordsForGame("hardWords.txt").keySet();

        textFiles.restoreDefaultTextFileWords("hardWords.txt");

        assertEquals("Word thumbscrew has been removed from hardWords.txt!", resultMessage);
        assertTrue(wordsResult.containsAll(Set.of("frizzled", "syndrome", "microwave")));
    }

    @Test
    void testRemovingWordFromFileWhichDoesNotExist() {
        String resultMessage = textFiles.removeWordFromFile("anotherFile.txt", "cat");
        assertEquals("There isn't file anotherFile.txt!", resultMessage);
    }
}