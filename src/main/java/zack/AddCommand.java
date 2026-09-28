package zack;

public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) {
        tasks.add(task);
        ui.showMessage(" Got it. I've added this task:");
        ui.showMessage("   " + task);
        ui.showMessage(" Now you have " + tasks.size() + " tasks in the list.");
    }
}