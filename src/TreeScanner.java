package org.example;

import java.util.Map;
import java.util.NoSuchElementException;

public class TreeScanner implements Sonar {

    @Override
    public Objective findTask(int taskId, Map<Integer, Objective> objectives) {
        try {
            return findInMain(taskId, objectives);
        } catch (NoSuchElementException e) {
            return findInEpics(taskId, objectives);
        }
    }

    @Override
    public Objective findAndReturnTaskContainer(int taskID, Map<Integer, Objective> objectives) {
        try {
            return findAndReturnTaskContainerInMain(taskID, objectives);
        } catch (NoSuchElementException e) {
            System.out.println("Запрошенной задачи на заданному id не существует");
        }
        throw new NoSuchElementException();
    }

    private Objective findAndReturnTaskContainerInMain(int id, Map<Integer, Objective> objectives) {
        if (objectives.containsKey(id)) {
            return objectives.get(id);
        } else {
            return findAndReturnTaskContainerInEpics(id, objectives);
        }
    }

    private Objective findAndReturnTaskContainerInEpics(int id, Map<Integer, Objective> objectives){
        for (Map.Entry<Integer, Objective> entry : objectives.entrySet()) {
            Objective obj = entry.getValue();
            if (obj instanceof Epic) {
                try {
                    if (((Epic) obj).doesContains(id)) {
                        return obj;
                    }
                } catch (NoSuchElementException e) {
                    continue;
                }
            }
        }
        throw new NoSuchElementException("нет задачи в эпиках");
    }

    private Objective findInMain(int id, Map<Integer, Objective> objectives) {
        for (Map.Entry<Integer, Objective> entry : objectives.entrySet()) {
            if (entry.getValue().getId() == id) {
                return entry.getValue();
            }
        }

        throw new NoSuchElementException();
    }

    private Objective findInEpics(int id, Map<Integer, Objective> objectives) {
        for (Map.Entry<Integer, Objective> entry : objectives.entrySet()) {
            Objective obj = entry.getValue();
            if (obj instanceof Epic) {
                try {
                    return ((Epic) obj).getSubtaskById(id);
                } catch (NoSuchElementException e) {
                    continue;
                }
            }
        }
        throw new NoSuchElementException();
    }
}