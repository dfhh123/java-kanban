import models.*;
import taskmanager.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void createTestEnvironment() {
        taskManager = Managers.getDefaultTaskManager();
    }

    @Test
    public void fullInitializedTaskManagerTest() {
        Task createdTask = new Task(0, "Задача 1", Statuses.NEW);

        taskManager.createTask(createdTask);
        Task takenTusk = taskManager.getTaskDyId(0);

        int historySize = taskManager.getHistory().size();
        Assertions.assertEquals(takenTusk, createdTask);
        Assertions.assertEquals(historySize, 1);
    }

    @Test
    public void createTask_ShouldCreateTask() {
        Task createdTask = new Task("Задача 2", Statuses.NEW);
        Task actualTask;

        taskManager.createTask(createdTask);

        actualTask = taskManager.getTaskDyId(0);
        Assertions.assertEquals(actualTask, createdTask);
    }

    @Test
    public void getTuskById_ShouldReturnCreatedTusk() {
        Task createdTask = new Task("Задача 3", Statuses.NEW);
        Task actualTask;
        taskManager.createTask(createdTask);

        actualTask = taskManager.getTaskDyId(0);

        Assertions.assertEquals(actualTask, createdTask);
    }

    @Test
    public void removeTaskById_ShouldDeleteTask() {
        Task createdTask = new Task("Задача 4", Statuses.NEW);
        taskManager.createTask(createdTask);

        taskManager.removeTaskById(0);

        try {
            taskManager.getTaskDyId(0);
        } catch (NoSuchElementException ignored) {
        }
    }

    @Test
    public void removeAllTusks_ShouldDeleteAllTasks() {
        Task createdTask = new Task("Задача 5", Statuses.NEW);
        taskManager.createTask(createdTask);

        taskManager.removeAllTasks();

        try {
            taskManager.getTaskDyId(0);
        } catch (NoSuchElementException ignored) {
        }
    }

    @Test
    public void updateTusk_ShouldUpdateTusk() {
        Task createdTask = new Task("Задача 6", Statuses.NEW);
        Task actualTask = new Task(0, "Задача 7", Statuses.NEW);
        Task updatedTusk;
        taskManager.createTask(createdTask);

        taskManager.updateTask(actualTask);

        updatedTusk = taskManager.getTaskDyId(0);
        Assertions.assertEquals(updatedTusk, actualTask);
    }

    @Test
    public void taskImmutabilityAfterAdding() {
        Task createdTask = new Task(1, "Задача 8", Statuses.NEW);
        Task actualTask;
        taskManager.createTask(createdTask);

        actualTask = taskManager.getTaskDyId(1);

        Assertions.assertEquals(actualTask.getId(), 1);
        Assertions.assertEquals(actualTask.getDescription(), createdTask.getDescription());
        Assertions.assertEquals(actualTask.getStatus(), createdTask.getStatus());
    }

    @Test
    public void idConflictTest() {
        Task createdTask1 = new Task(0, "Задача 9", Statuses.NEW);
        Task createdTask2 = new Task("Задача 10", Statuses.NEW);

        taskManager.createTask(createdTask1);
        taskManager.createTask(createdTask2);


        Assertions.assertEquals(0, createdTask1.getId());
        Assertions.assertEquals(1, createdTask2.getId());
    }

    @Test
    public void subTaskList_ShouldReturnAllSubTasks() {
        Epic epic1 = new Epic("Эпик 1", Statuses.NEW, List.of(1, 2, 3));
        SubTask subTask1 = new SubTask("Сабтаск 1", Statuses.IN_PROGRESS, 0);
        SubTask subTask2 = new SubTask("Сабтаск 2", Statuses.NEW, 0);
        SubTask subTask3 = new SubTask("Сабтаск 3", Statuses.NEW, 0);
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);


        List<SubTask> subTaskList = taskManager.getAllEpicSubTasks(0);

        Assertions.assertEquals(3, subTaskList.size());
    }

    @Test
    public void create_EpicStatusShouldBeInProgress() {
        Epic epic1 = new Epic("Эпик 2", Statuses.NEW, List.of(1, 2, 3));
        SubTask subTask1 = new SubTask("Сабтаск 4", Statuses.IN_PROGRESS, 0);
        SubTask subTask2 = new SubTask("Сабтаск 5", Statuses.NEW, 0);
        SubTask subTask3 = new SubTask("Сабтаск 6", Statuses.NEW, 0);
        taskManager.createEpic(epic1);


        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);

        Assertions.assertEquals(Statuses.IN_PROGRESS, taskManager.getEpicDyId(0).getStatus());
    }

    @Test
    public void getHistory_ShouldReturnCorrectHistory() {
        Task createdTask1 = new Task(0, "Задача 11", Statuses.NEW);
        Task createdTask2 = new Task("Задача 12", Statuses.NEW);
        taskManager.createTask(createdTask1);
        taskManager.createTask(createdTask2);

        taskManager.getTaskDyId(0);
        taskManager.getTaskDyId(1);
        taskManager.getTaskDyId(0);
        taskManager.getTaskDyId(1);


        Assertions.assertEquals(2, taskManager.getHistory().size());
    }

    @Test
    public void removeAllSubTusks_EpicShouldHaveStatusNew() {
        Epic epic1 = new Epic("Эпик 3", Statuses.NEW, List.of(1, 2, 3));
        SubTask subTask1 = new SubTask("Сабтаск 7", Statuses.IN_PROGRESS, 0);
        SubTask subTask2 = new SubTask("Сабтаск 8", Statuses.NEW, 0);
        SubTask subTask3 = new SubTask("Сабтаск 9", Statuses.NEW, 0);
        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);

        taskManager.removeAllSubTasks();

        Assertions.assertEquals(Statuses.NEW, taskManager.getEpicDyId(0).getStatus());
    }

    @Test
    public void getHistory_ShouldReturnCorrectTaskAfterChange() {
        Task createdTask1 = new Task(0, "Задача 13", Statuses.NEW);
        taskManager.createTask(createdTask1);
        taskManager.getTaskDyId(0);

        createdTask1.setDescription("Подставной описание");

        Assertions.assertEquals("Задача 13", taskManager.getHistory().get(0).getDescription());
    }


    @Test
    public void taskEqualsTest_ShouldReturnTrue() {
        Task createdTask1 = new Task(1, "Задача14", Statuses.NEW);
        Task createdTask2 = new Task(1, "Задача15", Statuses.NEW);

        Assertions.assertEquals(createdTask1, createdTask2);
    }

    @Test
    public void successorsEqualsTest_ShouldReturnTrue() {
        Task createdTask1 = new SubTask(1, "Сабтаск 10", Statuses.NEW, 3);
        Task createdTask2 = new SubTask(1, "Сабтаск 11", Statuses.NEW, 5);

        Assertions.assertEquals(createdTask1, createdTask2);
    }

    @Test
    public void epicCantSetItselfInSubTusksListTest_ShouldReturnFalse() {
        Epic cratedEpic = new Epic(1, "Эпик 3", Statuses.NEW);

        assertThrows(IllegalArgumentException.class, () -> {
            cratedEpic.setSubTusksIdes(List.of(1, 2, 3, 4));
        });
    }

    @Test
    public void subTuskCantSetItSelfTest_ShouldReturnFalse() {
        SubTask createdTask1 = new SubTask(1, "Сабтаск 12", Statuses.NEW, 3);

        assertThrows(IllegalArgumentException.class, () -> {
            createdTask1.setLinkedEpicId(createdTask1.getLinkedEpicId());
        });
    }

    @Test
    public void createTusk_ShouldReturnIllegalArgumentException() {
        Task createdTask1 = new Task(1, "Задача 16", Statuses.NEW);
        Task createdTask2 = new Task(1, "Задача 17", Statuses.NEW);

        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.createTask(createdTask1);
            taskManager.createTask(createdTask2);
        });
    }

    @Test
    public void changeTaskCondition_ShouldReturnTrue() {
        Task createdTask1 = new Task(1, "Задача 18", Statuses.NEW);
        taskManager.createTask(createdTask1);
        taskManager.getTaskDyId(1);
        taskManager.getHistory();

        createdTask1.setId(53);

        Task collectedTask = taskManager.getTaskDyId(1);
        Assertions.assertEquals(createdTask1, collectedTask);
    }
}