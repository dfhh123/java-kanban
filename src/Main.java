import Models.*;
import TaskManager.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager inMemoryTaskManager1 = Managers.getDefaultTaskManager();
        testTask(inMemoryTaskManager1);

        TaskManager inMemoryTaskManager2 = Managers.getDefaultTaskManager();
        TestSubTuskAndEpics(inMemoryTaskManager2);
    }

    public static void testTask(TaskManager inMemoryTaskManager) {


        System.out.println(inMemoryTaskManager);
        System.out.println();


        inMemoryTaskManager.removeTaskById(0);

        System.out.println(inMemoryTaskManager);
        System.out.println();


        Task task3 = new Task(1, "Еще раз поешь", Statuses.NEW);
        inMemoryTaskManager.updateTask(task3);

        System.out.println(inMemoryTaskManager);
        System.out.println();

        Task task4 = inMemoryTaskManager.getTaskDyId(1);


        List<Task> testList = inMemoryTaskManager.getAllTasks();

        System.out.println(testList);
        System.out.println();


        System.out.println(inMemoryTaskManager);
        System.out.println();
        inMemoryTaskManager.removeAllTasks();
        System.out.println(inMemoryTaskManager);
        System.out.println();
    }

    public static void TestSubTuskAndEpics(TaskManager inMemoryTaskManager) {
        Epic epic1 = new Epic("Отработай", Statuses.NEW, List.of(1,2,3));
        SubTask subTask1 = new SubTask("Приди на работу", Statuses.IN_PROGRESS, 0);
        SubTask subTask2 = new SubTask("Сделай работу", Statuses.NEW, 0);
        SubTask subTask3 = new SubTask("Уйди с работы", Statuses.NEW, 0);

        System.out.println("Test2");

        inMemoryTaskManager.createEpic(epic1);
        inMemoryTaskManager.createSubTask(subTask1);
        inMemoryTaskManager.createSubTask(subTask2);
        inMemoryTaskManager.createSubTask(subTask3);

        System.out.println(inMemoryTaskManager);
        System.out.println();

        List<SubTask> subTuskList = inMemoryTaskManager.getAllEpicSubTasks(0);

        System.out.println(subTuskList);
        System.out.println();

        inMemoryTaskManager.removeAllSubTasks();

        System.out.println(inMemoryTaskManager);
        System.out.println();

        inMemoryTaskManager.createSubTask(subTask1);
        inMemoryTaskManager.createSubTask(subTask2);
        inMemoryTaskManager.createSubTask(subTask3);

        System.out.println(inMemoryTaskManager);
        System.out.println();

        inMemoryTaskManager.removeAllEpics();
        System.out.println(inMemoryTaskManager);
        System.out.println();
    }
}
