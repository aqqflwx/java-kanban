import java.util.ArrayList;
import java.util.HashMap;

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
                for (Integer subtaskId : e.getSubtasks()) {
                    Subtask subtask = subtasks.get(subtaskId);
                    System.out.println(counterSubtasks++ + ") " + subtask);
                }
            }
        }
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void removeAllSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            Epic epic = epics.get(subtask.getIdEpic());
            epic.getSubtasks().remove(subtask.getId());
        }
        subtasks.clear();
    }

    public Epic getByIdEpic(int id) {
        return epics.get(id);
    }

    public Task getByIdTask(int id) {
        return tasks.get(id);
    }

    public Subtask getByIdSubtask(int id) {
        return subtasks.get(id);
    }

    public void createTask(Task task) {
        task.setId(getCounterId());
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(getCounterId());
        epics.put(epic.getId(), epic);

    }

    public void createSubtask(Subtask subtask) {
        subtask.setId(getCounterId());
        subtasks.put(subtask.getId(), subtask);
        if (epics.containsKey(subtask.getIdEpic()) &&
                !(epics.get(subtask.getIdEpic()).getSubtasks()).contains(subtask.getId())) {
            epics.get(subtask.getIdEpic()).addSubtask(subtask);
        }
    }

    public Task updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            Task taskToUpdate = tasks.get(task.getId());
            updateGeneralFields(task, taskToUpdate);
            tasks.put(taskToUpdate.getId(), taskToUpdate);
            return taskToUpdate;
        }
        return null;
    }

    public Epic updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic e = epics.get(epic.getId());
            e.setSubtasks(epic.getSubtasks());
            actualizeEpicStatus(e);
            return e;
        }
        return null;
    }

    public Subtask updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Subtask subTaskToUpdate = subtasks.get(subtask.getId());
            updateGeneralFields(subtask, subTaskToUpdate);
            Epic epic = epics.get(subTaskToUpdate.getIdEpic());
            actualizeEpicStatus(epic);
            return subTaskToUpdate;
        }
        return null;
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            Epic epic = epics.get(subtask.getIdEpic());
            epic.getSubtasks().remove(Integer.valueOf(id));
            subtasks.remove(id);
        }
    }

    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic e = epics.get(id);
            ArrayList<Integer> epicSubtasks = e.getSubtasks();
            for (Integer subtaskId : epicSubtasks) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }

    public ArrayList<Subtask> getSubtasksByEpicId(Integer epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        for (Integer id : epics.get(epicId).getSubtasks()) {
            if (subtasks.containsKey(id)) {
                result.add(subtasks.get(id));
            }
        }
        return result;
    }

    private void updateGeneralFields(Task updatedTask, Task taskToUpdate) {
        taskToUpdate.setTitle(
                updatedTask.getTitle() != null
                        ? updatedTask.getTitle()
                        : taskToUpdate.getTitle());

        taskToUpdate.setDescription(
                updatedTask.getDescription() != null
                        ? updatedTask.getDescription()
                        : taskToUpdate.getDescription());

        taskToUpdate.setStatus(
                updatedTask.getStatus() != null
                        ? updatedTask.getStatus()
                        : taskToUpdate.getStatus());
    }

    private void actualizeEpicStatus(Epic e) {
        boolean isAllSubTaskReady = true;
        boolean isAllSubTaskNew = true;
        for (Integer subtaskId : e.getSubtasks()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (!TypeStatus.DONE.equals(subtask.getStatus())) {
                isAllSubTaskReady = false;
                break;
            }
        }

        if (isAllSubTaskReady) {
            e.status = TypeStatus.DONE;
        }

        for (Integer subtaskId : e.getSubtasks()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (!TypeStatus.NEW.equals(subtask.getStatus())) {
                isAllSubTaskNew = false;
                break;
            }
        }

        if (!isAllSubTaskNew && !isAllSubTaskReady) {
            e.status = TypeStatus.IN_PROGRESS;
        }
    }

    private int getCounterId() {
        return ++counterTasks;
    }
}