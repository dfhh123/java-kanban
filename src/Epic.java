package org.example;

import java.util.*;

public class Epic extends Task {
    Map<Integer, SimpleTask> epic = new HashMap<>();
    Sonar treeScanner = new TreeScanner();

    public Epic(String description, TackCondition tackCondition, int id) {
        super(description, tackCondition, id);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epic=" + epic +
                '}';
    }

    private void updateSelfCondition() {
        if (epic.isEmpty()){
            setTackCondition(TackCondition.NEW);
            return;
        }

        for (Map.Entry <Integer, SimpleTask> entry : epic.entrySet()) {
            int newCount = 0;
            int doneCount = 0;
            int InProgressCount = 0;

            SimpleTask obj = entry.getValue();

            switch (obj.getTackCondition()){
                case NEW -> newCount++;
                case IN_PROGRESS -> InProgressCount++;
                case DONE -> doneCount++;
            }

            if (doneCount == epic.size()){
                setTackCondition(TackCondition.DONE);
            } else {
                setTackCondition(TackCondition.IN_PROGRESS);
            }
        }
    }

    public Objective getSubtaskById(int id) {
        if (epic.containsKey(id)) {
            return epic.get(id);
        }

        throw new NoSuchElementException();
    }

    public boolean doesContains (int id) {
        return epic.containsKey(id);
    }

    public Map<Integer, SimpleTask> getTasks() {
        return epic;
    }

    public void addSubtask(SimpleTask subtask, int subtaskId) {
        epic.put(subtaskId, subtask);
        updateSelfCondition();
    }

    public void removeSubtaskById(int id) {
        if (doesContains(id)) {
            epic.remove(id);
            updateSelfCondition();
        } else {
            throw new NoSuchElementException();
        }
    }
}