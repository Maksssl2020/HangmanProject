package hangman.project.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class HangmanUserInterfaceTest {

    private HangmanUserInterface userInterface;
    private final InputStream systemIn = System.in;
    private ByteArrayInputStream inputStream;

    @BeforeEach
    void setUp() {
        userInterface = new HangmanUserInterface();
    }

    @AfterEach
    void resetSystemIn() {
        System.setIn(systemIn);
    }

    private void inputData(String data) {
        inputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStream);
    }

    @Test
    void testUserEnteringCorrectName() {
        String userEnteredName = "player";
        inputData(userEnteredName);

        String result = userInterface.askUserToEnterName();
        assertEquals("player", result);
    }

    @Test
    void testUserEnteringEmptyName() {
        assertFalse(userInterface.isValidUserEnteredName(""));
    }

    @Test
    void testUserSelectingCorrectOption() {
        String userSelectedOption = "2";
        inputData(userSelectedOption);

        String result = userInterface.askUserToSelectOption();
        assertEquals("2", result);
    }

    @Test
    void testUserEnteredInvalidOptionNumber() {
        assertFalse(userInterface.isUserSelectedOptionValid("9"));
    }

    @Test
    void testAskingUserToEnterLetter() {
        String userInput = "a";
        inputData(userInput);

        char result = userInterface.askUserToEnterLetter();
        assertEquals('a', result);
    }

    @Test
    void testUserEnteringInvalidLetter() {
        assertFalse(userInterface.isEnteredLetterValid("."));
    }

    @Test
    void testUserChoosingEasyDifficultyLevel() {
        String userDifficultyLevelChoice = "easy";
        inputData(userDifficultyLevelChoice);

        String result = userInterface.askUserToChooseDifficultyLevel();
        assertEquals("EASY", result);
    }

    @Test
    void testUserChoosingMediumDifficultyLevel() {
        String userDifficultyLevelChoice = "medium";
        inputData(userDifficultyLevelChoice);

        String result = userInterface.askUserToChooseDifficultyLevel();
        assertEquals("MEDIUM", result);
    }

    @Test
    void testUserChoosingHardDifficultyLevel() {
        String userDifficultyLevelChoice = "hard";
        inputData(userDifficultyLevelChoice);

        String result = userInterface.askUserToChooseDifficultyLevel();
        assertEquals("HARD", result);
    }

    @Test
    void testUserEnteringInvalidDifficultyLevel() {
        assertFalse(userInterface.isEnteredDifficultyLevelValid("EA"));
    }

    @Test
    void testGettingMessageAboutPossibleTriesToChooseOnEasyLevel() {
        String result = userInterface.getMessageAboutMaxPossibleTriesToChoose("EASY");
        assertEquals("Amount of tries to choose: 1 - 8", result);
    }

    @Test
    void testGettingMessageAboutPossibleTriesToChooseOnMediumLevel() {
        String result = userInterface.getMessageAboutMaxPossibleTriesToChoose("MEDIUM");
        assertEquals("Amount of tries to choose: 1 - 5", result);
    }

    @Test
    void testGettingMessageAboutPossibleTriesToChooseOnHardLevel() {
        String result = userInterface.getMessageAboutMaxPossibleTriesToChoose("HARD");
        assertEquals("Amount of tries to choose: 1 - 3", result);
    }

    @Test
    void testUserChoosingAmountOfPossibleTriesOnEasyDifficultyLevel() {
        String userEnteredAmountOfPossibleTries = "8";
        inputData(userEnteredAmountOfPossibleTries);

        int result = userInterface.askUserToEnterAmountOfPossibleTries("EASY");
        assertEquals(8, result);
    }

    @Test
    void testUserChoosingAmountOfPossibleTriesOnMediumDifficultyLevel() {
        String userEnteredAmountOfPossibleTries = "5";
        inputData(userEnteredAmountOfPossibleTries);

        int result = userInterface.askUserToEnterAmountOfPossibleTries("MEDIUM");
        assertEquals(5, result);
    }

    @Test
    void testUserChoosingAmountOfPossibleTriesOnHardDifficultyLevel() {
        String userEnteredAmountOfPossibleTries = "3";
        inputData(userEnteredAmountOfPossibleTries);

        int result = userInterface.askUserToEnterAmountOfPossibleTries("HARD");
        assertEquals(3, result);
    }

    @Test
    void testEnteringTooBigAmountOfPossibleTries() {
        assertFalse(userInterface.isValidUserEnteredAmountOfPossibleTries("9", "EASY"));
    }
    
    @Test
    void testUserChoosingWrongAmountOfPossibleTriesOnEasyDifficultyLevel() {
        assertFalse(userInterface.isValidAmountOfPossibleTriesDependsOnDifficultyLevel(10, "EASY"));
    }

    @Test
    void testUserChoosingWrongAmountOfPossibleTriesOnMediumDifficultyLevel() {
        assertFalse(userInterface.isValidAmountOfPossibleTriesDependsOnDifficultyLevel(8, "MEDIUM"));
    }

    @Test
    void testUserChoosingWrongAmountOfPossibleTriesOnHardDifficultyLevel() {
        assertFalse(userInterface.isValidAmountOfPossibleTriesDependsOnDifficultyLevel(6, "HARD"));
    }

    @Test
    void testGettingMessageAboutMinAndMaxLengthForWordOnEasyDifficultyLevel() {
        String result = userInterface.getMessageAboutWordToAddMinAndMaxLength("EASY");
        assertEquals("For word on EASY difficulty level ( minimum word length is 3 | maximum word length is 8 )", result);
    }

    @Test
    void testGettingMessageAboutMinAndMaxLengthForWordOnMediumDifficultyLevel() {
        String result = userInterface.getMessageAboutWordToAddMinAndMaxLength("MEDIUM");
        assertEquals("For word on MEDIUM difficulty level ( minimum word length is 6 | maximum word length is 10 )", result);
    }

    @Test
    void testGettingMessageAboutMinAndMaxLengthForWordOnHardDifficultyLevel() {
        String result = userInterface.getMessageAboutWordToAddMinAndMaxLength("HARD");
        assertEquals("For word on HARD difficulty level ( minimum word length is 8 | maximum word length is 20 )", result);
    }

    @Test
    void testUserAddingWordForEasyDifficultyLevel() {
        String userWord = "money";
        inputData(userWord);

        String result = userInterface.askUserToEnterWordToAdd("EASY");
        assertEquals(result, userWord);
    }

    @Test
    void testUserAddingWordForMediumDifficultyLevel() {
        String userWord = "backspace";
        inputData(userWord);

        String result = userInterface.askUserToEnterWordToAdd("MEDIUM");
        assertEquals(result, userWord);
    }

    @Test
    void testUserAddingWordForHardDifficultyLevel() {
        String userWord = "copyrightable";
        inputData(userWord);

        String result = userInterface.askUserToEnterWordToAdd("HARD");
        assertEquals(result, userWord);
    }

    @Test
    void testUserEnteringTooLongOrTooShortWordToAddForEasyDifficultyLevel() {
        assertFalse(userInterface.isWordToAddValid("buildings", "EASY"));
        assertFalse(userInterface.isWordToAddValid("or", "EASY"));
    }

    @Test
    void testUserEnteringTooLongOrTooShortWordToAddForMediumDifficultyLevel() {
        assertFalse(userInterface.isWordToAddValid("copyrightable", "MEDIUM"));
        assertFalse(userInterface.isWordToAddValid("cat", "MEDIUM"));
    }

    @Test
    void testUserEnteringTooLongOrTooShortWordToAddForHardDifficultyLevel() {
        assertFalse(userInterface.isWordToAddValid("hippopotomonstrosesquippedaliophobia", "HARD"));
        assertFalse(userInterface.isWordToAddValid("dog", "HARD"));
    }

    @Test
    void testGettingMessageAboutMinAndMaxAmountOfPointsPerWordOnEasyDifficultyLevel() {
        String result = userInterface.getMessageAboutMinAndMaxAmountOfPointsForAddedWord("EASY");
        assertEquals("For word on EASY difficulty level ( min points is 1 | max points is 2 )", result);
    }

    @Test
    void testGettingMessageAboutMinAndMaxAmountOfPointsPerWordOnMediumDifficultyLevel() {
        String result = userInterface.getMessageAboutMinAndMaxAmountOfPointsForAddedWord("MEDIUM");
        assertEquals("For word on MEDIUM difficulty level ( min points is 2,5 | max points is 3,5 )", result);
    }

    @Test
    void testGettingMessageAboutMinAndMaxAmountOfPointsPerWordOnHardDifficultyLevel() {
        String result = userInterface.getMessageAboutMinAndMaxAmountOfPointsForAddedWord("HARD");
        assertEquals("For word on HARD difficulty level ( min points is 4 | max points is 5 )", result);
    }

    @Test
    void testUserEnteringCorrectAmountOfWordPointsToEasyWord() {
        String userEnteredAmountOfWordPoints = "1.5";
        inputData(userEnteredAmountOfWordPoints);

        Float result = userInterface.askUserToEnterAmountOfWordPoints("EASY");
        assertEquals(1.5f, result);
    }

    @Test
    void testUserEnteringCorrectAmountOfWordPointsToMediumWord() {
        String userEnteredAmountOfWordPoints = "2.5";
        inputData(userEnteredAmountOfWordPoints);

        Float result = userInterface.askUserToEnterAmountOfWordPoints("MEDIUM");
        assertEquals(2.5f, result);
    }

    @Test
    void testUserEnteringCorrectAmountOfWordPointsToHardWord() {
        String userEnteredAmountOfWordPoints = "4.5";
        inputData(userEnteredAmountOfWordPoints);

        Float result = userInterface.askUserToEnterAmountOfWordPoints("HARD");
        assertEquals(4.5f, result);
    }

    @Test
    void testUserEnteringCorrectWordToRemove() {
        String userEnteredWordToRemove = "CAT";
        inputData(userEnteredWordToRemove);

        String result = userInterface.askUserToEnterWordToRemove();
        assertEquals("cat", result);
    }

    @Test
    void testUserEnteringInvalidWordToRemove() {
        assertFalse(userInterface.isEnteredUserWordToRemoveValid("cat1"));
    }

    @Test
    void testUserSelectedCorrectScoreboardOption() {
        String userSelectedOption = "2";
        inputData(userSelectedOption);

        String result = userInterface.askUserToSelectScoreboardSortingMethod();
        assertEquals("2", result);
    }

    @Test
    void testUserSelectedInCorrectScoreboardOption() {
        assertFalse(userInterface.isValidUserSelectedScoreboardSortingMethod("6"));
    }

    @Test
    void testUserEnteringYes() {
        String userAnswer = "Y";
        inputData(userAnswer);

        String result = userInterface.askUserToChooseYesOrNo();
        assertEquals("Y", result);
    }

    @Test
    void testUserEnteringNo() {
        String userAnswer = "n";
        inputData(userAnswer);

        String result = userInterface.askUserToChooseYesOrNo();
        assertEquals("n", result);
    }

    @Test
    void testUserEnteringWrongOptionLetter() {
        assertFalse(userInterface.isUserAnswerValid("w"));
    }
}