package manager.impl;

import manager.HistoryManager;
import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> history = new HashMap<>();
    private Node first;
    private Node last;

    @Override
    public void add(Task task) {
        removeNode(task.getId());
        linkLast(task);
        history.put(task.getId(), last);
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> resultHistory = new ArrayList<>();
        Node currentNode = first;

        while (currentNode != null) {
            resultHistory.add(currentNode.data);
            currentNode = currentNode.next;
        }

        return resultHistory;
    }

    private void removeNode(int id) {
        Node node = history.remove(id);
        if (node == null) {
            return;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }

        node.prev = null;
        node.next = null;
    }

    private void linkLast(Task task) {
        Node node = new Node(task, last, null);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
    }


    private static class Node {
        Task data;
        Node prev;
        Node next;

        public Node(Task data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}
