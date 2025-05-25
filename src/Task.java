public class Task {
    protected String title;
    protected String description;
    protected final int id;
    protected TypeStatus status;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public TypeStatus getStatus() {
        return status;
    }

    public void setStatus(TypeStatus status) {
        this.status = status;
    }

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TypeStatus.NEW;
    }

    @Override
    public String toString() {
        return "Название: " + title +
                "\nОписание: " + description +
                "\nСтатус: " + status;
    }

    public Task update(Task task) {
        this.title = task.getTitle();
        this.status = task.getStatus();
        this.description = task.getDescription();
        return this;
    }
}
