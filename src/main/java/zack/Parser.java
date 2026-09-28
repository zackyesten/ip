package zack;

public class Parser {
    public static int parseTaskIndex(String command, int taskCount) throws ZackException {
        String number = command.substring(command.indexOf(' ') + 1).trim();
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new ZackException("Please enter a whole task number, for example: mark 1.");
        }

        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new ZackException("There is no task with that number. Use list to check.");
        }
        return taskNumber - 1;
    }

    public static Task parseEvent(String command) throws ZackException {
        int fromIndex = command.indexOf(" /from ");
        int toIndex = command.indexOf(" /to ");
        if (fromIndex < "event ".length()
                || toIndex < fromIndex + " /from ".length()) {
            throw new ZackException("Use: event DESCRIPTION /from START /to END.");
        }

        String description = command.substring("event ".length(), fromIndex).trim();
        String from = command.substring(fromIndex + " /from ".length(), toIndex).trim();
        String to = command.substring(toIndex + " /to ".length()).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new ZackException("An event needs a description, a start time, and an end time.");
        }

        Task event = new Event(description, from, to);
        return event;
    }

    public static Task parseDeadline(String command) throws ZackException {
        int byIndex = command.indexOf(" /by ");
        if (byIndex < "deadline ".length()) {
            throw new ZackException("Use: deadline DESCRIPTION /by TIME.");
        }

        String description = command.substring("deadline ".length(), byIndex).trim();
        String by = command.substring(byIndex + " /by ".length()).trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new ZackException("A deadline needs both a description and a time.");
        }

        Task deadline = new Deadline(description, by);
        return deadline;
    }

    public static Task parseTodo(String command) throws ZackException {
        String description = command.substring("todo ".length()).trim();
        if (description.isEmpty()) {
            throw new ZackException("Please describe the todo, for example: todo read book.");
        }

        Task todo = new Todo(description);
        return todo;
    }
}
