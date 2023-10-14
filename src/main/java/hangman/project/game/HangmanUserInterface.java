package hangman.project.game;

import hangman.project.DifficultyLevels;

import java.text.DecimalFormat;
import java.util.Scanner;

public class HangmanUserInterface {
    private final DecimalFormat pointsFormatter = new DecimalFormat("#.#");
    private String userInput;

    public String askUserToEnterName() {
        System.out.println("Enter your name: ");
        do {
            userInput = new Scanner(System.in).nextLine();
        } while (!isValidUserEnteredName(userInput));

        return userInput;
    }

    public boolean isValidUserEnteredName(String userEnteredName) {
        String userEnteredNameRegex = "[a-zA-Z1-9]+";

        if (userEnteredName.matches(userEnteredNameRegex)) {
            return true;
        } else {
            System.out.println("You entered wrong data! Try again!");
            return false;
        }
    }

    protected void displayGameOptionsMenu() {
        String menu = """
                \n1. Start game
                2. Add new word to game
                3. Remove word from game
                4. Restore default words
                5. Show scoreboard
                6. Clear scoreboard
                7. Sort scoreboard
                8. Quit
                
                Select one of the options:
                """;

        System.out.print(menu);
    }

    protected String askUserToSelectOption() {
        displayGameOptionsMenu();
        do {
            userInput = new Scanner(System.in).nextLine();
        } while (!isUserSelectedOptionValid(userInput));

        return userInput;
    }

    protected boolean isUserSelectedOptionValid(String userSelectedOption) {
        String userSelectedOptionRegex = "[1-8]";

        if (userSelectedOption.matches(userSelectedOptionRegex)) {
            return true;
        } else {
            System.out.println("You selected wrong option! Try again!");
            return false;
        }
    }

    protected char askUserToEnterLetter() {
        System.out.println("Enter a letter: ");
        do {
            userInput = new Scanner(System.in).nextLine();
        } while (!isEnteredLetterValid(userInput));

        return userInput.charAt(0);
    }

    protected boolean isEnteredLetterValid(String userEnteredLetter) {
        String enteredLetterRegex = "[a-zA-Z]";

        if (userEnteredLetter.matches(enteredLetterRegex)) {
            return true;
        } else {
            System.out.println("You entered wrong data! Try again!");
            return false;
        }
    }

    protected String askUserToChooseDifficultyLevel() {
        System.out.println(DifficultyLevels.displayDifficultyLevels());
        System.out.println("Choose difficulty level words file: ");
        do {
            userInput = new Scanner(System.in).nextLine().toUpperCase();
        } while (!isEnteredDifficultyLevelValid(userInput));

        return userInput;
    }

    protected boolean isEnteredDifficultyLevelValid(String userDifficultyLevelChoice) {
        if (isEnteredDifficultyLevelEqualsToAnyFromPossibles(userDifficultyLevelChoice)) {
            return true;
        } else {
            System.out.println("You entered wrong difficulty level! Try again!");
            return false;
        }
    }

    protected boolean isEnteredDifficultyLevelEqualsToAnyFromPossibles(String userDifficultyLevelChoice) {
        try {
            DifficultyLevels.valueOf(userDifficultyLevelChoice);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    protected int askUserToEnterAmountOfPossibleTries(String chosenDifficultyLevel) {
        System.out.println(getMessageAboutMaxPossibleTriesToChoose(chosenDifficultyLevel));
        System.out.println("Enter amount of tries: ");
        do {
            userInput = new Scanner(System.in).nextLine();
        } while (!isValidUserEnteredAmountOfPossibleTries(userInput, chosenDifficultyLevel));

        return Integer.parseInt(userInput);
    }

    protected String getMessageAboutMaxPossibleTriesToChoose(String chosenDifficultyLevel) {
        int maxPossibleTriesForCertainDifficultyLevel = DifficultyLevels.valueOf(chosenDifficultyLevel).getMaxPossibleTriesForCertainDifficultyLevel();
        return String.format("Amount of tries to choose: 1 - %d", maxPossibleTriesForCertainDifficultyLevel);
    }

    protected boolean isValidUserEnteredAmountOfPossibleTries(String userEnteredAmountOfPossibleTries, String chosenDifficultyLevel) {
        String userEnteredAmountOfPossibleTriesRegex = "[1-8]";

        if (userEnteredAmountOfPossibleTries.matches(userEnteredAmountOfPossibleTriesRegex)) {
            int possibleTries = Integer.parseInt(userEnteredAmountOfPossibleTries);
            return isValidAmountOfPossibleTriesDependsOnDifficultyLevel(possibleTries,chosenDifficultyLevel);
        } else {
            System.out.println("You entered invalid data!");
            return false;
        }
    }

    protected boolean isValidAmountOfPossibleTriesDependsOnDifficultyLevel(int possibleTries, String chosenDifficultyLevel) {
        return switch (chosenDifficultyLevel) {
            case "EASY" -> possibleTries <= DifficultyLevels.EASY.getMaxPossibleTriesForCertainDifficultyLevel();
            case "MEDIUM" -> possibleTries <= DifficultyLevels.MEDIUM.getMaxPossibleTriesForCertainDifficultyLevel();
            case "HARD" -> possibleTries <= DifficultyLevels.HARD.getMaxPossibleTriesForCertainDifficultyLevel();
            default -> {
                System.out.println("You entered invalid amount of points!");
                yield false;
            }
        };
    }

    protected String askUserToEnterWordToAdd(String chosenDifficultyLevel) {
        System.out.println(getMessageAboutWordToAddMinAndMaxLength(chosenDifficultyLevel));
        System.out.println("Enter word to add: ");
        do {
            userInput = new Scanner(System.in).nextLine().toLowerCase();
        } while (!isWordToAddValid(userInput, chosenDifficultyLevel));

        return userInput;
    }

    protected String getMessageAboutWordToAddMinAndMaxLength(String chosenDifficultyLevel) {
        int minWordLength = DifficultyLevels.valueOf(chosenDifficultyLevel).getMinWordLength();
        int maxWordLength = DifficultyLevels.valueOf(chosenDifficultyLevel).getMaxWordLength();
        return String.format("For word on %s difficulty level ( minimum word length is %d | maximum word length is %d )", chosenDifficultyLevel, minWordLength, maxWordLength);
    }

    protected boolean isWordToAddValid(String enteredUserWord, String chosenDifficultyLevel) {
        String enteredUserWordRegex = "[a-zA-Z]+";
        int minWordLengthDependsOnChosenDifficultyLevel = DifficultyLevels.valueOf(chosenDifficultyLevel).getMinWordLength();
        int maxWordLengthDependsOnChosenDifficultyLevel = DifficultyLevels.valueOf(chosenDifficultyLevel).getMaxWordLength();
        int enteredUserWordLength = enteredUserWord.length();
        boolean wordLengthResult = enteredUserWordLength >= minWordLengthDependsOnChosenDifficultyLevel && enteredUserWordLength <= maxWordLengthDependsOnChosenDifficultyLevel;

        if (!enteredUserWord.matches(enteredUserWordRegex) || !wordLengthResult) {
            System.out.println("You entered invalid word! Try again!");
            return false;
        } else {
            return true;
        }
    }

    protected Float askUserToEnterAmountOfWordPoints(String chosenDifficultyLevel) {
        System.out.println(getMessageAboutMinAndMaxAmountOfPointsForAddedWord(chosenDifficultyLevel));
        System.out.println("Enter amount of points: ");
        do {
            userInput = new Scanner(System.in).nextLine();
        } while (!isUserEnteredAmountOfPointsToAddedWordValid(userInput, chosenDifficultyLevel));

        return Float.parseFloat(userInput);
    }

    protected String getMessageAboutMinAndMaxAmountOfPointsForAddedWord(String chosenDifficultyLevel) {
        String minAmountOfPoints = pointsFormatter.format(DifficultyLevels.valueOf(chosenDifficultyLevel).getMinPointsPerWord());
        String maxAmountOfPoints = pointsFormatter.format(DifficultyLevels.valueOf(chosenDifficultyLevel).getMaxPointsPerWord());

        return String.format("For word on %s difficulty level ( min points is %s | max points is %s )", chosenDifficultyLevel, minAmountOfPoints, maxAmountOfPoints);
    }

    protected boolean isUserEnteredAmountOfPointsToAddedWordValid(String userEnteredAmountOfPointsToAddedWord, String difficultyLevel) {
        String userEnteredAmountOfPointsToAddedWordRegex = "[1-5][.]*5*";

        if (userEnteredAmountOfPointsToAddedWord.matches(userEnteredAmountOfPointsToAddedWordRegex)) {
            float wordToAddAmountOfPoints = Float.parseFloat(userEnteredAmountOfPointsToAddedWord);
            return isAmountOfPointsCorrectForChosenDifficultyLevel(wordToAddAmountOfPoints, difficultyLevel);
        } else {
            System.out.println("You entered invalid amount of points! Try again!");
            return false;
        }
    }

    protected boolean isAmountOfPointsCorrectForChosenDifficultyLevel(Float wordToAddAmountOfPoints, String difficultyLevel) {
        return switch (difficultyLevel) {
            case "EASY" -> wordToAddAmountOfPoints >= DifficultyLevels.EASY.getMinPointsPerWord() && wordToAddAmountOfPoints <= DifficultyLevels.EASY.getMaxPointsPerWord();
            case "MEDIUM" -> wordToAddAmountOfPoints >= DifficultyLevels.MEDIUM.getMinPointsPerWord() && wordToAddAmountOfPoints <= DifficultyLevels.MEDIUM.getMaxPointsPerWord();
            case "HARD" -> wordToAddAmountOfPoints >= DifficultyLevels.HARD.getMinPointsPerWord() && wordToAddAmountOfPoints <= DifficultyLevels.HARD.getMaxPointsPerWord();
            default -> false;
        };
    }

    protected String askUserToEnterWordToRemove() {
        System.out.println("Enter word that you want to remove: ");
        do {
            userInput = new Scanner(System.in).nextLine();
        } while (!isEnteredUserWordToRemoveValid(userInput));

        return userInput.toLowerCase();
    }

    protected boolean isEnteredUserWordToRemoveValid(String enteredUserWordToRemove) {
        String userWordToRemoveRegex = "[a-zA-Z]+";

        if (enteredUserWordToRemove.matches(userWordToRemoveRegex)) {
            return true;
        } else {
            System.out.println("You entered invalid word! Try again!");
            return false;
        }
    }

    protected void displayScoreboardSortingMethod() {
        String scoreboardSortingMethod = """
                How do you want to sort a scoreboard?
                
                1. Alphabetically
                2. By players total points (descending)
                3. By player stats adding date
                
                Select one of the options:
                """;

        System.out.print(scoreboardSortingMethod);
    }

    protected String askUserToSelectScoreboardSortingMethod() {
        do {
            userInput = new Scanner(System.in).nextLine();
        } while (!isValidUserSelectedScoreboardSortingMethod(userInput));

        return userInput;
    }

    protected boolean isValidUserSelectedScoreboardSortingMethod(String userSelectedSortingMethod) {
        String userSelectedScoreboardOptionRegex = "[1-3]";

        if (userSelectedSortingMethod.matches(userSelectedScoreboardOptionRegex)) {
            return true;
        } else {
            System.out.println("You selected invalid option! Try again!");
            return false;
        }
    }

    protected String askUserToChooseYesOrNo() {
        do {
            userInput = new Scanner(System.in).nextLine();
        } while (!isUserAnswerValid(userInput));

        return userInput;
    }

    protected boolean isUserAnswerValid(String userAnswer) {
        String userAnswerRegex = "[yYnN]";

        if (userAnswer.matches(userAnswerRegex)) {
            return true;
        } else {
            System.out.println("You entered invalid letter! Try again!");
            return false;
        }
    }

    public DecimalFormat getPointsFormatter() {
        return pointsFormatter;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }
}
