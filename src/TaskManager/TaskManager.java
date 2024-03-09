package TaskManager;

import Models.Epic;
import Models.SubTask;
import Models.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getHistory();
    //методы для тасков
    Task getTaskDyId(int id);

    void removeAllTasks();

    Task createTask(Task newTask);

    void removeTaskById(int id);

    Task updateTask(Task updatedTask);

    List<Task> getAllTasks();

    //методы для эпиков
    void removeAllEpics();

    Epic createEpic(Epic newEpic);

    void removeEpicById(int id);

    Epic updateEpic(Epic updatedEpic);

    List<Epic> getAllEpics();

    Epic getEpicDyId(int id);

    //методы для сабтасков
    SubTask getSubTuskById(int id);

    void removeAllSubTasks();

    SubTask createSubTask(SubTask newSubTask);

    void removeSubTaskById(int id);

    SubTask updateSubTask(SubTask updatedSubTask);

    List<SubTask> getAllSubTasks();

    List<SubTask> getAllEpicSubTasks(int epicId);
}
