package hangman.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyLevelsTest {

    @Test
    void testPreparingDifficultyLevelsToDisplay() {
        String expectedResult = """
                EASY
                MEDIUM
                HARD""";

        assertEquals(expectedResult, DifficultyLevels.displayDifficultyLevels());
    }

    @Test
    void testGettingPossibleAmountOfTriesOnEasyLevel() {
        assertEquals(8, DifficultyLevels.EASY.getMaxPossibleTriesForCertainDifficultyLevel());
    }

    @Test
    void testGettingPossibleAmountOfTriesOnMediumLevel() {
        assertEquals(5, DifficultyLevels.MEDIUM.getMaxPossibleTriesForCertainDifficultyLevel());
    }

    @Test
    void testGettingPossibleAmountOfTriesOnHardLevel() {
        assertEquals(3, DifficultyLevels.HARD.getMaxPossibleTriesForCertainDifficultyLevel());
    }

    @Test
    void testGettingMinAndMaxWordsLengthOnEasyLevel() {
        assertEquals(3, DifficultyLevels.EASY.getMinWordLength());
        assertEquals(8, DifficultyLevels.EASY.getMaxWordLength());
    }

    @Test
    void testGettingMinAndMaxWordsLengthOnMediumLevel() {
        assertEquals(6, DifficultyLevels.MEDIUM.getMinWordLength());
        assertEquals(10, DifficultyLevels.MEDIUM.getMaxWordLength());
    }

    @Test
    void testGettingMinAndMaxWordsLengthOnHardLevel() {
        assertEquals(8, DifficultyLevels.HARD.getMinWordLength());
        assertEquals(20, DifficultyLevels.HARD.getMaxWordLength());
    }

    @Test
    void testGettingMinAndMaxAmountOfPointsPerWordOnEasyLevel() {
        assertEquals(1.0f, DifficultyLevels.EASY.getMinPointsPerWord());
        assertEquals(2.0f, DifficultyLevels.EASY.getMaxPointsPerWord());
    }

    @Test
    void testGettingMinAndMaxAmountOfPointsPerWordOnMediumLevel() {
        assertEquals(2.5f, DifficultyLevels.MEDIUM.getMinPointsPerWord());
        assertEquals(3.5f, DifficultyLevels.MEDIUM.getMaxPointsPerWord());
    }

    @Test
    void testGettingMinAndMaxAmountOfPointsPerWordOnHardLevel() {
        assertEquals(4.0f, DifficultyLevels.HARD.getMinPointsPerWord());
        assertEquals(5.0f, DifficultyLevels.HARD.getMaxPointsPerWord());
    }
}