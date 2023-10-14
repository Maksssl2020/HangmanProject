package hangman.project.playersdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HangmanPlayersScoreboardTest {

    private HangmanPlayersScoreboard scoreboard;
    private HangmanPlayer testPlayer1 = new HangmanPlayer("mark");
    private HangmanPlayer testPlayer2 = new HangmanPlayer("frank");
    private HangmanPlayer testPlayer3 = new HangmanPlayer("andrew");
    private List<HangmanPlayer> testPlayers = new LinkedList<>(List.of(testPlayer1, testPlayer2, testPlayer3));
    private final LocalDate today = LocalDate.now();

    @BeforeEach
    void setUp() {
        scoreboard = new HangmanPlayersScoreboard();
        scoreboard.clearScoreboard();
        testPlayer1.addWonGameRound();
        testPlayer1.addPoints(1.5f);
        testPlayer1.addLostGameRound();
        testPlayer1.addLostGameRound();
        testPlayer2.addLostGameRound();
        testPlayer3.addWonGameRound();
        testPlayer3.addPoints(3.0f);
    }

    @Test
    void testDeletingScoreboardFile() {
        scoreboard.removingScoreboardFile();
        assertFalse(scoreboard.isFileAvailable("hangmanPlayersScoreboard.txt"));
    }

    @Test
    void testCreatingScoreboardFile() {
        scoreboard.removingScoreboardFile();
        scoreboard.createScoreboardFile();
        assertTrue(scoreboard.isFileAvailable("hangmanPlayersScoreboard.txt"));
    }

    @Test
    void testGettingEmptyMessageFromFile() {
        String result = scoreboard.getPlayersStatsFromScoreboard();
        assertEquals("Scoreboard is empty!", result);
    }

    @Test
    void testClearingScoreboard() {
        scoreboard.clearScoreboard();
        assertEquals("Scoreboard is empty!", scoreboard.getPlayersStatsFromScoreboard());
    }

    @Test
    void testPreparingPlayerStatsToAdd() {
        String result = scoreboard.preparePlayerStatsToAdd(testPlayer1.getPlayerPreparedStats());
        String expectedResult = String.format("%s | Player name: %s | Total played rounds: 3 | Won rounds: 1 | Lost rounds: 2 | Total points: 1.5\n", today, testPlayer1.getPlayerName());
        assertEquals(expectedResult, result);
    }

    @Test
    void testPreparingPlayerStatsToDisplay() {
        String preparedPlayerStats = scoreboard.preparePlayerStatsToAdd(testPlayer1.getPlayerPreparedStats());
        scoreboard.getAllPlayersStats().add(HangmanPlayerStats.createPlayerStats(preparedPlayerStats));
        String expectedResult = String.format("%s | Player name: %s | Total played rounds: 3 | Won rounds: 1 | Lost rounds: 2 | Total points: 1.5", today, testPlayer1.getPlayerName());
        assertEquals(expectedResult, scoreboard.getPlayersStatsFromScoreboard());
    }

    @Test
    void testAddingOnePlayerStatsToScoreboard() {
        addTestPlayersStats(1);
        String result = scoreboard.getPlayersStatsFromScoreboard();
        String expectedResult = String.format("%s | Player name: %s | Total played rounds: 3 | Won rounds: 1 | Lost rounds: 2 | Total points: 1.5", today, testPlayer1.getPlayerName());
        assertEquals(expectedResult, result);
    }

    @Test
    void testAddingTwoPlayerStatsToScoreboard() {
        addTestPlayersStats(2);
        String result = scoreboard.getPlayersStatsFromScoreboard();
        StringBuilder expectedResult = new StringBuilder()
                .append(scoreboard.preparePlayerStatsToAdd(testPlayer1.getPlayerPreparedStats()))
                .append(scoreboard.preparePlayerStatsToAdd(testPlayer2.getPlayerPreparedStats()));

        assertEquals(expectedResult.toString().strip(), result);
    }

    @Test
    void testSortingPlayerStatsByTheirNamesAndUpdatingFileAfterSorting() {
        addTestPlayersStats(3);
        scoreboard.sortPlayerStatsByPlayersNames();

        StringBuilder expectedResult = new StringBuilder()
                .append(scoreboard.preparePlayerStatsToAdd(testPlayer3.getPlayerPreparedStats()))
                .append(scoreboard.preparePlayerStatsToAdd(testPlayer2.getPlayerPreparedStats()))
                .append(scoreboard.preparePlayerStatsToAdd(testPlayer1.getPlayerPreparedStats()));

        assertEquals(expectedResult.toString().strip(), scoreboard.getPlayersStatsFromScoreboard());

        scoreboard = new HangmanPlayersScoreboard();
        assertEquals(expectedResult.toString().strip(), scoreboard.getPlayersStatsFromScoreboard());
    }

    @Test
    void testSortingPlayerStatsByTheirTotalPointsAndUpdatingFileAfterSorting() {
        addTestPlayersStats(3);
        scoreboard.sortPlayerStatsByPlayersTotalPoints();

        StringBuilder expectedResult = new StringBuilder()
                .append(scoreboard.preparePlayerStatsToAdd(testPlayer3.getPlayerPreparedStats()))
                .append(scoreboard.preparePlayerStatsToAdd(testPlayer1.getPlayerPreparedStats()))
                .append(scoreboard.preparePlayerStatsToAdd(testPlayer2.getPlayerPreparedStats()));

        assertEquals(expectedResult.toString().strip(), scoreboard.getPlayersStatsFromScoreboard());

        scoreboard = new HangmanPlayersScoreboard();
        assertEquals(expectedResult.toString().strip(), scoreboard.getPlayersStatsFromScoreboard());
    }

    @Test
    void testSortingPlayerStatsByDateTheStatsWasAddedAndUpdatingScoreboardFileAfterSorting() {
        testPlayer1.getPlayerStats().setStatsDate(today.plusDays(2).toString());
        testPlayer2.getPlayerStats().setStatsDate(today.toString());
        testPlayer3.getPlayerStats().setStatsDate(today.plusDays(5).toString());

        scoreboard.addStatsToFile(testPlayer1.getPlayerPreparedStats());
        scoreboard.addStatsToFile(testPlayer2.getPlayerPreparedStats());
        scoreboard.addStatsToFile(testPlayer3.getPlayerPreparedStats());
        scoreboard.getAllPlayersStats().add(HangmanPlayerStats.createPlayerStats(testPlayer1.getPlayerPreparedStats()));
        scoreboard.getAllPlayersStats().add(HangmanPlayerStats.createPlayerStats(testPlayer2.getPlayerPreparedStats()));
        scoreboard.getAllPlayersStats().add(HangmanPlayerStats.createPlayerStats(testPlayer3.getPlayerPreparedStats()));

        scoreboard.sortPlayerStatsByDateTheStatsWasAdded();

        StringBuilder expectedResult = new StringBuilder()
                .append(testPlayer3.getPlayerPreparedStats())
                .append("\n")
                .append(testPlayer1.getPlayerPreparedStats())
                .append("\n")
                .append(testPlayer2.getPlayerPreparedStats());

        assertEquals(expectedResult.toString().strip(), scoreboard.getPlayersStatsFromScoreboard());

        scoreboard = new HangmanPlayersScoreboard();
        assertEquals(expectedResult.toString().strip(), scoreboard.getPlayersStatsFromScoreboard());
    }

    private void addTestPlayersStats(int howManyStats) {
        for (int i = 0; i < howManyStats; i++) {
            scoreboard.addPlayerStatsToScoreboard(testPlayers.get(i).getPlayerPreparedStats());
        }
    }
}