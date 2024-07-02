package additionalmodules.historymanager;

import additionalmodules.historymanager.customecollection.CustomHistoryManagerCollection;
import models.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private CustomHistoryManagerCollection history = new CustomHistoryManagerCollection();

    @Override
    public void add(Task task) {
        history.add(task.clone());
    }

    public void setHistory(CustomHistoryManagerCollection history) {
        this.history = history;
    }

    @Override
    public void removeTask(Task task) {
        history.removeTask(task);
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistoryAsList();
    }
}