package additionalmodules.historymanager.customecollection;

import models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomHistoryManagerCollection {
    private Node head;
    private Node tail;
    private final Map<Integer, Node> map;

    public CustomHistoryManagerCollection() {
        map = new HashMap<>();
    }

    public void add(Task task) {
        Task taskClone = task.clone();

        if (!map.containsKey(taskClone.getId())) {
            Node newNode = new Node(taskClone);
            map.put(taskClone.getId(), newNode);
            linkLast(newNode);
        }
    }

    public void removeTask(Task task) {
        Node node = map.get(task.getId());
        if (node != null) {
            map.remove(task.getId());
            removeNode(node);
        }
    }

    public ArrayList<Task> getHistoryAsList() {
        ArrayList<Task> history = new ArrayList<>();
        Node current = head;
        while (current != null) {
            history.add(current.getTask());
            current = current.getNext();
        }
        return history;
    }

    private void linkLast(Node node) {
        if (head == null) {
            head = node;
        } else {
            tail.setNext(node);
            node.setPrev(tail);
        }
        tail = node;
    }

    private void removeNode(Node node) {
        if (node.getPrev() != null) {
            node.getPrev().setNext(node.getNext());
        } else {
            head = node.getNext();
        }
        if (node.getNext() != null) {
            node.getNext().setPrev(node.getPrev());
        } else {
            tail = node.getPrev();
        }
    }
}
