import java.util.Scanner;

public class Zack {
    private static final int MAX_TASKS = 100;
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);

        printGreeting();

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            printHorizontalLine();

            if (command.equals("bye")) {
                printGoodbye();
                break;
            }

            try {
                taskCount = executeCommand(command, tasks, taskCount);
            } catch (ZackException e) {
                System.out.println(" " + e.getMessage());
            }
            printHorizontalLine();
        }
    }

    private static int executeCommand(String command, Task[] tasks, int taskCount)
            throws ZackException {
        if (command.equals("todo")) {
            throw new ZackException("Please describe the todo, for example: todo read book.");
        }

        if (command.equals("list")) {
            printTaskList(tasks, taskCount);
        } else if (command.startsWith("mark ")) {
            markTask(command, tasks, taskCount);
        } else if (command.startsWith("unmark ")) {
            unmarkTask(command, tasks, taskCount);
        } else if (command.startsWith("event ")) {
            return addEvent(command, tasks, taskCount);
        } else if (command.startsWith("deadline ")) {
            return addDeadline(command, tasks, taskCount);
        } else if (command.startsWith("todo ")) {
            return addTodo(command, tasks, taskCount);
        } else {
            throw new ZackException(
                    "Unknown command. Use todo, deadline, event, list, mark, unmark, or bye.");
        }
        return taskCount;
    }

    private static void printTaskList(Task[] tasks, int taskCount) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + "." + tasks[i]);
        }
    }

    private static void markTask(String command, Task[] tasks, int taskCount)
            throws ZackException {
        int taskIndex = parseTaskIndex(command, taskCount);
        tasks[taskIndex].markAsDone();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + tasks[taskIndex]);
    }

    private static void unmarkTask(String command, Task[] tasks, int taskCount)
            throws ZackException {
        int taskIndex = parseTaskIndex(command, taskCount);
        tasks[taskIndex].markAsNotDone();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + tasks[taskIndex]);
    }

    private static int parseTaskIndex(String command, int taskCount) throws ZackException {
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

    private static int addEvent(String command, Task[] tasks, int taskCount)
            throws ZackException {
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
        return addTypedTask(event, tasks, taskCount);
    }

    private static int addDeadline(String command, Task[] tasks, int taskCount)
            throws ZackException {
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
        return addTypedTask(deadline, tasks, taskCount);
    }

    private static int addTodo(String command, Task[] tasks, int taskCount)
            throws ZackException {
        String description = command.substring("todo ".length()).trim();
        if (description.isEmpty()) {
            throw new ZackException("Please describe the todo, for example: todo read book.");
        }

        Task todo = new Todo(description);
        return addTypedTask(todo, tasks, taskCount);
    }

    private static int addTypedTask(Task task, Task[] tasks, int taskCount)
            throws ZackException {
        if (taskCount >= tasks.length) {
            throw new ZackException("The task list is full. No task was added.");
        }

        tasks[taskCount] = task;
        int updatedTaskCount = taskCount + 1;

        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks[taskCount]);
        System.out.println(" Now you have " + updatedTaskCount + " tasks in the list.");

        return updatedTaskCount;
    }

    private static int addTask(String command, Task[] tasks, int taskCount) {
        tasks[taskCount] = new Task(command);
        int updatedTaskCount = taskCount + 1;
        System.out.println(" added: " + command);
        System.out.println(" Now you have " + updatedTaskCount + " tasks in the list.");
        return updatedTaskCount;
    }

    private static void printGreeting() {
        printHorizontalLine();
        System.out.println(" Hello! I'm Zack");
        System.out.println(" What can I do for you?");
        printHorizontalLine();
    }

    private static void printGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
        printHorizontalLine();
    }

    private static void printHorizontalLine() {
        System.out.println(HORIZONTAL_LINE);
    }
}
