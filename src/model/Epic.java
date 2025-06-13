package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<Integer> subtasks;

    public Epic(String title, String description) {
        super(title, description);
        this.subtasks = new ArrayList<>();
    }

    public List<Integer> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        if (subtask != null && subtask.getId() != this.getId()) {
            subtasks.add(subtask.getId());
        }
    }

    @Override
    public void setStatus(TypeStatus status) {
        super.setStatus(status);
    }

    public void setSubtasks(List<Integer> subtasks) {
        this.subtasks = subtasks;
    }
}
