package hangman.project.playersdata;

public class HangmanPlayer {
    private String playerName;
    private HangmanPlayerStats playerStats = new HangmanPlayerStats();

    public HangmanPlayer(String playerName) {
        this.playerName = playerName;
        playerStats.setPlayerName(playerName);
    }

    public String getPlayerPreparedStats() {
        return playerStats.toString();
    }

    public void addWonGameRound() {
        playerStats.addWonGameRound();
    }

    public void addLostGameRound() {
        playerStats.addLostGameRound();
    }

    public void addPoints(float points) {
        playerStats.addPoints(points);
    }

    public String getPlayerName() {
        return playerName;
    }

    protected void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public HangmanPlayerStats getPlayerStats() {
        return playerStats;
    }

    protected void setPlayerStats(HangmanPlayerStats playerStats) {
        this.playerStats = playerStats;
    }
}
