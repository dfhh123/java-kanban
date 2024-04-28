package Utils.HistoryManager;

import Models.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    void removeTask(Task task);

    List<Task> getHistory();
}
