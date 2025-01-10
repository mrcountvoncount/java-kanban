package statusInfo;

public enum Status {
    NEW("New"),
    IN_PROGRESS("In Progress"),
    DONE("Done");
    private String nameOfStatus;

    Status() {
    }

    Status(String nameOfStatus) {
        this.nameOfStatus = nameOfStatus;
    }

    @Override
    public String toString() {
        return nameOfStatus;
    }
}
