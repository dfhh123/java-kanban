package TaskManager;

import Models.*;
import Utils.HistoryManager.HistoryManager;
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
        if (newTask.getId() == null) {
            newTask.setId(generateCorrectId());
            tasks.put(newTask.getId(), newTask);
            return newTask;
        } else {
            if (subTusks.containsKey(newTask.getId()) || epics.containsKey(newTask.getId())) {
                throw new IllegalArgumentException();
            }
            if (!tasks.containsKey(newTask.getId())) {
                tasks.put(newTask.getId(), newTask);
                return newTask;
            } else {
                throw new IllegalArgumentException();
            }
        }
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
        for (Map.Entry<Integer, SubTask> entry : subTusks.entrySet()) {
            SubTask currentSuTusk = entry.getValue();
            currentSuTusk.setLinkedEpicId(-1);
        }
    }

    @Override
    public Epic createEpic(Epic newEpic) {
        if (newEpic.getId() == null) {
            newEpic.setId(generateCorrectId());
            epics.put(newEpic.getId(), newEpic);
            return newEpic;
        } else {
            if (subTusks.containsKey(newEpic.getId()) || tasks.containsKey(newEpic.getId())) {
                throw new IllegalArgumentException();
            }
            if (!epics.containsKey(newEpic.getId())) {
                tasks.put(newEpic.getId(), newEpic);
                return newEpic;
            } else {
                throw new IllegalArgumentException();
            }
        }
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
        if (subTusks.containsKey(updatedEpic.getId()) || tasks.containsKey(updatedEpic.getId())) {
            throw new IllegalArgumentException();
        }
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
            historyManager.add(epics.get(id));
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
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Epic currentEpic = entry.getValue();
            currentEpic.setSubTusksIdes(List.of());
            updateEpicStatus(currentEpic.getId());
        }
    }

    @Override
    public SubTask createSubTask(SubTask newSubTask) {
        if (newSubTask.getId() == null) {
            newSubTask.setId(generateCorrectId());
            subTusks.put(newSubTask.getId(), newSubTask);
            updateEpicStatus(newSubTask.getLinkedEpicId());
            return newSubTask;
        } else {
            if (tasks.containsKey(newSubTask.getId()) || epics.containsKey(newSubTask.getId())) {
                throw new IllegalArgumentException();
            }
            if (!subTusks.containsKey(newSubTask.getId())) {
                subTusks.put(newSubTask.getId(), newSubTask);
                return newSubTask;
            } else {
                throw new IllegalArgumentException();
            }
        }
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
        if (epics.containsKey(updatedSubTask.getId()) || tasks.containsKey(updatedSubTask.getId())) {
            throw new IllegalArgumentException();
        }
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

    private int generateCorrectId() {
        int correctId = idGenerator.createId();

        while (epics.containsKey(correctId) || tasks.containsKey(correctId) || subTusks.containsKey(correctId)) {
            correctId = idGenerator.createId();
        }
        return correctId;
    }
}