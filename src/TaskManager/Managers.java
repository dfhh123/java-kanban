package TaskManager;

import Utils.HistoryManager.HistoryManager;
import Utils.HistoryManager.InMemoryHistoryManager;
import Utils.IdGenerator.ConsistentIdGenerator;
import Utils.IdGenerator.IdGenerator;

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
