package taskManager;

import statusInfo.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.ArrayList;


public class TaskManager {
    private int id = 1;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private int generateId() {
        return id++;
    }

    public int createTask(Task task) {
        int newTaskId = generateId();
        task.setId(newTaskId);
        tasks.put(newTaskId, task);
        return newTaskId;
    }

    public int createEpic(Epic epic) {
        int newEpicId = generateId();
        epic.setId(newEpicId);
        epics.put(newEpicId, epic);
        return newEpicId;
    }

    public int createSubtask(SubTask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            int newSubtaskId = generateId();
            subtask.setId(newSubtaskId);
            subtasks.put(newSubtaskId, subtask);
            epic.setSubtaskIds(newSubtaskId);
            updateStatusEpic(epic);
            return newSubtaskId;
        } else {
            System.out.println("Epic not found");
            return -1;
        }
    }

    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Task not found");
        }
    }

    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        } else {
            System.out.println("Epic not found");
        }
    }

    public void deleteSubtaskById(int id) {
        SubTask subtask = subtasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.removeSubtaskById(subtask);
            updateStatusEpic(epic);
            subtasks.remove(id);
        } else {
            System.out.println("Subtask not found");
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.removeAllSubtask();
            updateStatusEpic(epic);
        }
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public SubTask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public ArrayList<Task> getAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Task list is empty");
            return new ArrayList<>();
        }
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        if (epics.isEmpty()) {
            System.out.println("Epic list is empty");
            return new ArrayList<>();
        }
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getAllSubtasks() {
        if (subtasks.isEmpty()) {
            System.out.println("Subtasks list is empty");
            return new ArrayList<>();
        }
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<SubTask> getAllSubtasksByEpicId(int id) {
        if (epics.containsKey(id)) {
            ArrayList<SubTask> subtasksNew = new ArrayList<>();
            Epic epic = epics.get(id);
            for (int i = 0; i < epic.getSubtaskIds().size(); i++) {
                subtasksNew.add(subtasks.get(epic.getSubtaskIds().get(i)));
            }
            return subtasksNew;
        } else {
            return new ArrayList<>();
        }
    }

    public boolean updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            return true;
        } else {
            System.out.println("Task not found");
            return false;
        }
    }

    public boolean updateEpic(Epic epic) {
        Epic currentEpic = epics.get(epic.getId());
        if (currentEpic != null) {
            currentEpic.setDescription(epic.getDescription());
            currentEpic.setName(epic.getName());
            epics.put(epic.getId(), currentEpic);
            return true;
        } else {
            return false;
        }
    }

    private boolean updateStatusEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            if (epic.getSubtaskIds().isEmpty()) {
                epic.setStatus(Status.NEW);
            } else {
                ArrayList<SubTask> subtasksNew = new ArrayList<>();
                int countDone = 0;
                int countNew = 0;

                for (int i = 0; i < epic.getSubtaskIds().size(); i++) {
                    subtasksNew.add(subtasks.get(epic.getSubtaskIds().get(i)));
                }

                for (SubTask subtask : subtasksNew) {
                    if (subtask.getStatus() == Status.DONE) {
                        countDone++;
                    }
                    if (subtask.getStatus() == Status.NEW) {
                        countNew++;
                    }
                    if (subtask.getStatus() == Status.IN_PROGRESS) {
                        epic.setStatus(Status.IN_PROGRESS);

                    }
                }

                if (countDone == epic.getSubtaskIds().size()) {
                    epic.setStatus(Status.DONE);
                } else if (countNew == epic.getSubtaskIds().size()) {
                    epic.setStatus(Status.NEW);
                } else {
                    epic.setStatus(Status.IN_PROGRESS);
                }

            }
        } else {
            System.out.println("Epic not found");
            return false;
        }
        return true;
    }

    public boolean updateSubtask(SubTask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            SubTask currentSubTask = subtasks.get(subtask.getId());
            if (currentSubTask != null && currentSubTask.getEpicId() == subtask.getEpicId()) {
                subtasks.put(subtask.getId(), subtask);
                Epic epic = epics.get(subtask.getEpicId());
                updateStatusEpic(epic);
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("Subtask not found");
            return false;
        }
    }

    public void printTasks() {
        if (tasks.size() == 0) {
            System.out.println("Task list is empty");
            return;
        }
        for (Task task : tasks.values()) {
            System.out.println("Task{" +
                    "description='" + task.getDescription() + '\'' +
                    ", id=" + task.getId() +
                    ", name='" + task.getName() + '\'' +
                    ", status=" + task.getStatus() +
                    '}');
        }
    }

    public void printEpics() {
        if (epics.size() == 0) {
            System.out.println("Epic list is empty");
            return;
        }
        for (Epic epic : epics.values()) {
            System.out.println("Epic{" +
                    "subtasksIds=" + epic.getSubtaskIds() +
                    ", description='" + epic.getDescription() + '\'' +
                    ", id=" + epic.getId() +
                    ", name='" + epic.getName() + '\'' +
                    ", status=" + epic.getStatus() +
                    '}');
        }
    }

    public void printSubtasks() {
        if (subtasks.size() == 0) {
            System.out.println("Subtask list is empty");
            return;
        }
        for (SubTask subtask : subtasks.values()) {
            System.out.println("Subtask{" +
                    "epicId=" + subtask.getEpicId() +
                    ", description='" + subtask.getDescription() + '\'' +
                    ", id=" + subtask.getId() +
                    ", name='" + subtask.getName() + '\'' +
                    ", status=" + subtask.getStatus() +
                    '}');
        }
    }
}
