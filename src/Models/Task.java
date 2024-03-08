package Models;

import java.util.Objects;

public  class Task {
    private int id;
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

    public void setStatus(Statuses Statuses) {
        this.status = Statuses;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Models.Task{" +
                "description='" + description + '\'' +
                ", tackCondition=" + status +
                ", id=" + id +
                '}';
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public Statuses getStatus() {
        return status;
    }
}