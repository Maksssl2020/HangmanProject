package hangman.project.playersdata;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HangmanPlayerStats {
    private String playerName;
    private int totalWonGameRounds;
    private int totalLostGameRounds;
    private int totalPlayedRounds;
    private float totalPlayerPoints;
    private String statsDate;
    private static final String numericDataRegex = "(?<dataInformation>\\w+:)\\s(?<statsData>[\\d.*]*\\w*)";
    private static final Pattern statsPattern = Pattern.compile(numericDataRegex);

    public HangmanPlayerStats() {}

    public HangmanPlayerStats(String statsDate, String playerName, int totalPlayedRounds, int totalWonGameRounds, int totalLostGameRounds, float totalPlayerPoints) {
        this.statsDate = statsDate;
        this.playerName = playerName;
        this.totalPlayedRounds = totalPlayedRounds;
        this.totalWonGameRounds = totalWonGameRounds;
        this.totalLostGameRounds = totalLostGameRounds;
        this.totalPlayerPoints = totalPlayerPoints;
    }

    public static HangmanPlayerStats createPlayerStats(String playerStats) {
        String[] separatedPlayerStats = playerStats.split("\\|");
        String statsDate = separatedPlayerStats[0].strip();
        String playerName = parsingTakenPlayerStatsData(separatedPlayerStats[1]);
        int totalPlayedRounds = Integer.parseInt(parsingTakenPlayerStatsData(separatedPlayerStats[2]));
        int totalWonGamesRound = Integer.parseInt(parsingTakenPlayerStatsData(separatedPlayerStats[3]));
        int totalLostGameRounds = Integer.parseInt(parsingTakenPlayerStatsData(separatedPlayerStats[4]));
        float totalPlayerPoints = Float.parseFloat(parsingTakenPlayerStatsData(separatedPlayerStats[5]));

        return new HangmanPlayerStats(statsDate, playerName, totalPlayedRounds, totalWonGamesRound, totalLostGameRounds, totalPlayerPoints);
    }

    private static String parsingTakenPlayerStatsData(String playerStats) {
        Matcher statsMatcher = statsPattern.matcher(playerStats);
        String preparedData;

        if (statsMatcher.find()) {
            preparedData = statsMatcher.group("statsData");
        } else {
            preparedData = "N/A";
        }

        return preparedData;
    }

    public void addWonGameRound() {
        totalWonGameRounds++;
        totalPlayedRounds++;
    }

    public void addLostGameRound() {
        totalLostGameRounds++;
        totalPlayedRounds++;
    }

    public void addPoints(float points) {
        totalPlayerPoints += points;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTotalWonGameRounds() {
        return totalWonGameRounds;
    }

    public void setTotalWonGameRounds(int totalWonGameRounds) {
        this.totalWonGameRounds = totalWonGameRounds;
    }

    public int getTotalLostGameRounds() {
        return totalLostGameRounds;
    }

    public void setTotalLostGameRounds(int totalLostGameRounds) {
        this.totalLostGameRounds = totalLostGameRounds;
    }

    public int getTotalPlayedRounds() {
        return totalPlayedRounds;
    }

    public void setTotalPlayedRounds(int totalPlayedRounds) {
        this.totalPlayedRounds = totalPlayedRounds;
    }

    public float getTotalPlayerPoints() {
        return totalPlayerPoints;
    }

    public void setTotalPlayerPoints(float totalPlayerPoints) {
        this.totalPlayerPoints = totalPlayerPoints;
    }

    public String getStatsDate() {
        return statsDate;
    }

    public void setStatsDate(String statsDate) {
        this.statsDate = statsDate;
    }

    @Override
    public String toString() {
        DecimalFormatSymbols statsFormatterSymbol = new DecimalFormatSymbols(Locale.getDefault());
        statsFormatterSymbol.setDecimalSeparator('.');
        DecimalFormat statsFormatter = new DecimalFormat("#.##", statsFormatterSymbol);

        String preparedPlayerStats = String.format("Player name: %s | Total played rounds: %d | Won rounds: %d | Lost rounds: %d | Total points: %s",
                playerName, totalPlayedRounds, totalWonGameRounds, totalLostGameRounds, statsFormatter.format(totalPlayerPoints));

        if (statsDate == null) {
            return preparedPlayerStats;
        } else {
            return String.format("%s | %s", statsDate, preparedPlayerStats);
        }
    }
}
