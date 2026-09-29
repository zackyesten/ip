package zack.command;

import zack.Ui;
import zack.ZackException;
import zack.task.TaskList;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui) throws ZackException;

    public boolean isExit() {
        return false;
    }

    public boolean changesTasks() {
        return false;
    }
}