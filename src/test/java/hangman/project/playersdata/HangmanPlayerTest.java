package hangman.project.playersdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HangmanPlayerTest {

    private HangmanPlayer hangmanPlayer;

    @BeforeEach
    void setUp() {
        hangmanPlayer = new HangmanPlayer("player");
    }

    @Test
    void testGettingPreparedPlayerStats() {
        hangmanPlayer.addWonGameRound();
        hangmanPlayer.addPoints(2.5f);
        hangmanPlayer.addLostGameRound();
        String result = hangmanPlayer.getPlayerPreparedStats();

        assertEquals("Player name: player | Total played rounds: 2 | Won rounds: 1 | Lost rounds: 1 | Total points: 2.5", result);
    }
}