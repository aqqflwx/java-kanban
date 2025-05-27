public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        manager.createTask(new Task("Task1", "DescriptionOne"));
        manager.createTask(new Task("Task2", "DescriptionTwo"));

        Epic epic1 = new Epic("Epic1", "EpicDescriptionOne");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask1", "SubtaskDescriptionOne", epic1.getId());
        Subtask subtask2 = new Subtask("Subtask2", "SubtaskDescriptionTwo", epic1.getId());

        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        Epic epic2 = new Epic("Epic2", "EpicDescriptionTwo");
        manager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Subtask3", "SubtaskDescriptionThree", epic2.getId());
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
        manager.deleteSubtaskById(subtask1.getId());
        // manager.getEpics().get(epic1.getSubtasks().remove(subtask1.getId()));


        manager.printAllTasks();
    }
}
