package tasks;

import statusInfo.Status;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskIds;

    public Epic(String description, String name) {
        super(description, name, Status.NEW);
        this.subtaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    public void setSubtaskIds(int id) {
        subtaskIds.add(id);
    }

    public void removeSubtaskById(SubTask subtask) {
        subtaskIds.remove((Integer)subtask.getId());

    }

    public void removeAllSubtask() {
        subtaskIds.clear();
    }


    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
