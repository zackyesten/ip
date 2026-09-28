package zack;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui) throws ZackException;

    public boolean isExit() {
        return false;
    }

    public boolean changesTasks() {
        return false;
    }
}