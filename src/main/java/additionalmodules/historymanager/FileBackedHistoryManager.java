package additionalmodules.historymanager;

import additionalmodules.historymanager.customecollection.CustomHistoryManagerCollection;
import additionalmodules.savemoduls.TaskFromCsvLoader;
import additionalmodules.savemoduls.TaskToCsvSaver;
import models.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedHistoryManager implements SavableHistoryManager {
    HistoryManager history = new InMemoryHistoryManager();
    private final Path defoultPath = Path.of("src/main/resources/file-backed-history-manager-save.csv");
    private Path saveFileDirectory = defoultPath;

    public Path getSaveFileDirectory() {
        return saveFileDirectory;
    }

    public void setSaveFileDirectory(Path saveFileDirectory) {
        this.saveFileDirectory = saveFileDirectory;
    }

    private TaskToCsvSaver saver = new TaskToCsvSaver(defoultPath);
    private TaskFromCsvLoader loader = new TaskFromCsvLoader(defoultPath);

    public FileBackedHistoryManager() {
        loadDataFromCsv();
    }

    @Override
    public void add(Task task) {
        history.add(task);
        saveDataToCsv();
    }

    @Override
    public void removeTask(Task task) {
        history.removeTask(task);
        saveDataToCsv();
    }

    @Override
    public void setHistory(CustomHistoryManagerCollection history) {
        this.history.setHistory(history);
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    @Override
    public void saveDataToCsv() {
        try {
            saver.saveAllData(getHistory());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void loadDataFromCsv() {
        CustomHistoryManagerCollection newHistory = new CustomHistoryManagerCollection();
        if (Files.exists(defoultPath)) {
            try {
                loader.loadDataFromFile().forEach(newHistory::add);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        history.setHistory(newHistory);
    }
}