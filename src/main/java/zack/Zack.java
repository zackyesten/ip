package zack;

import zack.command.Command;
import zack.task.TaskList;

import java.io.IOException;

public class Zack {
    private final Ui ui = new Ui();

    public static void main(String[] args) {
        new Zack().run();
    }

    public void run() {
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

            try {
                Command parsedCommand = Parser.parse(command, tasks.size());
                parsedCommand.execute(tasks, ui);
                if (parsedCommand.isExit()) {
                    printHorizontalLine();
                    break;
                }
                if (parsedCommand.changesTasks()) {
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

    private void printGreeting() {
        printHorizontalLine();
        ui.showMessage(" Hello! I'm Zack");
        ui.showMessage(" What can I do for you?");
        printHorizontalLine();
    }

    private void printHorizontalLine() {
        ui.showLine();
    }
}
