package additionalmodules.savemoduls;

import additionalmodules.savemoduls.convertors.ModelToStringConvertor;
import models.Task;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

public class TaskToCsvSaver {
    private Path saveFileDirectory;
    private ModelToStringConvertor toStringConvertor = new ModelToStringConvertor();

    public TaskToCsvSaver(Path path) {
        this.saveFileDirectory = path;
    }

    public Path getSaveFileDirectory() {
        return saveFileDirectory;
    }

    public void setSaveFileDirectory(Path path) {
        saveFileDirectory = path;
    }

    public void saveAllData(List<Task> tasks) throws IOException {
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFileDirectory.toFile()))) {
                writer.write("id,type,status,description,epic,subtaskIds\n");
                saveAllTasks(writer, tasks);
            }
        } catch (IOException e) {
            System.out.println("Failed to save data to file");
        }
    }

    private void saveAllTasks(BufferedWriter writer, List<Task> tasks) {
        tasks.stream().map(toStringConvertor::convaertModelToString).forEachOrdered(task -> {
                    try {
                        writer.append(task);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
