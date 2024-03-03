package org.example;

import java.util.Objects;

public abstract class Task implements Objective {
    private String description;
    private TackCondition tackCondition;
    private int id;

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", tackCondition=" + tackCondition +
                ", id=" + id +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public TackCondition getTackCondition() {
        return tackCondition;
    }

    public void setTackCondition(TackCondition tackCondition) {
        this.tackCondition = tackCondition;
    }

    public Task(String description, TackCondition tackCondition, int id) {
        this.description = description;
        this.tackCondition = tackCondition;
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    public void updateCondition(Task task) {
        this.description = task.description;
        this.tackCondition = task.tackCondition;
    }
}