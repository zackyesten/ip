package zack;

import java.util.Scanner;

public class Ui {
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }
}