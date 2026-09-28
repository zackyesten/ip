package zack;

public class DeleteCommand extends Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) {
        Task deletedTask = tasks.remove(taskIndex);
        ui.showMessage(" Removed this task:");
        ui.showMessage("   " + deletedTask);
        ui.showMessage(" Now you have " + tasks.size() + " tasks in the list.");
    }
}