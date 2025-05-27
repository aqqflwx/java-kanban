import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtasks;

    public Epic(String title, String description) {
        super(title, description);
        this.subtasks = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask.getId());
    }

    @Override
    public void setStatus(TypeStatus status) {
        System.out.println("Смена статуса эпика напрямую запрещена");
    }

    public void setSubtasks(ArrayList<Integer> subtasks) {
        this.subtasks = subtasks;
    }
}
