package additionalmodules.historymanager.customecollection;

import models.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomHistoryManagerCollection {
    private Node head;
    private Node tail;
    private final HashMap<Integer, Node> map;

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
            history.add(current.task);
            current = current.next;
        }
        return new ArrayList<>(history);
    }


    private void linkLast(Node node) {
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    static class Node {
        Task task;
        Node next;
        Node prev;

        public Node(Task task) {
            this.task = task.clone();
        }
    }
}