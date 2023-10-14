package hangman.project.wordstodraw;

import hangman.project.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HangmanWordsToDrawTest {

    private HangmanWordsToDraw wordsToDraw;

    @BeforeEach
    void setUp() {
        wordsToDraw = new HangmanWordsToDraw();
    }

    @RepeatedTest(5)
    void testGetRandomIndexToDrawWordIsGettingAllPossibleIndexesOnEasyLevel() {
        List<Integer> easyPossibleWordsToDrawAllIndexes = getAllRandomIndexesToDrawnWords(wordsToDraw.getEasyPossibleWordsToDraw(), "EASY");

        assertTrue(easyPossibleWordsToDrawAllIndexes.containsAll(List.of(0,1,2,3)));
    }

    @RepeatedTest(5)
    void testGetRandomIndexToDrawWordIsGettingAllPossibleIndexesOnMediumLevel() {
        List<Integer> mediumPossibleWordsToDrawAllIndexes =getAllRandomIndexesToDrawnWords(wordsToDraw.getMediumPossibleWordsToDraw(), "MEDIUM");

        assertTrue(mediumPossibleWordsToDrawAllIndexes.containsAll(List.of(0,1,2,3)));
    }

    @RepeatedTest(5)
    void testGetRandomIndexToDrawWordIsGettingAllPossibleIndexesOnHardLevel() {
        List<Integer> hardPossibleWordsToDrawAllIndexes = getAllRandomIndexesToDrawnWords(wordsToDraw.getHardPossibleWordsToDraw(), "HARD");

        assertTrue(hardPossibleWordsToDrawAllIndexes.containsAll(List.of(0,1,2,3)));
    }

    private List<Integer> getAllRandomIndexesToDrawnWords(Map<String, Float> possibleWordsToDraw, String difficultyLevel) {
        List<Integer> drawnIndexes = new ArrayList<>();

        for (int i = 0; i < possibleWordsToDraw.size(); i++) {
            drawnIndexes.add(wordsToDraw.getRandomIndexToDrawWord(difficultyLevel, possibleWordsToDraw));
        }

        return drawnIndexes;
    }

    @Test
    void testGettingEasyWordToGuess() {
        Pair<String, Float> easyWord = wordsToDraw.getRandomWordDependsOnDifficultyLevel("EASY");
        String possibleEasyWords = wordsToDraw.getEasyPossibleWordsToDraw().keySet().toString();

        assertTrue(possibleEasyWords.contains(easyWord.getFirst()));
    }

    @Test
    void testGettingMediumWordToGuess() {
        Pair<String, Float> mediumWord = wordsToDraw.getRandomWordDependsOnDifficultyLevel("MEDIUM");
        String possibleEasyWords = wordsToDraw.getMediumPossibleWordsToDraw().keySet().toString();

        assertTrue(possibleEasyWords.contains(mediumWord.getFirst()));
    }

    @Test
    void testGettingHardWordToGuess() {
        Pair<String, Float> hardWord = wordsToDraw.getRandomWordDependsOnDifficultyLevel("HARD");
        String possibleEasyWords = wordsToDraw.getHardPossibleWordsToDraw().keySet().toString();

        assertTrue(possibleEasyWords.contains(hardWord.getFirst()));
    }

    @Test
    void testGettingWordToGuessByEnterWrongDifficultyLevel() {
        Pair<String, Float> hardWord = wordsToDraw.getRandomWordDependsOnDifficultyLevel("UNKNOWN");

        assertEquals("Unknown", hardWord.getFirst());
    }

    @Test
    void testCheckingEasyWordDifficultyLevel() {
        String result = wordsToDraw.checkWordDifficultyLevel("flower");
        assertEquals("EASY", result);
    }

    @Test
    void testCheckingMediumWordDifficultyLevel() {
        String result = wordsToDraw.checkWordDifficultyLevel("building");
        assertEquals("MEDIUM", result);
    }

    @Test
    void testCheckingHardWordDifficultyLevel() {
        String result = wordsToDraw.checkWordDifficultyLevel("thumbscrew");
        assertEquals("HARD", result);
    }

    @Test
    void testAddingNewWordToEasyWords() {
        String result = wordsToDraw.addNewWordToGame("money", 1.5f, "EASY");
        wordsToDraw.restoreDefaultTextFileWords("EASY");
        assertEquals("Word (money 1.5) has been added to easyWords.txt!", result);
    }

    @Test
    void testAddingNewWordToMediumWords() {
        String result = wordsToDraw.addNewWordToGame("interesting", 3.5f, "MEDIUM");
        wordsToDraw.restoreDefaultTextFileWords("MEDIUM");
        assertEquals("Word (interesting 3.5) has been added to mediumWords.txt!", result);
    }

    @Test
    void testAddingNewWordToHardWords() {
        String result = wordsToDraw.addNewWordToGame("radioactivity", 4.5f, "HARD");
        wordsToDraw.restoreDefaultTextFileWords("HARD");
        assertEquals("Word (radioactivity 4.5) has been added to hardWords.txt!", result);
    }

    @Test
    void testIsAddedWordInAnyFile() {
        wordsToDraw.addNewWordToGame("interesting", 3.5f, "MEDIUM");
        String result = wordsToDraw.checkWordDifficultyLevel("interesting");
        wordsToDraw.restoreDefaultTextFileWords("MEDIUM");
        assertEquals("MEDIUM", result);
    }

    @Test
    void testIsAddedWordIndexGettingDuringDrawingWord() {
        wordsToDraw.addNewWordToGame("interesting", 3.5f, "MEDIUM");
        List<Integer> mediumPossibleWordsToDrawAllIndexes = getAllRandomIndexesToDrawnWords(wordsToDraw.getMediumPossibleWordsToDraw(), "MEDIUM");
        wordsToDraw.restoreDefaultTextFileWords("MEDIUM");

        assertTrue(mediumPossibleWordsToDrawAllIndexes.containsAll(List.of(0,1,2,3,4)));
    }

    @Test
    void testAddingTheSameNewWordTwiceToContainer() {
        wordsToDraw.addNewWordToGame("money", 1.5f, "EASY");
        String result = wordsToDraw.addNewWordToGame("money", 1.5f, "EASY");
        wordsToDraw.restoreDefaultTextFileWords("EASY");
        assertEquals("Cannot add money because it is already in the files!", result);
    }

    @Test
    void testRemovingWordFromEasyWordsFile() {
        String result = wordsToDraw.removingWordFromTheFile("cat");
        wordsToDraw.restoreDefaultTextFileWords("EASY");
        assertEquals("Word cat has been removed from easyWords.txt!" ,result);
    }

    @Test
    void testRemovingWordFromMediumWordsFile() {
        String result = wordsToDraw.removingWordFromTheFile("building");
        wordsToDraw.restoreDefaultTextFileWords("MEDIUM");
        assertEquals("Word building has been removed from mediumWords.txt!" ,result);
    }

    @Test
    void testRemovingWordFromHardWordsFile() {
        String result = wordsToDraw.removingWordFromTheFile("microwave");
        wordsToDraw.restoreDefaultTextFileWords("HARD");
        assertEquals("Word microwave has been removed from hardWords.txt!" ,result);
    }

    @Test
    void testIsRemovedWordInAnyFile() {
        wordsToDraw.removingWordFromTheFile("cat");
        String result = wordsToDraw.checkWordDifficultyLevel("cat");
        wordsToDraw.restoreDefaultTextFileWords("EASY");
        assertEquals("There isn't word cat in any file!", result);
    }

    @Test
    void testIsRemovedWordIndexGettingDuringDrawingWord() {
        wordsToDraw.removingWordFromTheFile("cat");
        List<Integer> mediumPossibleWordsToDrawAllIndexes = getAllRandomIndexesToDrawnWords(wordsToDraw.getEasyPossibleWordsToDraw(), "EASY");
        wordsToDraw.restoreDefaultTextFileWords("EASY");

        assertTrue(mediumPossibleWordsToDrawAllIndexes.containsAll(List.of(0,1,2)));
    }

    @Test
    void testRemovingWordThatIsNotInTheFiles() {
        String result = wordsToDraw.removingWordFromTheFile("money");
        assertEquals("There isn't word money in any file!" ,result);
    }

    @Test
    void testGettingEasyWordsFileName() {
        String chosenDifficulty = "EASY";
        String result = wordsToDraw.getCertainWordsFileName(chosenDifficulty);
        assertEquals("easyWords.txt", result);
    }

    @Test
    void testGettingMediumWordsFileName() {
        String chosenDifficulty = "MEDIUM";
        String result = wordsToDraw.getCertainWordsFileName(chosenDifficulty);
        assertEquals("mediumWords.txt", result);
    }

    @Test
    void testGettingHardWordsFileName() {
        String chosenDifficulty = "HARD";
        String result = wordsToDraw.getCertainWordsFileName(chosenDifficulty);
        assertEquals("hardWords.txt", result);
    }

    @Test
    void testEnteringInvalidDataToGetWordsFileName() {
        String chosenDifficulty = "words";
        String result = wordsToDraw.getCertainWordsFileName(chosenDifficulty);
        assertEquals("Invalid difficulty level!", result);
    }
}