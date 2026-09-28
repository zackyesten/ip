package zack;

public class MarkCommand extends Command {
    private final int taskIndex;
    private final boolean isDone;

    public MarkCommand(int taskIndex, boolean isDone) {
        this.taskIndex = taskIndex;
        this.isDone = isDone;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) {
        Task task = tasks.get(taskIndex);
        if (isDone) {
            task.markAsDone();
            ui.showMessage(" Nice! I've marked this task as done:");
        } else {
            task.markAsNotDone();
            ui.showMessage(" OK, I've marked this task as not done yet:");
        }
        ui.showMessage("   " + task);
    }

    @Override
    public boolean changesTasks() {
        return true;
    }
}