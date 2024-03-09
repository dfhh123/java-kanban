package Models;

import org.junit.jupiter.api.Test;

public class SubTask extends Task {
    private int linkedEpicId;

    public void setLinkedEpicId(int linkedEpicId) {
        if (this.linkedEpicId == linkedEpicId) {
            throw new IllegalArgumentException();
        }
        this.linkedEpicId = linkedEpicId;
    }

    public SubTask(int id, String description, Statuses status, int linkedEpicId) {
        super(id, description, status);
        this.linkedEpicId = linkedEpicId;
    }

    public int getLinkedEpicId() {
        return linkedEpicId;
    }

    public SubTask(String description, Statuses Statuses, int linkedEpicId) {
        super(description, Statuses);
        this.linkedEpicId = linkedEpicId;
    }

    @Override
    public Task clone() {
        SubTask clone = (SubTask) super.clone();
        clone.linkedEpicId = this.linkedEpicId;
        return clone;
    }
}