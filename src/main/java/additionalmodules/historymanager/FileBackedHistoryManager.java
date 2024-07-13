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
    private Path saveFileDirectory;

    private TaskToCsvSaver saver;
    private TaskFromCsvLoader loader;

    public FileBackedHistoryManager() {
        setSaveFileDirectory(defoultPath);
        saver = new TaskToCsvSaver(saveFileDirectory);
        loader = new TaskFromCsvLoader(saveFileDirectory);
        loadDataFromCsv();
    }

    public FileBackedHistoryManager(Path path) {
        setSaveFileDirectory(path);
        saver = new TaskToCsvSaver(saveFileDirectory);
        loader = new TaskFromCsvLoader(saveFileDirectory);
        loadDataFromCsv();
    }

    @Override
    public Path getSaveFileDirectory() {
        return saveFileDirectory;
    }

    @Override
    public void setSaveFileDirectory(Path userSaveFileDirectory) {
        saveFileDirectory = userSaveFileDirectory;
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

        ifFileExistsLoadDataToNewHistory(newHistory);
        history.setHistory(newHistory);
    }

    private void ifFileExistsLoadDataToNewHistory(CustomHistoryManagerCollection newHistory) {
        if (Files.exists(saveFileDirectory)) {
            try {
                loader.loadDataFromFile().forEach(newHistory::add);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}