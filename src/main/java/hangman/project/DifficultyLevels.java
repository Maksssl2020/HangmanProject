package hangman.project;

public enum DifficultyLevels {
    EASY(8, 3, 8, 1.0f, 2.0f),
    MEDIUM(5, 6, 10, 2.5f, 3.5f),
    HARD(3, 8, 20, 4.0f, 5.0f);

    private int maxPossibleTriesForCertainDifficultyLevel;
    private int minWordLength;
    private int maxWordLength;
    private float minPointsPerWord;
    private float maxPointsPerWord;

    DifficultyLevels(int maxPossibleTriesForCertainDifficultyLevel, int minWordLength, int maxWordLength, float minPointsPerWord, float maxPointsPerWord) {
        this.maxPossibleTriesForCertainDifficultyLevel = maxPossibleTriesForCertainDifficultyLevel;
        this.minWordLength = minWordLength;
        this.maxWordLength = maxWordLength;
        this.minPointsPerWord = minPointsPerWord;
        this.maxPointsPerWord = maxPointsPerWord;
    }

    public static String displayDifficultyLevels() {
        StringBuilder difficultyLevelsBuilder = new StringBuilder();
        DifficultyLevels[] difficultyLevels = DifficultyLevels.values();

        for (int i = 0; i < difficultyLevels.length; i++) {
            difficultyLevelsBuilder.append(difficultyLevels[i]);

            if (i != difficultyLevels.length - 1) {
                difficultyLevelsBuilder.append("\n");
            }
        }

        return difficultyLevelsBuilder.toString();
    }

    public int getMaxPossibleTriesForCertainDifficultyLevel() {
        return maxPossibleTriesForCertainDifficultyLevel;
    }

    private void setMaxPossibleTriesForCertainDifficultyLevel(int maxPossibleTriesForCertainDifficultyLevel) {
        this.maxPossibleTriesForCertainDifficultyLevel = maxPossibleTriesForCertainDifficultyLevel;
    }

    public int getMinWordLength() {
        return minWordLength;
    }

    public void setMinWordLength(int minWordLength) {
        this.minWordLength = minWordLength;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }

    public void setMaxWordLength(int maxWordLength) {
        this.maxWordLength = maxWordLength;
    }

    public float getMinPointsPerWord() {
        return minPointsPerWord;
    }

    public void setMinPointsPerWord(float minPointsPerWord) {
        this.minPointsPerWord = minPointsPerWord;
    }

    public float getMaxPointsPerWord() {
        return maxPointsPerWord;
    }

    public void setMaxPointsPerWord(float maxPointsPerWord) {
        this.maxPointsPerWord = maxPointsPerWord;
    }
}
