import additionalmodules.historymanager.FileBackedHistoryManager;
import additionalmodules.historymanager.SavableHistoryManager;
import additionalmodules.savemoduls.TaskFromCsvLoader;
import additionalmodules.savemoduls.TaskToCsvSaver;
import additionalmodules.savemoduls.convertors.ModelToStringConvertor;
import additionalmodules.savemoduls.convertors.StringLineToModelConvertor;
import models.*;
import org.junit.jupiter.api.*;
import taskmanager.FileBackedTaskManager;
import taskmanager.Managers;
import taskmanager.TaskManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class FileBackedTaskManagerTest {
    private FileBackedTaskManager testManager;
    private static final Path FILE_BACKED_TASK_MANAGER_TEST_PATH = Path
            .of("test/resources/file-backed-task-manager-save.csv");
    private static final Path FILE_BACKED_HISTORY_MANAGER_TEST_PATH = Path
            .of("test/resources/file-backed-task-manager-save.csv");

    @Nested
    class StringLineToModelConvertorTests {
        StringLineToModelConvertor stringLineToModelConvertor;

        @BeforeEach
        public void setUp() {
            stringLineToModelConvertor = new StringLineToModelConvertor();
        }

        @Test
        public void loadTaskFromCsv_shouldCorrectlyCreateTaskLineToTask() {
            Task task = stringLineToModelConvertor.convertCsvLineToTask("0,TASK,NEW,Задача 1,");

            Assertions.assertEquals(0, task.getId());
            Assertions.assertEquals("Задача 1", task.getDescription());
            Assertions.assertEquals(Statuses.NEW, task.getStatus());
        }

        @Test
        public void loadEpicFromCsv_shouldCorrectlyCreateEpic() {
            Epic epic = stringLineToModelConvertor.convertCsvLineToTask("4,EPIC,IN_PROGRESS,Эпик 1,,5 6 ");

            Assertions.assertEquals(4, epic.getId());
            Assertions.assertEquals("Эпик 1", epic.getDescription());
            Assertions.assertEquals(Statuses.IN_PROGRESS, epic.getStatus());
            Assertions.assertEquals(List.of(5, 6), epic.getSubTusksIdes());
        }

        @Test
        public void loadSubTaskFromCsv_shouldCorrectlyCreateSubTask() {
            SubTask subTask = stringLineToModelConvertor.convertCsvLineToTask("5,SUBTASK,NEW,Подзадача 1,4");

            Assertions.assertEquals(5, subTask.getId());
            Assertions.assertEquals("Подзадача 1", subTask.getDescription());
            Assertions.assertEquals(Statuses.NEW, subTask.getStatus());
            Assertions.assertEquals(4, subTask.getLinkedEpicId());
        }
    }

    @Nested
    class ModelToCsvConvertorTests {
        ModelToStringConvertor modelToStringLineConvertor;

        @BeforeEach
        public void setUp() {
            modelToStringLineConvertor = new ModelToStringConvertor();
        }

        @Test
        public void shouldConvertTaskToString() {
            Task task = new Task(0, "Задача 1", Statuses.NEW);

            String csvLine = modelToStringLineConvertor.convaertModelToString(task);

            Assertions.assertEquals("0,TASK,NEW,Задача 1,\n", csvLine);
        }

        @Test
        public void shouldConvertEpicToString() {
            Epic epic = new Epic(4, "Эпик 1", Statuses.NEW, List.of(5, 6));

            String csvLine = modelToStringLineConvertor.convaertModelToString(epic);

            Assertions.assertEquals("4,EPIC,NEW,Эпик 1,,5 6 \n", csvLine);
        }

        @Test
        public void shouldConvertSubTaskToString() {
            SubTask subTask = new SubTask(5, "Подзадача 1", Statuses.NEW, 4);

            String csvLine = modelToStringLineConvertor.convaertModelToString(subTask);

            Assertions.assertEquals("5,SUBTASK,NEW,Подзадача 1,4\n", csvLine);
        }
    }

    @Nested
    class LoaderTest {

        @Test
        public void shouldLoadFromFileWhenCreated() {
            TaskFromCsvLoader taskFromCsvLoader = new TaskFromCsvLoader(FILE_BACKED_TASK_MANAGER_TEST_PATH);
            taskFromCsvLoader.setSaveFileDirectory(FILE_BACKED_TASK_MANAGER_TEST_PATH);
            try (var writer = Files.newBufferedWriter(FILE_BACKED_TASK_MANAGER_TEST_PATH)) {
                writer.write("id,type,status,description,epic,subtaskIds\n");
                writer.write("0,TASK,NEW,Задача 1,\n");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            List<Task> tasks = taskFromCsvLoader.loadDataFromFile().toList();

            Assertions.assertEquals(1, tasks.size());
            Assertions.assertEquals("Задача 1", tasks.getFirst().getDescription());
        }
    }

    @Nested
    class SaveTest {
        @Test
        public void shouldSaveToFileWhenCreated() {
            TaskToCsvSaver taskToCsvSaver = new TaskToCsvSaver(FILE_BACKED_TASK_MANAGER_TEST_PATH);
            TaskFromCsvLoader taskFromCsvLoader = new TaskFromCsvLoader(FILE_BACKED_TASK_MANAGER_TEST_PATH);

            try {
                taskToCsvSaver.saveAllData(List.of(new Task(0, "Задача 1", Statuses.NEW)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            List<Task> tasks = taskFromCsvLoader.loadDataFromFile().toList();

            Assertions.assertEquals(1, tasks.size());
            Assertions.assertEquals("Задача 1", tasks.getFirst().getDescription());
        }
    }

    @Nested
    @Order(1)
    class SaveAndLoadTests {
        @BeforeEach
        public void setUp() {
            testManager = Managers.getDefaultFileBackedTaskManager();

            testManager.createTask(new Task("Задача 1", Statuses.NEW));
            testManager.createTask(new Task("Задача 2", Statuses.NEW));
            testManager.createTask(new Task("Задача 3", Statuses.NEW));

            testManager.createEpic(new Epic(4,"Эпик 1", Statuses.NEW, List.of(5, 6)));

            testManager.createSubTask(new SubTask("Подзадача 1", Statuses.NEW, 4));
            testManager.createSubTask(new SubTask("Подзадача 2", Statuses.NEW, 4));
        }

        @Test
        public void shouldLoadFromFileWhenCreated() {
            TaskManager loadedManager = Managers.getDefaultFileBackedTaskManager();

            assert loadedManager.getAllTasks().size() == 3;
            assert loadedManager.getAllEpics().size() == 1;
            assert loadedManager.getAllSubTasks().size() == 2;
        }
    }

    @Nested
    @Order(2)
    class UploadFromEmptyFileTest {
        @BeforeEach
        public void clearDataFile() throws IOException {
            Files.delete(Path.of("src/main/resources/file-backed-task-manager-save.csv"));
        }

        @Test
        public void shouldCorrectlyCreate() {
            testManager = Managers.getDefaultFileBackedTaskManager();

            assert testManager.getAllTasks().isEmpty();
            assert testManager.getAllEpics().isEmpty();
            assert testManager.getAllSubTasks().isEmpty();
        }
    }

    @Nested
    class FileBackedHistoryManagerTest {
        private SavableHistoryManager testHistory;

        @BeforeEach
        public void setUp() {
        testHistory = new FileBackedHistoryManager();
        testHistory.setSaveFileDirectory(FILE_BACKED_HISTORY_MANAGER_TEST_PATH);
        }

        @Test
        public void shouldCorrectlyCreate_WhenUsingBaseHistoryManagerMethods() {
            testHistory.add(new Task(0, "Задача 1", Statuses.NEW));

            assert testHistory.getHistory().size() == 1;
        }

        @Test
        public void shouldRestore_WhenCreated() {
            testHistory.add(new Task(0, "Задача 1", Statuses.NEW));

            SavableHistoryManager loadedHistory = new FileBackedHistoryManager();

            Assertions.assertEquals(1, loadedHistory.getHistory().size());
        }
    }

    @AfterAll
    public static void clearAllDataFile() {
        try {
            Files.delete(FILE_BACKED_TASK_MANAGER_TEST_PATH);
            Files.delete(FILE_BACKED_HISTORY_MANAGER_TEST_PATH);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
