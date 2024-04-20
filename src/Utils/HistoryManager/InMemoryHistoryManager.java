package Utils.HistoryManager;

import Models.Task;
import Utils.HistoryManager.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task.equals(history.getLast())) {
            return;
        }
        history.addLast(task.clone());
        if (history.size() >= 10) {
            history.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
