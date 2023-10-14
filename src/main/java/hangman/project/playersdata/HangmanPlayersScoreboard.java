package hangman.project.playersdata;

import java.io.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class HangmanPlayersScoreboard {

    private static final String HANGMAN_PLAYERS_SCOREBOARD_TXT = "hangmanPlayersScoreboard.txt";
    private final List<HangmanPlayerStats> allPlayersStats = new LinkedList<>();

    public HangmanPlayersScoreboard() {
        initAllPlayerStats();
    }

    public void initAllPlayerStats() {
        if (!isFileAvailable(HANGMAN_PLAYERS_SCOREBOARD_TXT)) {
            createScoreboardFile();
        } else {
            getStatsFromFile();
        }
    }

    public void addPlayerStatsToScoreboard(String playerStats) {
        if (isFileAvailable(HANGMAN_PLAYERS_SCOREBOARD_TXT)) {
            String preparedStatsToAdd = preparePlayerStatsToAdd(playerStats);
            allPlayersStats.add(HangmanPlayerStats.createPlayerStats(preparedStatsToAdd));
            addStatsToFile(preparedStatsToAdd);
        } else {
            createScoreboardFile();
            addStatsToFile(playerStats);
        }
    }

    public String getPlayersStatsFromScoreboard() {
        if (allPlayersStats.isEmpty()) {
            return "Scoreboard is empty!";
        } else {
            return preparePlayerStatsToDisplay();
        }
    }

    protected String preparePlayerStatsToDisplay() {
        StringBuilder statsToDisplayBuilder = new StringBuilder();

        for (int i = 0; i < allPlayersStats.size(); i++) {
            statsToDisplayBuilder.append(allPlayersStats.get(i));

            if (i < allPlayersStats.size() - 1) {
                statsToDisplayBuilder.append("\n");
            }
        }

        return statsToDisplayBuilder.toString();
    }

    public void getStatsFromFile() {
        String playersStats;

        try (BufferedReader scoreboardReader = new BufferedReader(new FileReader(HANGMAN_PLAYERS_SCOREBOARD_TXT))){
            while ((playersStats = scoreboardReader.readLine()) != null) {
                addStatsToAllPlayerStatsContainer(playersStats);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void addStatsToAllPlayerStatsContainer(String playerStats) {
        if (!playerStats.contains("empty")) {
            allPlayersStats.add(HangmanPlayerStats.createPlayerStats(playerStats));
        }
    }

    public void addStatsToFile(String preparedPlayerStats) {
        try (BufferedWriter scoreboardWriter = new BufferedWriter(new FileWriter(HANGMAN_PLAYERS_SCOREBOARD_TXT, true))){
            scoreboardWriter.write(preparedPlayerStats);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected String preparePlayerStatsToAdd(String playerStats) {
        LocalDate today = LocalDate.now();
        return today.toString().concat(" | ").concat(playerStats).concat("\n");
    }

    public void createScoreboardFile() {
        try (BufferedWriter scoreboardWriter = new BufferedWriter(new FileWriter(HANGMAN_PLAYERS_SCOREBOARD_TXT))){
            scoreboardWriter.write("");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void removingScoreboardFile() {
        File fileToRemove = new File(HANGMAN_PLAYERS_SCOREBOARD_TXT);

        if (isFileAvailable(HANGMAN_PLAYERS_SCOREBOARD_TXT)) {
            fileToRemove.delete();
            allPlayersStats.clear();
        }
    }

    public String sortPlayerStatsByPlayersNames() {
        if (allPlayersStats.size() > 1) {
            allPlayersStats.sort(Comparator.comparing(HangmanPlayerStats::getPlayerName));
            updateScoreboardFile();

            return "Scoreboard has been sorted alphabetically!";
        } else {
            return "Can't sort scoreboard because there isn't more than 1 player stats!";
        }
    }

    public String sortPlayerStatsByPlayersTotalPoints() {
        if (allPlayersStats.size() > 1) {
            allPlayersStats.sort(Comparator.comparing(HangmanPlayerStats::getTotalPlayerPoints).reversed());
            updateScoreboardFile();

            return "Scoreboard has been sorted by players total points!";
        } else {
            return "Can't sort scoreboard because there isn't more than 1 player stats!";
        }
    }

    public String sortPlayerStatsByDateTheStatsWasAdded() {
        if (allPlayersStats.size() > 1) {
            allPlayersStats.sort(Comparator.comparing(HangmanPlayerStats::getStatsDate).reversed());
            updateScoreboardFile();
            return "Scoreboard has been sorted by player stats adding date!";
        } else {
            return "Can't sort scoreboard because there isn't more than 1 player stats!";
        }
    }

    public void updateScoreboardFile() {
        createScoreboardFile();
        for (HangmanPlayerStats playerStats : allPlayersStats) {
            addStatsToFile(playerStats.toString().concat("\n"));
        }
    }

    public String clearScoreboard() {
        createScoreboardFile();
        allPlayersStats.clear();
        return "Scoreboard has been cleared!";
    }

    public boolean isFileAvailable(String fileName) {
        File fileToCheck = new File(fileName);

        return fileToCheck.exists();
    }

    public List<HangmanPlayerStats> getAllPlayersStats() {
        return allPlayersStats;
    }
}
