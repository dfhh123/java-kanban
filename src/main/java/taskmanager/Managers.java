package taskmanager;

import additionalmodules.historymanager.FileBackedHistoryManager;
import additionalmodules.historymanager.HistoryManager;
import additionalmodules.historymanager.InMemoryHistoryManager;
import additionalmodules.idgenerator.ConsistentIdGenerator;
import additionalmodules.idgenerator.IdGenerator;

import java.nio.file.Path;

public class Managers {
    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager(getDefaultIdGenerator(), getDefaultHistoryManager());
    }

    public static FileBackedTaskManager getDefaultFileBackedTaskManager() {
        return new FileBackedTaskManager(getDefaultIdGenerator(), getFileBackedHistoryManager());
    }

    public static FileBackedTaskManager getPathFileBackedTaskManager(Path taskManagerPath, Path historyManagerPath) {
        return new FileBackedTaskManager(getDefaultIdGenerator(),
                                         getPathFileBackedHistoryManager(historyManagerPath),
                                         taskManagerPath);
    }

    public static HistoryManager getDefaultHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedHistoryManager getPathFileBackedHistoryManager(Path path) {
        return new FileBackedHistoryManager(path);
    }

    public static IdGenerator getDefaultIdGenerator() {
        return new ConsistentIdGenerator();
    }

    public static FileBackedHistoryManager getFileBackedHistoryManager() {
        return new FileBackedHistoryManager();
    }
}
