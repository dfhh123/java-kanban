package Models;

import java.util.*;

public class Epic extends Task {
    public Epic(String description, Statuses status, List<Integer> subTusksIds) {
        super(description, status);
        this.subTusksIds = subTusksIds;
    }

    private List <Integer> subTusksIds;

    public List<Integer> getSubTusksIds() {
        return subTusksIds;
    }

    public List<Integer> getSubTusksIdes() {
        return subTusksIds;
    }

    public void setSubTusksIdes(List<Integer> subTusksIdes) {
        this.subTusksIds = subTusksIdes;
    }

    public Epic(String description, Statuses Statuses) {
        super(description, Statuses);
    }
}