package model;

import java.util.Objects;

public class Task {
    protected String title;
    protected String description;
    protected int id;
    protected TypeStatus status;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = TypeStatus.NEW;
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && !description.isEmpty()) {
            this.description = description;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeStatus getStatus() {
        return status;
    }

    public void setStatus(TypeStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Название: " + title +
                "\nОписание: " + description +
                "\nСтатус: " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
