import java.util.Objects;

public abstract class Task {
    private int id;
    private String description;
    private Statuses status;

    public void setStatus(Statuses Statuses) {
        this.status = Statuses;
    }

    @Override
    public String toString() {
        return "Task{" +
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

    public Task(String description, Statuses status) {
        this.description = description;
        this.status = status;
    }

}