package manager.impl;

import manager.TaskManager;
import manager.HistoryManager;
import util.Managers;
import model.Task;
import model.Epic;
import model.Subtask;
import model.TypeStatus;

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
        for (Subtask st : subtasks.values()) {
            epics.get(st.getIdEpic()).getSubtasks().remove(st.getId());
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
        Subtask st = subtasks.get(id);
        if (st != null) {
            historyManager.add(st);
        }
        return st;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void createTask(Task task) {
        task.setId(nextId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(nextId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtask.setId(nextId());
        subtasks.put(subtask.getId(), subtask);
        Epic parent = epics.get(subtask.getIdEpic());
        if (parent != null) {
            parent.addSubtask(subtask);
        }
    }

    @Override
    public Task updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return null;
        }
        Task stored = tasks.get(task.getId());
        stored.setTitle(task.getTitle());
        stored.setDescription(task.getDescription());
        stored.setStatus(task.getStatus());

        return stored;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            return null;
        }
        Epic stored = epics.get(epic.getId());
        stored.setSubtasks(epic.getSubtasks());
        actualizeEpicStatus(stored);
        return stored;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getId())) {
            return null;
        }
        Subtask stored = subtasks.get(subtask.getId());
        stored.setTitle(subtask.getTitle());
        stored.setDescription(subtask.getDescription());
        stored.setStatus(subtask.getStatus());
        actualizeEpicStatus(epics.get(stored.getIdEpic()));

        return stored;
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id) {
        if (!subtasks.containsKey(id)) {
            return;
        }
        Subtask st = subtasks.remove(id);
        epics.get(st.getIdEpic()).getSubtasks().remove(Integer.valueOf(id));
    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic e = epics.get(id);
            List<Integer> epicSubtasks = e.getSubtasks();
            for (Integer subtaskId : epicSubtasks) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }

    @Override
    public List<Subtask> getSubtasksByEpicId(Integer epicId) {
        List<Subtask> list = new ArrayList<>();
        for (Integer id : epics.get(epicId).getSubtasks()) {
            list.add(subtasks.get(id));
        }
        return list;
    }

    private int nextId() {
        return ++counterTasks;
    }

    private void actualizeEpicStatus(Epic e) {
        boolean allDone = true;
        boolean allNew = true;
        for (int id : e.getSubtasks()) {
            TypeStatus st = subtasks.get(id).getStatus();
            if (st != TypeStatus.DONE) {
                allDone = false;
            }
            if (st != TypeStatus.NEW) {
                allNew = false;
            }
        }
        if (allDone) {
            e.setStatus(TypeStatus.DONE);
        } else if (allNew) {
            e.setStatus(TypeStatus.NEW);
        } else {
            e.setStatus(TypeStatus.IN_PROGRESS);
        }
    }
}
