import Models.*;
import TaskManager.TaskManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager1 = new TaskManager();
        testTask(taskManager1);

        TaskManager taskManager2 = new TaskManager();
        TestSubTuskAndEpics(taskManager2);
    }

    public static void testTask(TaskManager taskManager) {
        Task task1 = new Task("Сделай работу", Statuses.NEW);
        Task task2 = new Task("Поешь", Statuses.NEW);
        System.out.println("Test1");

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        System.out.println(taskManager);
        System.out.println();


        taskManager.removeTaskById(0);

        System.out.println(taskManager);
        System.out.println();


        Task task3 = new Task(1, "Еще раз поешь", Statuses.NEW);
        taskManager.updateTask(task3);

        System.out.println(taskManager);
        System.out.println();

        Task task4 = taskManager.getTaskDyId(1);


        List<Task> testList = taskManager.getAllTasks();

        System.out.println(testList);
        System.out.println();


        System.out.println(taskManager);
        System.out.println();
        taskManager.removeAllTasks();
        System.out.println(taskManager);
        System.out.println();
    }

    public static void TestSubTuskAndEpics(TaskManager taskManager) {
        Epic epic1 = new Epic("Отработай", Statuses.NEW, List.of(1,2,3));
        SubTask subTask1 = new SubTask("Приди на работу", Statuses.IN_PROGRESS, 0);
        SubTask subTask2 = new SubTask("Сделай работу", Statuses.NEW, 0);
        SubTask subTask3 = new SubTask("Уйди с работы", Statuses.NEW, 0);

        System.out.println("Test2");

        taskManager.createEpic(epic1);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);

        System.out.println(taskManager);
        System.out.println();

        List<SubTask> subTuskList = taskManager.getAllEpicSubTasks(0);

        System.out.println(subTuskList);
        System.out.println();

        taskManager.removeAllSubTasks();

        System.out.println(taskManager);
        System.out.println();

        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);

        System.out.println(taskManager);
        System.out.println();

        taskManager.removeAllEpics();
        System.out.println(taskManager);
        System.out.println();
    }
}
