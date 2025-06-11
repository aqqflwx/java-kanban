import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public interface TaskManager {
    HashMap<Integer, Task> getTasks();
    HashMap<Integer, Epic> getEpics();
    HashMap<Integer, Subtask> getSubtasks();

    void printAllTasks();

    void removeAllTasks();
    void removeAllEpics();
    void removeAllSubtasks();

    Epic getByIdEpic(int id);
    Task getByIdTask(int id);
    Subtask getByIdSubtask(int id);

    void createTask(Task task);
    void createEpic(Epic epic);
    void createSubtask(Subtask subtask);

    Task updateTask(Task task);
    Epic updateEpic(Epic epic);
    Subtask updateSubtask(Subtask subtask);

    void deleteTaskById(int id);
    void deleteSubtaskById(int id);
    void deleteEpicById(int id);

    ArrayList<Subtask> getSubtasksByEpicId(Integer epicId);

    List<Task> getHistory();
}
