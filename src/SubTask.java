public class SubTask extends Task {
    int linkedEpicId;

    public void setLinkedEpicId(int linkedEpicId) {
        this.linkedEpicId = linkedEpicId;
    }

    public int getLinkedEpicId() {
        return linkedEpicId;
    }

    public SubTask(String description, Statuses Statuses, int linkedEpicId) {
        super(description, Statuses);
        this.linkedEpicId = linkedEpicId;
    }
}