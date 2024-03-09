package TaskManager;

import Models.*;
import Utils.HistoryManager.HistoryManager;
import Utils.IdGenerator.ConsistentIdGenerator;
import Utils.IdGenerator.IdGenerator;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, SubTask> subTusks = new HashMap<>();

    private IdGenerator idGenerator;
    private HistoryManager historyManager;

    public InMemoryTaskManager(IdGenerator idGenerator, HistoryManager historyManager) {
        this.idGenerator = idGenerator;
        this.historyManager = historyManager;
    }

    private void updateEpicStatus(int currentEpicId) {
        Epic currentEpic = epics.get(currentEpicId);

        int newCount = 0;
        int doneCount = 0;
        int InProgressCount = 0;

        if (currentEpic.getSubTusksIdes().isEmpty()) {
            currentEpic.setStatus(Statuses.NEW);
            return;
        }

        List<Integer> idList = currentEpic.getSubTusksIdes();
        for (Integer currentId : idList) {
            if (subTusks.containsKey(currentId)) {
                SubTask currentSubTask = subTusks.get(currentId);

                switch (currentSubTask.getStatus()) {
                    case Statuses.NEW -> newCount++;
                    case Statuses.IN_PROGRESS -> InProgressCount++;
                    case Statuses.DONE -> doneCount++;
                }

                if (doneCount == idList.size()) {
                    currentEpic.setStatus(Statuses.DONE);
                } else {
                    currentEpic.setStatus(Statuses.IN_PROGRESS);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "TaskManager{" +
                "tasks=" + tasks +
                ", epics=" + epics +
                ", subTusks=" + subTusks +
                ", idGenerator=" + idGenerator +
                '}';
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    //методы для тасков
    @Override
    public Task getTaskDyId(int id) {
        if (tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
            return tasks.get(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public Task createTask(Task newTask) {
        newTask.setId(idGenerator.createId());
        tasks.put(newTask.getId(), newTask);
        return newTask;
    }

    @Override
    public void removeTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Task updateTask(Task updatedTask) {
        tasks.put(updatedTask.getId(), updatedTask);
        return updatedTask;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }


    //методы для эпиков
    @Override
    public void removeAllEpics() {
        epics.clear();
        for (Map.Entry<Integer, SubTask> entry: subTusks.entrySet()) {
            SubTask currentSuTusk = entry.getValue();
            currentSuTusk.setLinkedEpicId(-1);
        }
    }

    @Override
    public Epic createEpic(Epic newEpic) {
        newEpic.setId(idGenerator.createId());
        epics.put(newEpic.getId(), newEpic);
        return newEpic;
    }

    @Override
    public void removeEpicById(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Epic updateEpic(Epic updatedEpic) {
        epics.put(updatedEpic.getId(), updatedEpic);
        return updatedEpic;
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Epic getEpicDyId(int id) {
        if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));;
            return epics.get(id);
        } else {
            throw new NoSuchElementException();
        }
    }


    //методы для сабтасков
    @Override
    public SubTask getSubTuskById(int id) {
        if (subTusks.containsKey(id)) {
            historyManager.add(subTusks.get(id));
            return subTusks.get(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void removeAllSubTasks() {
        subTusks.clear();
        for (Map.Entry<Integer, Epic> entry: epics.entrySet()) {
            Epic currentEpic = entry.getValue();
            currentEpic.setSubTusksIdes(List.of());
            updateEpicStatus(currentEpic.getId());
        }
    }

    @Override
    public SubTask createSubTask(SubTask newSubTask) {
        newSubTask.setId(idGenerator.createId());
        subTusks.put(newSubTask.getId(), newSubTask);
        updateEpicStatus(newSubTask.getLinkedEpicId());
        return newSubTask;
    }

    @Override
    public void removeSubTaskById(int id) {
        if (subTusks.containsKey(id)) {
            int epicId = subTusks.get(id).getLinkedEpicId();
            subTusks.remove(id);
            updateEpicStatus(epicId);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public SubTask updateSubTask(SubTask updatedSubTask) {
        subTusks.put(updatedSubTask.getId(), updatedSubTask);
        updateEpicStatus(updatedSubTask.getLinkedEpicId());
        return updatedSubTask;
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTusks.values());
    }

    @Override
    public List<SubTask> getAllEpicSubTasks(int epicId) {
        if (epics.containsKey(epicId)) {
            List<Integer> idesList = epics.get(epicId).getSubTusksIdes();
            List<SubTask> subTasksList = new ArrayList<>();

            for (Integer currentId : idesList) {
                if (subTusks.containsKey(currentId)) {
                    subTasksList.add(subTusks.get(currentId));
                }
            }
            return subTasksList;
        } else {
            throw new NoSuchElementException();
        }
    }
}