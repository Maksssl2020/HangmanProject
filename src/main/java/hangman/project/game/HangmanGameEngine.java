package hangman.project.game;

import hangman.project.DifficultyLevels;
import hangman.project.wordstodraw.HangmanWordsToDraw;
import hangman.project.Pair;
import hangman.project.playersdata.HangmanPlayer;
import hangman.project.playersdata.HangmanPlayersScoreboard;

public class HangmanGameEngine {
    private final HangmanWordsToDraw wordsToDraw = new HangmanWordsToDraw();
    private final StringBuilder coveredDrawnWord = new StringBuilder();
    private String drawnWord;
    private float drawnWordPoints;
    private HangmanPlayer hangmanPlayer;
    private int possibleWrongGuesses;
    private int amountOfTries = 0;
    private HangmanPlayersScoreboard scoreboard = new HangmanPlayersScoreboard();
    private DifficultyLevels gameDifficultyLevel;

    public void addPlayerToGame(String playerName) {
        hangmanPlayer = new HangmanPlayer(playerName);
    }

    protected void drawnWordToGuess() {
        String difficultyLevel = gameDifficultyLevel.toString();
        Pair<String, Float> drawnWordAndWordPoints = wordsToDraw.getRandomWord(difficultyLevel);

        drawnWord = drawnWordAndWordPoints.getFirst();
        drawnWordPoints = drawnWordAndWordPoints.getSecond();
    }

    protected void createCoveredWord(String drawnWord) {
        coveredDrawnWord.append("_".repeat(drawnWord.length()));
    }

    protected String checkingEnteredLetter(char drawnWordLetter) {
        amountOfTries++;
        boolean checkingResult = isLetterInTheWord(drawnWordLetter);
        String coveredDrawnWordToDisplay = getCoveredDrawnWordToDisplay();;

        if (checkingResult) {
            return coveredDrawnWordToDisplay;
        } else {
            possibleWrongGuesses--;
            return String.format("There isn't %c in the drawn word!%n%s", drawnWordLetter, coveredDrawnWordToDisplay);
        }
    }

    protected String getCoveredDrawnWordToDisplay() {
        return coveredDrawnWord.toString();
    }

    protected boolean isLetterInTheWord(char drawnWordLetter) {
        boolean result = false;

        for (int i = 0; i < drawnWord.length(); i++) {
            if (drawnWord.charAt(i) == drawnWordLetter) {
                coveredDrawnWord.replace(i, i+1, String.valueOf(drawnWordLetter));
                result = true;
            }
        }

        return result;
    }

    protected String checkGameResult() {
        String checkedAmountOfTriesMessage = checkAmountOfTries();
        String coveredDrawnWordToCheck = coveredDrawnWord.toString();

        if (!coveredDrawnWordToCheck.contains("_")) {
            hangmanPlayer.addWonGameRound();
            hangmanPlayer.addPoints(drawnWordPoints);
            coveredDrawnWord.delete(0, coveredDrawnWord.length());
            return "Congratulations, you won the game!";
        } else if (checkedAmountOfTriesMessage.contains("Game Over!")) {
            coveredDrawnWord.delete(0, coveredDrawnWord.length());
        }

        return checkedAmountOfTriesMessage;
    }

    protected String checkAmountOfTries() {
        String tryWord = possibleWrongGuesses == 1 ? "try" : "tries";

        if (possibleWrongGuesses > 0) {
            return String.format("You have %d %s!", possibleWrongGuesses, tryWord);
        } else {
            hangmanPlayer.addLostGameRound();
            return "You don't have any tries! Game Over!";
        }
    }

    protected String getPlayerGameStats() {
        return hangmanPlayer.getPlayerPreparedStats();
    }

    protected String addNewWordToGame(String word, Float wordPoints, String difficultyLevel) {
        return wordsToDraw.addNewWordToGame(word, wordPoints, difficultyLevel);
    }

    protected String removingWordFromTheFile(String wordToRemove) {
        return wordsToDraw.removingWordFromTheFile(wordToRemove);
    }

    protected String restoreDefaultTextFileWords(String fileName) {
        return wordsToDraw.restoreDefaultTextFileWords(fileName);
    }

    public void addPlayerStatsToScoreboard() {
        scoreboard.addPlayerStatsToScoreboard(hangmanPlayer.getPlayerPreparedStats());
    }

    public String sortPlayerStatsByPlayersNames() {
        return scoreboard.sortPlayerStatsByPlayersNames();
    }

    public String sortPlayerStatsByPlayersTotalPoints() {
        return scoreboard.sortPlayerStatsByPlayersTotalPoints();
    }

    public String sortPlayerStatsByDateTheStatsWasAdded() {
        return scoreboard.sortPlayerStatsByDateTheStatsWasAdded();
    }

    public String getPlayersStatsFromScoreboard() {
        return scoreboard.getPlayersStatsFromScoreboard();
    }

    public String clearScoreboard() {
        return scoreboard.clearScoreboard();
    }

    protected HangmanWordsToDraw getWordsToDraw() {
        return wordsToDraw;
    }

    public StringBuilder getCoveredDrawnWord() {
        return coveredDrawnWord;
    }

    protected String getDrawnWord() {
        return drawnWord;
    }

    protected void setDrawnWord(String drawnWord) {
        this.drawnWord = drawnWord;
    }

    protected float getDrawnWordPoints() {
        return drawnWordPoints;
    }

    protected void setDrawnWordPoints(float drawnWordPoints) {
        this.drawnWordPoints = drawnWordPoints;
    }

    protected int getPossibleWrongGuesses() {
        return possibleWrongGuesses;
    }

    protected void setPossibleWrongGuesses(int possibleWrongGuesses) {
        this.possibleWrongGuesses = possibleWrongGuesses;
    }

    public HangmanPlayer getHangmanPlayer() {
        return hangmanPlayer;
    }

    public void setHangmanPlayer(HangmanPlayer hangmanPlayer) {
        this.hangmanPlayer = hangmanPlayer;
    }

    protected int getAmountOfTries() {
        return amountOfTries;
    }

    protected void setAmountOfTries(int amountOfTries) {
        this.amountOfTries = amountOfTries;
    }

    protected DifficultyLevels getGameDifficultyLevel() {
        return gameDifficultyLevel;
    }

    protected void setGameDifficultyLevel(String gameDifficultyLevel) {
        this.gameDifficultyLevel = DifficultyLevels.valueOf(gameDifficultyLevel.toUpperCase());
    }

    protected void setGameDifficultyLevel(DifficultyLevels gameDifficultyLevel) {
        this.gameDifficultyLevel = gameDifficultyLevel;
    }

    public HangmanPlayersScoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(HangmanPlayersScoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }
}
