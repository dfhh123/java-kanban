import Models.Statuses;
import Models.Task;
import TaskManager.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void createTestEnvironment() {
        taskManager = Managers.getDefaultTaskManager();
    }

    @Test
    public void createTask_ShouldCreateTask() {
        Task createdTask = new Task("Сделай работу", Statuses.NEW);
        Task actualTask;

        taskManager.createTask(createdTask);

        actualTask = taskManager.getTaskDyId(0);
        Assertions.assertEquals(actualTask, createdTask);
    }
}
