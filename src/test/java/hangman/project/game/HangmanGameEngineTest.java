package hangman.project.game;

import hangman.project.DifficultyLevels;
import hangman.project.playersdata.HangmanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HangmanGameEngineTest {

    private HangmanGameEngine gameEngine;

    @BeforeEach
    void setUp() {
        gameEngine = new HangmanGameEngine();
        gameEngine.setGameDifficultyLevel(DifficultyLevels.EASY);
        gameEngine.setHangmanPlayer(new HangmanPlayer("user"));
    }

    @Test
    void testAddingPlayerToTheGame() {
        gameEngine.addPlayerToGame("player");
        assertEquals("player", gameEngine.getHangmanPlayer().getPlayerName());
    }

    @Test
    void testGettingRandomWordOnEasyDifficultyLevel() {
        gameEngine.drawnWordToGuess();
        String result = gameEngine.getDrawnWord();
        String expectedResult = gameEngine.getWordsToDraw().getEasyPossibleWordsToDraw().toString();

        assertTrue(expectedResult.contains(result));
    }

    @Test
    void testGettingRandomWordOnMediumDifficultyLevel() {
        gameEngine.setGameDifficultyLevel("medium");
        gameEngine.drawnWordToGuess();
        String result = gameEngine.getDrawnWord();
        String expectedResult = gameEngine.getWordsToDraw().getMediumPossibleWordsToDraw().toString();

        assertTrue(expectedResult.contains(result));
    }

    @Test
    void testGettingRandomWordOnHardDifficultyLevel() {
        gameEngine.setGameDifficultyLevel("hard");
        gameEngine.drawnWordToGuess();
        String result = gameEngine.getDrawnWord();
        String expectedResult = gameEngine.getWordsToDraw().getHardPossibleWordsToDraw().toString();

        assertTrue(expectedResult.contains(result));
    }

    @Test
    void testCreatingCoveredWord() {
        gameEngine.drawnWordToGuess();
        String drawnWord = gameEngine.getDrawnWord();
        gameEngine.createCoveredWord(drawnWord);
        String coveredRandomWord = gameEngine.getCoveredDrawnWord().toString();
        StringBuilder expectedResult = new StringBuilder();

        expectedResult.append("_".repeat(drawnWord.length()));

        assertEquals(expectedResult.toString(), coveredRandomWord);
    }

    @Test
    void testDiscoveringLetterInCoveredWord() {
        gameEngine.drawnWordToGuess();
        String drawnWord = gameEngine.getDrawnWord();
        gameEngine.createCoveredWord(drawnWord);
        char drawnWordLetter = drawnWord.charAt(0);
        String result = gameEngine.checkingEnteredLetter(drawnWordLetter);

        assertEquals(gameEngine.getCoveredDrawnWordToDisplay(), result);
    }

    @Test
    void testEnteringLetterWhichIsNotInDrawnWord() {
        gameEngine.drawnWordToGuess();
        String drawnWord = gameEngine.getDrawnWord();
        gameEngine.createCoveredWord(drawnWord);
        char wrongLetter = '.';
        String result = gameEngine.checkingEnteredLetter(wrongLetter);
        String expectedResult = String.format("There isn't %c in the drawn word!%n%s", wrongLetter, gameEngine.getCoveredDrawnWordToDisplay());

        assertEquals(expectedResult, result);
    }

    @Test
    void testGettingMessageAfterOneWrongGuess() {
        gameEngine.drawnWordToGuess();
        String drawnWord = gameEngine.getDrawnWord();
        gameEngine.createCoveredWord(drawnWord);
        char wrongLetter = '.';
        gameEngine.setPossibleWrongGuesses(3);
        gameEngine.checkingEnteredLetter(wrongLetter);
        String result = gameEngine.checkAmountOfTries();

        assertEquals("You have 2 tries!", result);
    }

    @Test
    void testGettingMessageAfterThreeWrongGuesses() {
        gameEngine.drawnWordToGuess();
        String drawnWord = gameEngine.getDrawnWord();
        gameEngine.createCoveredWord(drawnWord);
        char wrongLetter = '.';
        gameEngine.checkingEnteredLetter(wrongLetter);
        gameEngine.checkingEnteredLetter(wrongLetter);
        gameEngine.checkingEnteredLetter(wrongLetter);
        String result = gameEngine.checkAmountOfTries();

        assertEquals("You don't have any tries! Game Over!", result);
    }

    @Test
    void testWinningSituation() {
        String drawnWord = "cat";
        gameEngine.setDrawnWord(drawnWord);
        gameEngine.createCoveredWord(drawnWord);
        gameEngine.checkingEnteredLetter('c');
        gameEngine.checkingEnteredLetter('a');
        gameEngine.checkingEnteredLetter('t');
        String result = gameEngine.checkGameResult();

        assertEquals("Congratulations, you won the game!", result);
    }

    @Test
    void testLoosingSituation() {
        String drawnWord = "cat";
        gameEngine.setDrawnWord(drawnWord);
        gameEngine.createCoveredWord(drawnWord);
        gameEngine.checkingEnteredLetter('d');
        gameEngine.checkingEnteredLetter('d');
        gameEngine.checkingEnteredLetter('d');
        String result = gameEngine.checkGameResult();

        assertEquals("You don't have any tries! Game Over!", result);
    }

    @Test
    void testAmountOfWonGamesAfterWinningGameRound() {
        String drawnWord = "cat";
        gameEngine.setDrawnWord(drawnWord);
        gameEngine.createCoveredWord(drawnWord);
        gameEngine.checkingEnteredLetter('c');
        gameEngine.checkingEnteredLetter('a');
        gameEngine.checkingEnteredLetter('t');
        gameEngine.checkGameResult();
        int result = gameEngine.getHangmanPlayer().getPlayerStats().getTotalWonGameRounds();

        assertEquals(1, result);
    }

    @Test
    void testAmountOfLostGamesAfterLoosingGameRound() {
        String drawnWord = "cat";
        gameEngine.setDrawnWord(drawnWord);
        gameEngine.createCoveredWord(drawnWord);
        gameEngine.checkingEnteredLetter('d');
        gameEngine.checkingEnteredLetter('d');
        gameEngine.checkingEnteredLetter('d');
        gameEngine.checkGameResult();
        int result = gameEngine.getHangmanPlayer().getPlayerStats().getTotalLostGameRounds();

        assertEquals(1, result);
    }

    @Test
    void testAmountOfPointsAfterWinningGameRound() {
        String drawnWord = "cat";
        gameEngine.setDrawnWord(drawnWord);
        gameEngine.setDrawnWordPoints(1);
        gameEngine.createCoveredWord(drawnWord);
        gameEngine.checkingEnteredLetter('c');
        gameEngine.checkingEnteredLetter('a');
        gameEngine.checkingEnteredLetter('t');
        gameEngine.checkGameResult();
        float result = gameEngine.getHangmanPlayer().getPlayerStats().getTotalPlayerPoints();

        assertEquals(1, result);
    }

    @Test
    void testParsingCoveredWordToDisplay() {
        gameEngine.drawnWordToGuess();
        gameEngine.createCoveredWord(gameEngine.getDrawnWord());
        String result = gameEngine.getCoveredDrawnWordToDisplay();
        char[] drawnWordLetters = new char[gameEngine.getDrawnWord().length()];
        Arrays.fill(drawnWordLetters,'_');
        String expectedResult = new String(drawnWordLetters);
        assertEquals(expectedResult, result);
    }
}