package taskmanager;

import additionalmodules.historymanager.HistoryManager;
import additionalmodules.idgenerator.IdGenerator;
import additionalmodules.savemoduls.TaskFromCsvLoader;
import additionalmodules.savemoduls.TaskToCsvSaver;
import models.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    Path defoultPath = Path.of("src/main/resources/file-backed-task-manager-save.csv");

    private TaskToCsvSaver saver = new TaskToCsvSaver(defoultPath);
    private TaskFromCsvLoader loader = new TaskFromCsvLoader(defoultPath);

    public FileBackedTaskManager(IdGenerator idGenerator, HistoryManager historyManager) {
        super(idGenerator, historyManager);
        uploadDuringCreation();
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
        return subTask;
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeSubTaskById(int id) {
        super.removeSubTaskById(id);
        save();
    }

    @Override
    public Task updateTask(Task updatedTask) {
        super.updateTask(updatedTask);
        save();
        return updatedTask;
    }

    @Override
    public Epic updateEpic(Epic updatedEpic) {
        super.updateEpic(updatedEpic);
        save();
        return updatedEpic;
    }

    @Override
    public SubTask updateSubTask(SubTask updatedSubTask) {
        super.updateSubTask(updatedSubTask);
        save();
        return updatedSubTask;
    }

    private void save() {
        try {
            saver.saveAllData(prepareTasksToSave());
        } catch (Exception e) {
            throw new ManagerSaveException("Failed to save data", e);
        }
    }


    private List<Task> prepareTasksToSave() {
        List<Task> tasks = getAllTasks();
        tasks.addAll(getAllSubTasks());
        tasks.addAll(getAllEpics());
        return tasks;
    }

    private <T extends Task> void appendToMaps(T task) {
        if (task instanceof Epic) {
            epics.put(task.getId(), (Epic) task);
        } else if (task instanceof SubTask) {
            subTusks.put(task.getId(), (SubTask) task);
        } else if (task instanceof Task) {
            tasks.put(task.getId(), (Task) task);
        }
    }

    private void uploadDuringCreation() {
        if (Files.exists(defoultPath)) {
            try {
                load();
            } catch (InputMismatchException e) {
                System.out.println("Input data mismatch " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Failed to load data from file " + e.getMessage());
            }
        }
    }

    private void load() {
        loader.loadDataFromFile()
                .forEach(this::appendToMaps);
    }

    class ManagerSaveException extends RuntimeException {
        public ManagerSaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}