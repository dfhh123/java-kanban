package additionalmodules.savemoduls;

import additionalmodules.savemoduls.convertors.StringLineToModelConvertor;
import models.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class TaskFromCsvLoader {
    private Path saveFileDirectory;
    private StringLineToModelConvertor fromCsvToModelConvertor = new StringLineToModelConvertor();
    private final String correctHeader = "id,type,status,description,epic,subtaskIds";

    public TaskFromCsvLoader(Path path) {
        this.saveFileDirectory = path;
    }

    public void setSaveFileDirectory(Path path) {
        saveFileDirectory = path;
    }

    public Path getSaveFileDirectory() {
        return saveFileDirectory;
    }

    public <T extends Task> Stream<T> loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFileDirectory.toFile()))) {
            String headerLine = reader.readLine();
            if (!correctHeader.equals(headerLine)) {
                throw new InputMismatchException("Incorrect header");
            }
            List<T> tasks = reader.lines()
                    .skip(0)
                    .map(line -> {
                        return fromCsvToModelConvertor.<T>convertCsvLineToTask(line);
                    })
                    .filter(Objects::nonNull)
                    .toList();
            return tasks.stream();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
    }
}
