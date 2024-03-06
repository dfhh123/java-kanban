import java.util.*;

public class Epic extends Task {
    private List <Integer> subTusksIds;

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