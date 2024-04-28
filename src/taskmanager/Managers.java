package taskmanager;

import additionalmodules.historymanager.HistoryManager;
import additionalmodules.historymanager.InMemoryHistoryManager;
import additionalmodules.idgenerator.ConsistentIdGenerator;
import additionalmodules.idgenerator.IdGenerator;

public class Managers {
    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager(getDefaultIdGenerator(), getDefaultHistoryManager());
    }

    public static HistoryManager getDefaultHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public static IdGenerator getDefaultIdGenerator() {
        return new ConsistentIdGenerator();
    }
}
