public class Task {
    private String description;
    private int priority;
    private String time;

    public Task(String description, int priority, String time) {
        this.description = description;
        this.priority = priority;
        this.time = time;
    }

    public String getDescription() { return description; }
    public int getPriority() { return priority; }
    public String getTime() { return time; }

    @Override
    public String toString() {
        return "[" + priority + "] " + description + "    At: " + time;
    }
}
