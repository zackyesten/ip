package zack;

import java.io.IOException;

public class Zack {
    private static final Ui ui = new Ui();

    public static void main(String[] args) {
        Storage storage = new Storage();
        TaskList tasks;
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showMessage(" Cannot load data/zack.properties: " + e.getMessage());
            return;
        }
        int taskCount = tasks.size();

        printGreeting();

        while (ui.hasNextCommand()) {
            String command = ui.readCommand();
            printHorizontalLine();

            if (command.equals("bye")) {
                new ExitCommand().execute(tasks, ui);
                printHorizontalLine();
                break;
            }

            try {
                taskCount = executeCommand(command, tasks, taskCount);
                if (!command.equals("list")) {
                    storage.save(tasks.snapshot());
                }
            } catch (ZackException e) {
                ui.showMessage(" " + e.getMessage());
            } catch (IOException e) {
                ui.showMessage(" Could not save changes to disk: " + e.getMessage());
                ui.showMessage(" Changes remain in memory but have not been saved.");
            }
            printHorizontalLine();
        }
    }

    private static int executeCommand(String command, TaskList tasks, int taskCount)
            throws ZackException {
        if (command.equals("todo")) {
            throw new ZackException("Please describe the todo, for example: todo read book.");
        }

        if (command.equals("list")) {
            printTaskList(tasks, taskCount);
        } else if (command.equals("delete") || command.startsWith("delete ")) {
            return deleteTask(command, tasks);
        } else if (command.startsWith("mark ")) {
            markTask(command, tasks, taskCount);
        } else if (command.startsWith("unmark ")) {
            unmarkTask(command, tasks, taskCount);
        } else if (command.startsWith("event ")) {
            return addTypedTask(Parser.parseEvent(command), tasks, taskCount);
        } else if (command.startsWith("deadline ")) {
            return addTypedTask(Parser.parseDeadline(command), tasks, taskCount);
        } else if (command.startsWith("todo ")) {
            return addTypedTask(Parser.parseTodo(command), tasks, taskCount);
        } else {
            throw new ZackException(
                    "Unknown command. Use todo, deadline, event, list, mark, unmark, delete, or bye.");
        }
        return taskCount;
    }

    private static void printTaskList(TaskList tasks, int taskCount) {
        new ListCommand().execute(tasks, ui);
    }

    private static void markTask(String command, TaskList tasks, int taskCount)
            throws ZackException {
        int taskIndex = Parser.parseTaskIndex(command, taskCount);
        new MarkCommand(taskIndex, true).execute(tasks, ui);
    }

    private static void unmarkTask(String command, TaskList tasks, int taskCount)
            throws ZackException {
        int taskIndex = Parser.parseTaskIndex(command, taskCount);
        new MarkCommand(taskIndex, false).execute(tasks, ui);
    }

    private static int addTypedTask(Task task, TaskList tasks, int taskCount)
            throws ZackException {
        Command command = new AddCommand(task);
        command.execute(tasks, ui);
        return tasks.size();
    }

    private static int deleteTask(String command, TaskList tasks)
            throws ZackException {
        if (command.equals("delete")) {
            throw new ZackException("Please provide a task number, for example: delete 1.");
        }
        int taskIndex = Parser.parseTaskIndex(command, tasks.size());
        new DeleteCommand(taskIndex).execute(tasks, ui);
        return tasks.size();
    }

    private static void printGreeting() {
        printHorizontalLine();
        ui.showMessage(" Hello! I'm Zack");
        ui.showMessage(" What can I do for you?");
        printHorizontalLine();
    }

    private static void printHorizontalLine() {
        ui.showLine();
    }
}
