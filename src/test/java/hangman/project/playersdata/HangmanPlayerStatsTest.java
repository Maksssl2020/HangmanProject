package hangman.project.playersdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HangmanPlayerStatsTest {

    private HangmanPlayerStats hangmanPlayerStats;

    @BeforeEach
    void setUp() {
        hangmanPlayerStats = new HangmanPlayerStats();
    }

    @Test
    void testAddingOneWonGameRound() {
        hangmanPlayerStats.addWonGameRound();
        assertEquals(1, hangmanPlayerStats.getTotalWonGameRounds());
    }

    @Test
    void testAddingOneLostGameRound() {
        hangmanPlayerStats.addLostGameRound();
        assertEquals(1, hangmanPlayerStats.getTotalLostGameRounds());
    }

    @Test
    void testAddingPointsToUserScore() {
        hangmanPlayerStats.addPoints(2.5f);
        assertEquals(2.5f, hangmanPlayerStats.getTotalPlayerPoints());
    }

    @Test
    void testGettingInfoAboutPlayedTotalRoundsAfterOneLostRoundAndOneWonRound() {
        hangmanPlayerStats.addLostGameRound();
        hangmanPlayerStats.addWonGameRound();
        assertEquals(2, hangmanPlayerStats.getTotalPlayedRounds());
    }

    @Test
    void testGettingUserStatsAfterPlayedThreeRounds() {
        hangmanPlayerStats.setPlayerName("player");
        hangmanPlayerStats.addWonGameRound();
        hangmanPlayerStats.addPoints(1.5f);
        hangmanPlayerStats.addLostGameRound();
        hangmanPlayerStats.addWonGameRound();
        hangmanPlayerStats.addPoints(2.0f);

        String result = hangmanPlayerStats.toString();
        assertEquals("Player name: player | Total played rounds: 3 | Won rounds: 2 | Lost rounds: 1 | Total points: 3.5", result);
    }

    @Test
    void testCreatingOneHangmanPlayerStatsFromTextLine() {
        String playerStats = "2023-10-03 | Player name: player | Total played rounds: 3 | Won rounds: 2 | Lost rounds: 1 | Total points: 3.5";
        HangmanPlayerStats result = HangmanPlayerStats.createPlayerStats(playerStats);

        assertEquals(playerStats, result.toString());
    }

    @Test
    void testCreatingTwoHangmanPlayerStatsFromTextLine() {
        String playerStats1 = "2023-10-07 | Player name: player1 | Total played rounds: 3 | Won rounds: 2 | Lost rounds: 1 | Total points: 3.5";
        String playerStats2 = "2023-10-07 | Player name: player2 | Total played rounds: 2 | Won rounds: 1 | Lost rounds: 1 | Total points: 2";
        HangmanPlayerStats result1 = HangmanPlayerStats.createPlayerStats(playerStats1);
        HangmanPlayerStats result2 = HangmanPlayerStats.createPlayerStats(playerStats2);

        assertEquals(playerStats1, result1.toString());
        assertEquals(playerStats2, result2.toString());
    }
}