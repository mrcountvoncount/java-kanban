package tasks;

import statusInfo.Status;

import java.util.Objects;

public class Task {
    protected String description;
    protected int id;
    protected String name;
    protected Status status;

    public Task(String description, String name, Status status) {
        this.description = description;
        this.name = name;
        this.status = status;
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    public String getDescription() {
        return description;
    }


    public void setId(int newId) {
        id = newId;
    }

    public int getId() {
        return id;
    }


    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }

    public void setStatus(Status newStatus) {
        status = newStatus;
    }

    public Status getStatus() {
        return status;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return id == task.id && Objects.equals(description, task.description) && Objects.equals(name, task.name)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
