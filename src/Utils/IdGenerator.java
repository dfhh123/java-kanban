package Utils;

public class IdGenerator {
    private int countOfTasks = 0;

    public int createDefaultId() {

        return countOfTasks++;
    }
}