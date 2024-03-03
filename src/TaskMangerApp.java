package org.example;

public class TaskMangerApp {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        IdGenerator idGenerator = new DefaultIdGenerator();

        {
            Objective objective = new SimpleTask("Сделай завтрак",
                    TackCondition.NEW, idGenerator.createDefaultId());
            Objective objective2 = new SimpleTask("Купи Мише танк",
                    TackCondition.NEW, idGenerator.createDefaultId());
            Objective objective3 = new SimpleTask("Купи хлеб",
                    TackCondition.NEW, idGenerator.createDefaultId());

            taskManager.createTask(objective);
            taskManager.createTask(objective2);
            taskManager.createTask(objective3);
        }

        System.out.println(taskManager);
        System.out.println();

        taskManager.removeById(1);

        System.out.println(taskManager);
        System.out.println();

        {
            Epic epic = new Epic("Сделай работу",
                    TackCondition.NEW, idGenerator.createDefaultId());

            SimpleTask subtask = new SimpleTask("Приди на работу",
                    TackCondition.NEW, idGenerator.createDefaultId());
            SimpleTask subtask2 = new SimpleTask("Начни делать работу",
                    TackCondition.NEW, idGenerator.createDefaultId());
            SimpleTask subtask3 = new SimpleTask("Активно делай работу",
                    TackCondition.NEW, idGenerator.createDefaultId());
            SimpleTask subtask4 = new SimpleTask("Приди на работу",
                    TackCondition.NEW, idGenerator.createDefaultId());

            epic.addSubtask(subtask, subtask.getId());
            epic.addSubtask(subtask2, subtask2.getId());
            epic.addSubtask(subtask3, subtask3.getId());
            epic.addSubtask(subtask4, subtask4.getId());

            taskManager.createTask(epic);
        }

        taskManager.removeById(6);
        System.out.println(taskManager);
    }
}
