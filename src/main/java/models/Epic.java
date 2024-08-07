package models;

import java.util.*;

public class Epic extends Task {
    private List<Integer> subTusksIds;

    public Epic(String description, Statuses status, List<Integer> subTusksIds) {
        super(description, status);
        this.subTusksIds = subTusksIds;
    }

    public Epic() {
    }

    public Epic(int i, String s, Statuses statuses, List<Integer> es) {
        super(i, s, statuses);
        this.subTusksIds =  new ArrayList<>(es);
    }

    @Override
    public String toString() {
        return getId() + "," + getStatus() + "," + getDescription() + "," + getSubTusksIdes() + "\n";
    }

    public Epic(int id, String description, Statuses status) {
        super(id, description, status);
    }

    public List<Integer> getSubTusksIdes() {
        return subTusksIds;
    }

    public void setSubTusksIdes(List<Integer> subTusksIdes) {
        if (subTusksIdes.contains(getId())) {
            throw new IllegalArgumentException();
        }
        this.subTusksIds = subTusksIdes;
    }

    public Epic(String description, Statuses statuses) {
        super(description, statuses);
    }

    @Override
    public Task clone() {
        Epic clone = (Epic) super.clone();
        clone.subTusksIds = this.subTusksIds;
        return clone;
    }
}