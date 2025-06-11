import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private static int counterTasks = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
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

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            Epic epic = epics.get(subtask.getIdEpic());
            epic.getSubtasks().remove(subtask.getId());
        }
        subtasks.clear();
    }

    @Override
    public Epic getByIdEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Task getByIdTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Subtask getByIdSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void createTask(Task task) {
        task.setId(getCounterId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(getCounterId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtask.setId(getCounterId());
        subtasks.put(subtask.getId(), subtask);
        if (epics.containsKey(subtask.getIdEpic())
                && !epics.get(subtask.getIdEpic()).getSubtasks().contains(subtask.getId())) {
            epics.get(subtask.getIdEpic()).addSubtask(subtask);
        }
    }

    @Override
    public Task updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            Task taskToUpdate = tasks.get(task.getId());
            updateGeneralFields(task, taskToUpdate);
            tasks.put(taskToUpdate.getId(), taskToUpdate);
            return taskToUpdate;
        }
        return null;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic e = epics.get(epic.getId());
            e.setSubtasks(epic.getSubtasks());
            actualizeEpicStatus(e);
            return e;
        }
        return null;
    }

    @Override
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

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            Epic epic = epics.get(subtask.getIdEpic());
            epic.getSubtasks().remove(Integer.valueOf(id));
            subtasks.remove(id);
        }
    }

    @Override
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

    @Override
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