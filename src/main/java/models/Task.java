package models;

import java.util.Objects;

public class Task implements Cloneable {
    private Integer id;
    private String description;
    private Statuses status;

    public Task(String description, Statuses status) {
        this.description = description;
        this.status = status;
    }

    public Task(int id, String description, Statuses status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public Task() {
    }

    public void setStatus(Statuses statuses) {
        this.status = statuses;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return id + "," + status + "," + description;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Statuses getStatus() {
        return status;
    }

    @Override
    public Task clone() {
        try {
            Task clone = (Task) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}