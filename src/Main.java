import util.Managers;
import manager.TaskManager;
import model.Task;
import model.Epic;
import model.Subtask;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        manager.createTask(new Task("Таск1", "Базовый таск"));
        manager.createTask(new Task("Таск2", "Другой таск"));

        Epic epic1 = new Epic("Эпик1", "Первый эпик");
        manager.createEpic(epic1);
        Subtask sub1 = new Subtask("Сабтаск1", "Сабтаск Эпик1", epic1.getId());
        Subtask sub2 = new Subtask("Сабтаск2", "Сабтаск Эпик1", epic1.getId());
        manager.createSubtask(sub1);
        manager.createSubtask(sub2);

        Epic epic2 = new Epic("Эпик2", "Второй эпик");
        manager.createEpic(epic2);
        Subtask sub3 = new Subtask("Сабтаск3", "Сабтаск Эпик2", epic2.getId());
        manager.createSubtask(sub3);

        manager.getByIdTask(1);
        printHistory(manager);

        manager.getByIdEpic(epic1.getId());
        printHistory(manager);

        manager.getByIdSubtask(sub3.getId());
        printHistory(manager);

        System.out.println("\n=== Финальный отчёт по всем задачам ===");
        printAll(manager);
    }

    private static void printHistory(TaskManager manager) {
        System.out.println("История просмотров:");
        for (Task t : manager.getHistory()) {
            System.out.println("  " + t);
        }
        System.out.println();
    }

    private static void printAll(TaskManager manager) {
        System.out.println("=== Все задачи ===");
        System.out.println("[Обычные задачи]");
        for (Task task : manager.getTasks().values()) {
            System.out.println(task);
        }

        System.out.println("\n[Эпики]");
        for (Epic epic : manager.getEpics().values()) {
            System.out.println(epic);
            for (Subtask st : manager.getSubtasksByEpicId(epic.getId())) {
                System.out.println("  -> " + st);
            }
        }

        System.out.println("\n[Подзадачи]");
        for (Subtask st : manager.getSubtasks().values()) {
            System.out.println(st);
        }

        System.out.println("\n[История просмотров]");
        for (Task t : manager.getHistory()) {
            System.out.println("  " + t);
        }
    }
}
