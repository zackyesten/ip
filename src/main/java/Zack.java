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

        while (true) {
            String command = scanner.nextLine();
            printHorizontalLine();

            if (command.equals("bye")) {
                printGoodbye();
                break;
            }

            taskCount = executeCommand(command, tasks, taskCount);
            printHorizontalLine();
        }
    }

    private static int executeCommand(String command, Task[] tasks, int taskCount) {
        if (command.equals("list")) {
            printTaskList(tasks, taskCount);
        } else if (command.startsWith("mark ")) {
            markTask(command, tasks);
        } else if (command.startsWith("unmark ")) {
            unmarkTask(command, tasks);
        } else if (command.startsWith("event ")) {
            return addEvent(command, tasks, taskCount);
        } else if (command.startsWith("deadline ")) {
            return addDeadline(command, tasks, taskCount);
        } else if (command.startsWith("todo ")) {
            return addTodo(command, tasks, taskCount);
        } else {
            return addTask(command, tasks, taskCount);
        }
        return taskCount;
    }

    private static void printTaskList(Task[] tasks, int taskCount) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + "." + tasks[i]);
        }
    }

    private static void markTask(String command, Task[] tasks) {
        int taskIndex = Integer.parseInt(command.substring("mark ".length())) - 1;
        tasks[taskIndex].markAsDone();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + tasks[taskIndex]);
    }

    private static void unmarkTask(String command, Task[] tasks) {
        int taskIndex = Integer.parseInt(command.substring("unmark ".length())) - 1;
        tasks[taskIndex].markAsNotDone();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + tasks[taskIndex]);
    }

    private static int addEvent(String command, Task[] tasks, int taskCount) {
        int fromIndex = command.indexOf(" /from ");
        int toIndex = command.indexOf(" /to ");

        String description = command.substring("event ".length(), fromIndex);
        String from = command.substring(fromIndex + " /from ".length(), toIndex);
        String to = command.substring(toIndex + " /to ".length());

        Task event = new Event(description, from, to);
        return addTypedTask(event, tasks, taskCount);
    }

    private static int addDeadline(String command, Task[] tasks, int taskCount) {
        int byIndex = command.indexOf(" /by ");
        String description = command.substring("deadline ".length(), byIndex);
        String by = command.substring(byIndex + " /by ".length());

        Task deadline = new Deadline(description, by);
        return addTypedTask(deadline, tasks, taskCount);
    }

    private static int addTodo(String command, Task[] tasks, int taskCount) {
        String description = command.substring("todo ".length());
        Task todo = new Todo(description);
        return addTypedTask(todo, tasks, taskCount);
    }

    private static int addTypedTask(Task task, Task[] tasks, int taskCount) {
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
