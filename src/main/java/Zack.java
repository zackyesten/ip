import java.util.Scanner;

public class Zack {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String[] tasks = new String[100];
        boolean[] isDone = new boolean[100];
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println(line);
        System.out.println(" Hello! I'm Zack");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        while (true) {
            String command = scanner.nextLine();
            System.out.println(line);

            if (command.equals("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            } else if (command.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    String status = isDone[i] ? "X" : " ";
                    System.out.println(" " + (i + 1) + ".[" + status + "] " + tasks[i]);
                }
            } else if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                isDone[taskIndex] = true;
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   [X] " + tasks[taskIndex]);
            } else if (command.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(command.substring(7)) - 1;
                isDone[taskIndex] = false;
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   [ ] " + tasks[taskIndex]);
            } else {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println(" added: " + command);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
            }

            System.out.println(line);
        }
    }
}
