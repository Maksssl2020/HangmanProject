package hangman.project.wordstodraw;

import java.io.*;
import java.util.*;

public class HangmanWordsToDrawFiles {
    private final Set<String> defaultEasyWordsToDraw = new LinkedHashSet<>(Set.of("cat 1.0", "dog 1.0", "city 1.5", "flower 2.0"));
    private final Set<String> defaultMediumWordsToDraw = new LinkedHashSet<>(Set.of("building 2.5", "computer 2.5", "reflector 3.0", "difficulty 3.5"));
    private final Set<String> defaultHardWordsToDraw = new LinkedHashSet<>(Set.of("frizzled 4.0", "thumbscrew 5.0", "syndrome 4.0", "microwave 4.5"));

    protected Map<String, Float> getWordsForGame(String fileName) {
        Map<String, Float> gameWordsAndPoints = new LinkedHashMap<>();

        if (isFileAvailable(fileName)) {
            readsWordsFromFile(fileName, gameWordsAndPoints);
        } else {
            createWordsFiles(fileName);
            readsWordsFromFile(fileName, gameWordsAndPoints);
        }

        return gameWordsAndPoints;
    }

    protected void readsWordsFromFile(String fileName, Map<String, Float> gameWordsAndPoints) {
        String line;

        try (BufferedReader gameWordsReader = new BufferedReader(new FileReader(fileName))){
            while ((line = gameWordsReader.readLine()) != null) {
                String[] wordsAndPoints = line.split("\\s");
                gameWordsAndPoints.put(wordsAndPoints[0], Float.parseFloat(wordsAndPoints[1]));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected void createWordsFiles(String fileName) {
        try (BufferedWriter wordsWriter = new BufferedWriter(new FileWriter(fileName));) {
            switch (fileName) {
                case "easyWords.txt" -> writeWordsToCertainFileDependsOnDifficultyLevel(wordsWriter, defaultEasyWordsToDraw);
                case "mediumWords.txt" -> writeWordsToCertainFileDependsOnDifficultyLevel(wordsWriter, defaultMediumWordsToDraw);
                case "hardWords.txt" -> writeWordsToCertainFileDependsOnDifficultyLevel(wordsWriter, defaultHardWordsToDraw);
                default -> wordsWriter.write("UnknownFile! 0.0");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void writeWordsToCertainFileDependsOnDifficultyLevel(BufferedWriter wordsWriter, Set<String> defaultWords) throws IOException {
        for (String word : defaultWords) {
            wordsWriter.write(word.concat("\n"));
        }
    }

    protected String addWordToCertainFile(String fileName, String word, Float points) {
        if (isFileAvailable(fileName)) {
            String preparedWord = prepareWordToAdd(word, points);
            addWordToFile(fileName, preparedWord);
            return String.format("Word (%s) has been added to %s!", preparedWord, fileName);
        } else {
            return String.format("There isn't file %s!", fileName);
        }
    }

    protected String prepareWordToAdd(String word, Float points) {
        return word.concat(" ").concat(points.toString());
    }

    protected void addWordToFile(String fileName, String preparedWord) {
        try (BufferedWriter wordsWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            wordsWriter.write(preparedWord.concat("\n"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    protected String removeWordFromFile(String fileName, String wordToRemove) {
        if (isFileAvailable(fileName)) {
            List<String> fileWordsLines = getWordsFromFileWithoutWordToRemove(fileName, wordToRemove);
            updatingChosenFile(fileName, fileWordsLines);
            return String.format("Word %s has been removed from %s!", wordToRemove, fileName);
        } else {
            return String.format("There isn't file %s!", fileName);
        }
    }

    protected List<String> getWordsFromFileWithoutWordToRemove(String fileName, String wordToRemove) {
        List<String> lines = new ArrayList<>();

        try {
            BufferedReader wordsReader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = wordsReader.readLine()) != null) {
                if (!line.contains(wordToRemove)) {
                    lines.add(line);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return lines;
    }

    protected void updatingChosenFile(String fileName, List<String> lines) {
        try (BufferedWriter wordsWriter = new BufferedWriter((new FileWriter(fileName)));) {
            for (String wordsLine : lines) {
                wordsWriter.write(wordsLine.concat("\n"));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected String restoreDefaultTextFileWords(String fileName) {
        if (isFileAvailable(fileName)) {
            createWordsFiles(fileName);
            return String.format("Default data in %s has been restored!", fileName);
        } else {
            return String.format("File %s doesn't exist!", fileName);
        }
    }

    protected String removeFile(String fileName) {
        File fileToRemove = new File(fileName);

        if (isFileAvailable(fileName)) {
            fileToRemove.delete();
            return String.format("File %s has been deleted!", fileName);
        } else {
            return String.format("File %s doesn't exist!", fileName);
        }
    }

    protected boolean isFileAvailable(String fileName) {
        File gameFile = new File(fileName);

        return gameFile.exists();
    }

    public Set<String> getDefaultEasyWordsToDraw() {
        return defaultEasyWordsToDraw;
    }

    public Set<String> getDefaultMediumWordsToDraw() {
        return defaultMediumWordsToDraw;
    }

    public Set<String> getDefaultHardWordsToDraw() {
        return defaultHardWordsToDraw;
    }
}
