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
                Command parsedCommand = Parser.parse(command, tasks.size());
                parsedCommand.execute(tasks, ui);
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
