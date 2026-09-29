package zack;

import java.util.Locale;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword.toLowerCase(Locale.ROOT);
    }

    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showMessage(" Here are the matching tasks:");
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().toLowerCase(Locale.ROOT).contains(keyword)) {
                ui.showMessage(" " + (i + 1) + "." + task);
                count++;
            }
        }
        ui.showMessage(" " + count + " matching tasks.");
    }
}