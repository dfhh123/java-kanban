package org.example;

import java.util.Map;

public interface Sonar {
    Objective findTask(int taskId, Map<Integer, Objective> objectives);

    Objective findAndReturnTaskContainer(int taskID, Map<Integer, Objective> objectives);
}