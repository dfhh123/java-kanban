package additionalmodules.historymanager;

import additionalmodules.historymanager.customecollection.CustomHistoryManagerCollection;
import models.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    void removeTask(Task task);

    void setHistory(CustomHistoryManagerCollection history);

    List<Task> getHistory();
}
