package hangman.project.wordstodraw;

import hangman.project.Pair;

import java.util.*;

public class HangmanWordsToDraw {
    private static final String EASY_WORDS_TXT = "easyWords.txt";
    private static final String MEDIUM_WORDS_TXT = "mediumWords.txt";
    private static final String HARD_WORDS_TXT = "hardWords.txt";
    private Map<String, Float> easyPossibleWordsToDraw = new LinkedHashMap<>();
    private Map<String, Float> mediumPossibleWordsToDraw = new LinkedHashMap<>();
    private Map<String, Float> hardPossibleWordsToDraw = new LinkedHashMap<>();
    private final Map<String, List<Integer>> drawnWordsIndexes = new LinkedHashMap<>();
    private final Random randomWordIndex = new Random();
    private final HangmanWordsToDrawFiles wordsFiles = new HangmanWordsToDrawFiles();

    public HangmanWordsToDraw() {
        getPossibleWordsToDrawFromFiles();
        initDrawnWordsIndexes();
    }

    protected void getPossibleWordsToDrawFromFiles() {
        easyPossibleWordsToDraw = wordsFiles.getWordsForGame(EASY_WORDS_TXT);
        mediumPossibleWordsToDraw = wordsFiles.getWordsForGame(MEDIUM_WORDS_TXT);
        hardPossibleWordsToDraw = wordsFiles.getWordsForGame(HARD_WORDS_TXT);
    }

    protected void initDrawnWordsIndexes() {
        drawnWordsIndexes.put("EASY", new ArrayList<>());
        drawnWordsIndexes.put("MEDIUM", new ArrayList<>());
        drawnWordsIndexes.put("HARD", new ArrayList<>());
    }

    public Pair<String, Float> getRandomWord(String difficultyLevel) {
        return getRandomWordDependsOnDifficultyLevel(difficultyLevel);
    }

    protected Pair<String, Float> getRandomWordDependsOnDifficultyLevel(String difficultyLevel) {
        return switch (difficultyLevel) {
            case "EASY" -> getRandomWordForCertainDifficultyLevel(difficultyLevel, easyPossibleWordsToDraw);
            case "MEDIUM" -> getRandomWordForCertainDifficultyLevel(difficultyLevel, mediumPossibleWordsToDraw);
            case "HARD" -> getRandomWordForCertainDifficultyLevel(difficultyLevel, hardPossibleWordsToDraw);
            default -> Pair.of("Unknown", 0f);
        };
    }

    protected Pair<String, Float> getRandomWordForCertainDifficultyLevel(String difficultyLevel, Map<String, Float> certainDifficultyLevelWordsToDraw) {
        int randomIndex = getRandomIndexToDrawWord(difficultyLevel, certainDifficultyLevelWordsToDraw);
        String drawnWord = new ArrayList<>(certainDifficultyLevelWordsToDraw.keySet()).get(randomIndex);

        return Pair.of(drawnWord, certainDifficultyLevelWordsToDraw.get(drawnWord));
    }

    protected int getRandomIndexToDrawWord(String difficultyLevel, Map<String, Float> certainDifficultyLevelWordsToDraw) {
        int randomIndex;

        if (drawnWordsIndexes.get(difficultyLevel).size() == certainDifficultyLevelWordsToDraw.size()) {
            drawnWordsIndexes.get(difficultyLevel).clear();
        }

        do {
            randomIndex = randomWordIndex.nextInt(certainDifficultyLevelWordsToDraw.size());
        } while (drawnWordsIndexes.get(difficultyLevel).contains(randomIndex));
        drawnWordsIndexes.get(difficultyLevel).add(randomIndex);

        return randomIndex;
    }

    public String addNewWordToGame(String word, Float wordPoints, String difficultyLevel) {
       if (checkWordDifficultyLevel(word).contains("There isn't")) {
           return addingWordToFileDependsOnDifficultyLevel(word, wordPoints, difficultyLevel);
       } else {
           return String.format("Cannot add %s because it is already in the files!", word);
       }
    }

    protected String addingWordToFileDependsOnDifficultyLevel(String word, Float wordPoints, String difficultyLevel) {
        String fileName = getCertainWordsFileName(difficultyLevel);
        String addingResult = wordsFiles.addWordToCertainFile(fileName, word, wordPoints);

        if (addingResult.contains("added")) {
            updateWordsContainersForCertainDifficultyLevel(difficultyLevel);
        }
        return addingResult;
    }

    protected void updateWordsContainersForCertainDifficultyLevel(String difficultyLevel) {
        switch (difficultyLevel) {
            case "EASY" -> easyPossibleWordsToDraw = wordsFiles.getWordsForGame(EASY_WORDS_TXT);
            case "MEDIUM" -> mediumPossibleWordsToDraw = wordsFiles.getWordsForGame(MEDIUM_WORDS_TXT);
            case "HARD" -> hardPossibleWordsToDraw = wordsFiles.getWordsForGame(HARD_WORDS_TXT);
        }
    }

    public String removingWordFromTheFile(String wordToRemove) {
        String resultMessageAfterCheckingWord = checkWordDifficultyLevel(wordToRemove);

        if (!resultMessageAfterCheckingWord.contains("There isn't")) {
            String wordToRemoveFile = getCertainWordsFileName(resultMessageAfterCheckingWord);
            String wordDifficultyLevel = resultMessageAfterCheckingWord;

            resultMessageAfterCheckingWord = wordsFiles.removeWordFromFile(wordToRemoveFile, wordToRemove);
            updateWordsContainersForCertainDifficultyLevel(wordDifficultyLevel);
        }

        return resultMessageAfterCheckingWord;
    }

    protected String checkWordDifficultyLevel(String word) {
        String result;

        if (easyPossibleWordsToDraw.containsKey(word)) {
            result = "EASY";
        } else if (mediumPossibleWordsToDraw.containsKey(word)) {
            result = "MEDIUM";
        } else if (hardPossibleWordsToDraw.containsKey(word)) {
            result = "HARD";
        } else {
            result = String.format("There isn't word %s in any file!", word);
        }

        return result;
    }

    public String restoreDefaultTextFileWords(String chosenDifficultyLevel) {
        String fileName = getCertainWordsFileName(chosenDifficultyLevel);
        String resultMessage = wordsFiles.restoreDefaultTextFileWords(fileName);
        updateWordsContainersForCertainDifficultyLevel(chosenDifficultyLevel);

        return resultMessage;
    }

    public String getCertainWordsFileName(String chosenDifficulty) {
        return switch (chosenDifficulty) {
            case "EASY" -> EASY_WORDS_TXT;
            case "MEDIUM" -> MEDIUM_WORDS_TXT;
            case "HARD" -> HARD_WORDS_TXT;
            default -> "Invalid difficulty level!";
        };
    }

    public Map<String, Float> getEasyPossibleWordsToDraw() {
        return easyPossibleWordsToDraw;
    }

    public void setEasyPossibleWordsToDraw(Map<String, Float> easyPossibleWordsToDraw) {
        this.easyPossibleWordsToDraw = easyPossibleWordsToDraw;
    }

    public Map<String, Float> getMediumPossibleWordsToDraw() {
        return mediumPossibleWordsToDraw;
    }

    public void setMediumPossibleWordsToDraw(Map<String, Float> mediumPossibleWordsToDraw) {
        this.mediumPossibleWordsToDraw = mediumPossibleWordsToDraw;
    }

    public Map<String, Float> getHardPossibleWordsToDraw() {
        return hardPossibleWordsToDraw;
    }

    public void setHardPossibleWordsToDraw(Map<String, Float> hardPossibleWordsToDraw) {
        this.hardPossibleWordsToDraw = hardPossibleWordsToDraw;
    }

    protected Random getRandomWordIndex() {
        return randomWordIndex;
    }

    protected HangmanWordsToDrawFiles getWordsFiles() {
        return wordsFiles;
    }

    public Map<String, List<Integer>> getDrawnWordsIndexes() {
        return drawnWordsIndexes;
    }
}
