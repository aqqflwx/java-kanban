import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class TaskManager {

    private static int counterTasks = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public HashMap<Integer,Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer,Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer,Subtask> getSubtasks() {
        return subtasks;
    }

    public void printAllTasks() {
        System.out.println("Список существующих задач:");
        if (!tasks.isEmpty()) {
            System.out.println("[TASKS]");
            for (Task t : tasks.values()) {
                System.out.println(t);
            }
        }
        if (!epics.isEmpty()) {
            System.out.println("[EPIC_TASKS]");
            int counterEpics = 1;
            for (Epic e : epics.values()) {
                System.out.println(counterEpics++ + ". " + e);
                char counterSubtasks = 97;
                for (Subtask s : e.getSubtasks().values()) {
                    System.out.println(counterSubtasks++ + ") " + s);
                }
            }
        }
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        System.out.println("Удаляем все EPICS и связанные с ними SUBTASKS...");
        epics.clear();
        subtasks.clear();
        System.out.println("Готово.");
    }

    public void removeAllSubtasks() {
        System.out.println("Удаляем все SUBTASKS и чистим ссылки в EPICS...");
        subtasks.clear();
        System.out.println("Готово.");
    }

    public Epic getByIdEpic(int id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        return null;
    }

    public Task getByIdTask(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }

        return null;
    }

    public Subtask getByIdSubtask(int id) {
        if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        }

        return null;
    }

    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        if (epics.containsKey(subtask.getIdEpic()) &&
                !(epics.get(subtask.getIdEpic()).getSubtasks()).containsKey(subtask.getId())) {
            epics.get(subtask.getIdEpic()).addSubtask(subtask);
        }
    }


    public int getCounterId() {
        return ++counterTasks;
    }


    public Task updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            Task t = tasks.get(task.getId());
            t.update(task);
            return t;
        }
        return null;
    }

    public Epic updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic e = epics.get(epic.getId());
            e.update(epic);
            return e;
        }
        return null;
    }

    public Subtask updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Subtask s = subtasks.get(subtask.getId());
            s.update(subtask);
            Epic epic = epics.get(s.getIdEpic());
            epic.update(epic);
            return s;
        }
        return null;
    }

    public void deleteById(int id) {
        if (epics.containsKey(id)) {
            Epic e = epics.get(id);
            Set<Integer> epicSubtasks = e.getSubtasks().keySet();
            for (Integer subtaskId : epicSubtasks) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
        if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            Epic epic = epics.get(subtask.getIdEpic());
            epic.getSubtasks().remove(id);
            subtasks.remove(id);
        }
        tasks.remove(id);
    }

    // получение списка сабтасков в эпике
    public Collection<Subtask> getSubtasksByEpicId(Integer epicId) {
        return epics.get(epicId).getSubtasks().values();
    }

}
