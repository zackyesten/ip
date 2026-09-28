package zack;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showMessage(" Bye. Hope to see you again soon!");
    }
}