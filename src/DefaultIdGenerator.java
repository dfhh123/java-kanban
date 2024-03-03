package org.example;

class DefaultIdGenerator implements IdGenerator {
    int countOfTasks = 0;

    public int createDefaultId() {

        int currentId = countOfTasks + 1;
        countOfTasks++;

        return currentId;
    }
}