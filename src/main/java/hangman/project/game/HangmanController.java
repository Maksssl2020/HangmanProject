package hangman.project.game;

public class HangmanController {
    private HangmanGameEngine gameEngine;
    private HangmanUserInterface userInterface;
    private String checkedGameResult;
    private String chosenDifficultyLevel;
    private int chosenAmountOfPossibleTries;
    private String userName;

    public HangmanController() {
        gameEngine = new HangmanGameEngine();
        userInterface = new HangmanUserInterface();
    }

    public void startTheHangmanGame() {
        String selectedOptionMessage;

        do {
            String userEnteredOption = userInterface.askUserToSelectOption();
            selectedOptionMessage = checkUserEnteredOption(userEnteredOption);
            System.out.println(selectedOptionMessage);
        } while (!selectedOptionMessage.contains("Goodbye!"));
    }

    protected String checkUserEnteredOption(String userEnteredOption) {
        return switch (userEnteredOption) {
            case "1" -> startHangmanRound();
            case "2" -> addNewWordToChosenDifficultyLevelWordsFile();
            case "3" -> removingChosenWordFromFiles();
            case "4" -> restoringDefaultWordsInCertainFile();
            case "5" -> gameEngine.getPlayersStatsFromScoreboard();
            case "6" -> clearingScoreboard();
            case "7" -> choosingSortingScoreboardMethod();
            case "8" -> quittingTheGame();
            default -> "Invalid option!";
        };
    }

    private String startHangmanRound() {
        String userChoices;
        userName = userInterface.askUserToEnterName();
        gameEngine.addPlayerToGame(userName);

        do {
            setUserChosenGameSetting();
            drawnWordToGuess();
            enteringLetters();
            userChoices = continuingTheGameRound();
        } while (userChoices.contains("continue"));

        return userChoices;
    }

    private void enteringLetters() {
        do {
            checkedGameResult = userInteraction();
            System.out.println(checkedGameResult);
        } while (!checkGameResultMessage(checkedGameResult));
        System.out.println(gameEngine.getPlayerGameStats());
    }

    private void setUserChosenGameSetting() {
        chosenDifficultyLevel = userInterface.askUserToChooseDifficultyLevel();
        gameEngine.setGameDifficultyLevel(chosenDifficultyLevel);
        chosenAmountOfPossibleTries = userInterface.askUserToEnterAmountOfPossibleTries(chosenDifficultyLevel);
        gameEngine.setPossibleWrongGuesses(chosenAmountOfPossibleTries);
    }

    private void drawnWordToGuess() {
        gameEngine.drawnWordToGuess();
        gameEngine.createCoveredWord(gameEngine.getDrawnWord());
        System.out.println(gameEngine.getCoveredDrawnWordToDisplay());
    }

    protected String continuingTheGameRound() {
        System.out.println("Do you want to continue game? (Y/N)");
        String userChoice = userInterface.askUserToChooseYesOrNo();

        if ("y".equalsIgnoreCase(userChoice)) {
            return "Game will be continue!";
        } else {
            addingStatsToScoreboardFile();
            return "Quitting this round!";
        }
    }

    protected void addingStatsToScoreboardFile() {
        System.out.println("Do you want to save current score? (Y/N)");
        String userAnswer = userInterface.askUserToChooseYesOrNo();

        if ("y".equalsIgnoreCase(userAnswer)) {
            gameEngine.addPlayerStatsToScoreboard();
        }
    }

    private String addNewWordToChosenDifficultyLevelWordsFile() {
        String chosenDifficultyLevel = userInterface.askUserToChooseDifficultyLevel();
        String userEnteredWordToAdd = userInterface.askUserToEnterWordToAdd(chosenDifficultyLevel);
        Float chosenAmountOfPoints = userInterface.askUserToEnterAmountOfWordPoints(chosenDifficultyLevel);

        return gameEngine.addNewWordToGame(userEnteredWordToAdd, chosenAmountOfPoints, chosenDifficultyLevel);
    }

    private String removingChosenWordFromFiles() {
        String chosenWordToRemove = userInterface.askUserToEnterWordToRemove();

        return gameEngine.removingWordFromTheFile(chosenWordToRemove);
    }

    private String userInteraction() {
        char enteredUserLetter = userInterface.askUserToEnterLetter();
        String checkingEnteredLetterResult = gameEngine.checkingEnteredLetter(enteredUserLetter);
        System.out.println(checkingEnteredLetterResult);

        return gameEngine.checkGameResult();
    }

    protected boolean checkGameResultMessage(String checkedGameResult) {
        return checkedGameResult.contains("you won") || checkedGameResult.contains("Game Over");
    }

    protected String restoringDefaultWordsInCertainFile() {
        System.out.println("Are you sure to restore default words? (Y/N)");
        String userAnswer = userInterface.askUserToChooseYesOrNo();

        if ("y".equalsIgnoreCase(userAnswer)) {
            String chosenDifficultyLevel = userInterface.askUserToChooseDifficultyLevel();
            return gameEngine.restoreDefaultTextFileWords(chosenDifficultyLevel);
        } else {
            return "Cancel restoring default data!";
        }
    }

    protected String clearingScoreboard() {
        System.out.println("Are you sure to clear scoreboard? (Y/N)");
        String userChoice = userInterface.askUserToChooseYesOrNo();

        if (userChoice.equalsIgnoreCase("y")) {
            return gameEngine.clearScoreboard();
        } else {
            return "Clearing scoreboard has been canceled!";
        }
    }

    protected String choosingSortingScoreboardMethod() {
        userInterface.displayScoreboardSortingMethod();
        String userChoice = userInterface.askUserToSelectScoreboardSortingMethod();

        return sortingByUserChosenMethod(userChoice);
    }

    protected String sortingByUserChosenMethod(String userChoice) {
        return switch (userChoice) {
            case "1" -> gameEngine.sortPlayerStatsByPlayersNames();
            case "2" -> gameEngine.sortPlayerStatsByPlayersTotalPoints();
            case "3" -> gameEngine.sortPlayerStatsByDateTheStatsWasAdded();
            default -> "Invalid data!";
        };
    }

    protected String quittingTheGame() {
        System.out.println("Are you sure to quit the game? (Y/N)");
        String userAnswer = userInterface.askUserToChooseYesOrNo();

        if ("y".equalsIgnoreCase(userAnswer)) {
            return "Thanks for playing! Goodbye!";
        } else {
            return "Cancel quitting the game!";
        }
    }
}
