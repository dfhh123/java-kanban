package org.example;

import java.util.*;

public class TaskManager {

    private Map<Integer, Objective> objectives = new HashMap<>();
    private Sonar treeScanner = new TreeScanner();

    Map<Integer, Objective> getAllTasks() {
        return objectives;
    }

    private void voidFindTask(int taskId) {
    }

    @Override
    public String toString() {
        return "TaskManager{" +
                "objectives=" + objectives + '}';
    }

    Objective getTaskDyId(Integer id) {
        Objective currentTask = null;

        currentTask = treeScanner.findTask(id,objectives);

        return currentTask;
    }

    public Objective returnEpicDyId(int id) {
        Objective obj = treeScanner.findAndReturnTaskContainer(id, objectives);
        if (obj instanceof Epic) {
            return obj;
        } else {
            throw new NoSuchElementException("Запрошенного эпика не существует");
        }
    }

    void removeAllTasks() {
        Iterator<Map.Entry<Integer, Objective>> iterator = objectives.entrySet().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }


    void removeById(Integer id) {
        if (objectives.containsKey(id)) {
            objectives.remove(id);
        } else {
            Epic objective = (Epic) treeScanner.findAndReturnTaskContainer(id, objectives);
            objective.removeSubtaskById(id);
        }
    }

    void createTask(Objective objective) {
        objectives.put(objective.getId(), objective);
    }

    void update(Objective objective) {
        removeById(objective.getId());
        createTask(objective);
    }

}