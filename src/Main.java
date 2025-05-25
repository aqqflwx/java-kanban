public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        manager.createTask(new Task(manager.getCounterId(), "Task1", "DescriptionOne"));
        manager.createTask(new Task(manager.getCounterId(), "Task2", "DescriptionTwo"));

        Epic epic1 = new Epic(manager.getCounterId(), "Epic1", "EpicDescriptionOne");
        Subtask subtask1 = new Subtask(manager.getCounterId(), "Subtask1", "SubtaskDescriptionOne", epic1.getId());
        Subtask subtask2 = new Subtask(manager.getCounterId(), "Subtask2", "SubtaskDescriptionTwo", epic1.getId());

        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        Epic epic2 = new Epic(manager.getCounterId(), "Epic2", "EpicDescriptionTwo");
        Subtask subtask3 = new Subtask(manager.getCounterId(), "Subtask3", "SubtaskDescriptionThree", epic2.getId());

        manager.createEpic(epic2);
        manager.createSubtask(subtask3);

        manager.printAllTasks();

        System.out.println("\nCHANGE STATUS\n");
        subtask3.setStatus(TypeStatus.DONE);
        manager.updateSubtask(subtask3);

        subtask2.setStatus(TypeStatus.IN_PROGRESS);
        manager.updateSubtask(subtask2);

        manager.printAllTasks();

        System.out.println("\nREMOVE TASKS AND EPIC\n");
        manager.removeAllTasks();
        manager.deleteById(subtask1.getIdEpic());

        manager.printAllTasks();
    }
}
