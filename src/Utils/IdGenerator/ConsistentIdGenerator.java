package Utils.IdGenerator;

public class ConsistentIdGenerator implements IdGenerator {
    private int countOfTasks = 0;

    public int createId() {

        return countOfTasks++;
    }
}