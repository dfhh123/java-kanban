import java.util.*;

public class TaskManager {

    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, SubTask> subTusks = new HashMap<>();

    IdGenerator idGenerator = new IdGenerator();


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
                    case NEW -> newCount++;
                    case IN_PROGRESS -> InProgressCount++;
                    case DONE -> doneCount++;
                }

                if (doneCount == idList.size()) {
                    currentEpic.setStatus(Statuses.DONE);
                } else {
                    currentEpic.setStatus(Statuses.IN_PROGRESS);
                }
            }
        }
    }


    //методы для тасков
    public Task getTaskDyId(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public Task createTask(Task newTask) {
        newTask.setId(idGenerator.createDefaultId());
        tasks.put(newTask.getId(), newTask);
        return newTask;
    }

    public void removeTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    public Task updateTask(Task updatedTask) {
        tasks.put(updatedTask.getId(), updatedTask);
        return updatedTask;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }


    //методы для эпиков
    public void removeAllEpics() {
        epics.clear();
    }

    public Epic createEpic(Epic newEpic) {
        newEpic.setId(idGenerator.createDefaultId());
        epics.put(newEpic.getId(), newEpic);
        return newEpic;
    }

    public void removeEpicById(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    public Epic updateEpic(Epic updatedEpic) {
        epics.put(updatedEpic.getId(), updatedEpic);
        return updatedEpic;
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public Epic getEpicDyId(int id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        } else {
            throw new NoSuchElementException();
        }
    }


    //методы для сабтасков
    public SubTask getSubTuskById(int id) {
        if (subTusks.containsKey(id)) {
            return subTusks.get(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    public void removeAllSubTasks() {
        subTusks.clear();
    }

    public SubTask createSubTask(SubTask newSubTask) {
        newSubTask.setId(idGenerator.createDefaultId());
        subTusks.put(newSubTask.getId(), newSubTask);
        updateEpicStatus(newSubTask.getLinkedEpicId());
        return newSubTask;
    }

    public void removeSubTaskById(int id) {
        if (subTusks.containsKey(id)) {
            int epicId = subTusks.get(id).linkedEpicId;
            subTusks.remove(id);
            updateEpicStatus(epicId);
        } else {
            throw new NoSuchElementException();
        }
    }

    public SubTask updateSubTask(SubTask updatedSubTask) {
        subTusks.put(updatedSubTask.getId(), updatedSubTask);
        return updatedSubTask;
    }

    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTusks.values());
    }

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