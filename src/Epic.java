import java.util.HashMap;

public class Epic extends Task {

    private HashMap<Integer,Subtask> subtasks;

    public Epic(int id, String title, String description) {
        super(id, title, description);
        this.subtasks = new HashMap<>();
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public Epic update(Epic epic) {
        Epic e = (Epic)super.update(epic);
        e.subtasks = epic.getSubtasks();
        boolean isAllSubTaskReady = true;
        boolean isAllSubTaskNew = true;
        for (Subtask subtask : e.subtasks.values()) {
            if (!TypeStatus.DONE.equals(subtask.getStatus())) {
                isAllSubTaskReady = false;
                break;
            }
        }

        if (isAllSubTaskReady) {
            e.status = TypeStatus.DONE;
            return e;
        }

        for (Subtask subtask : e.subtasks.values()) {
            if (!TypeStatus.NEW.equals(subtask.getStatus())) {
                isAllSubTaskNew = false;
                break;
            }
        }

        if (!isAllSubTaskNew) {
            e.status = TypeStatus.IN_PROGRESS;
        }
        return e;
    }

    @Override
    public void setStatus(TypeStatus status) {
        System.out.println("Смена статуса эпика напрямую запрещена");
            }
}
