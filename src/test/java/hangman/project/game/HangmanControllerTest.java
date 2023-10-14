package hangman.project.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class HangmanControllerTest {

    private HangmanController hangmanController;
    private final InputStream systemIn = System.in;
    private ByteArrayInputStream inputStream;

    @BeforeEach
    void setUp() {
        hangmanController = new HangmanController();
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
    void testEnteringInvalidOption() {
        assertEquals("Invalid option!", hangmanController.checkUserEnteredOption("9"));
    }

    @Test
    void testUserContinuingTheGameRound() {
        String userAnswer = "Y";
        inputData(userAnswer);

        String result = hangmanController.continuingTheGameRound();
        assertEquals("Game will be continue!", result);
    }

    @Test
    void testWinningSituationMessage() {
        assertTrue(hangmanController.checkGameResultMessage("Congratulations, you won the game!"));
    }

    @Test
    void testLoosingSituationMessage() {
        assertTrue(hangmanController.checkGameResultMessage("Game Over!"));
    }

    @Test
    void testUnknownSituationMessage() {
        assertFalse(hangmanController.checkGameResultMessage("Continue"));
    }

    @Test
    void testUserCancelRestoringDefaultData() {
        String userAnswer = "N";
        inputData(userAnswer);

        String result = hangmanController.restoringDefaultWordsInCertainFile();
        assertEquals("Cancel restoring default data!", result);
    }

    @Test
    void testCancelClearingScoreboard() {
        String userAnswer = "N";
        inputData(userAnswer);

        String result = hangmanController.clearingScoreboard();
        assertEquals("Clearing scoreboard has been canceled!", result);
    }

    @Test
    void testUserQuittingTheGame() {
        String userAnswer = "Y";
        inputData(userAnswer);

        String result = hangmanController.quittingTheGame();
        assertEquals("Thanks for playing! Goodbye!", result);
    }

    @Test
    void testUserCancelClearingScoreboard() {
        String userInput = "N";
        inputData(userInput);

        String result = hangmanController.clearingScoreboard();
        assertEquals("Clearing scoreboard has been canceled!", result);
    }

    @Test
    void testUserEnteringInvalidDataDuringSelectingSortingMethod() {
        String result = hangmanController.sortingByUserChosenMethod("o");
        assertEquals("Invalid data!", result);
    }

    @Test
    void testUserCancelingQuittingTheGame() {
        String userAnswer = "N";
        inputData(userAnswer);

        String result = hangmanController.quittingTheGame();
        assertEquals("Cancel quitting the game!", result);
    }
}