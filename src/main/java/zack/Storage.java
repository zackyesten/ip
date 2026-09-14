package zack;

import java.io.Reader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Properties;

public class Storage {
    private final Path filePath = Path.of("data", "zack.properties");

    public void save(ArrayList<Task> tasks) throws IOException {
        Properties data = new Properties();
        data.setProperty("task.count", Integer.toString(tasks.size()));

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String prefix = "task." + i + ".";
            data.setProperty(prefix + "description", task.getDescription());
            data.setProperty(prefix + "done", Boolean.toString(task.isDone()));

            if (task instanceof Todo) {
                data.setProperty(prefix + "type", "T");
            } else if (task instanceof Deadline) {
                data.setProperty(prefix + "type", "D");
                data.setProperty(prefix + "by", ((Deadline) task).getBy());
            } else if (task instanceof Event) {
                data.setProperty(prefix + "type", "E");
                data.setProperty(prefix + "from", ((Event) task).getFrom());
                data.setProperty(prefix + "to", ((Event) task).getTo());
            } else {
                throw new IOException("Cannot save an unsupported task type.");
            }
        }

        Files.createDirectories(filePath.getParent());
        Path temporaryFile = filePath.resolveSibling("zack.properties.tmp");
        try (Writer writer = Files.newBufferedWriter(temporaryFile)) {
            data.store(writer, "Zack task data");
        }
        Files.move(temporaryFile, filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (Files.notExists(filePath)) {
            save(tasks);
            return tasks;
        }

        Properties data = new Properties();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            data.load(reader);
            int count = Integer.parseInt(requireProperty(data, "task.count"));
            if (count < 0 || count > data.size()) {
                throw new IOException("Invalid task count.");
            }

            int expectedFieldCount = 1;
            for (int i = 0; i < count; i++) {
                Task task = readTask(data, i);
                tasks.add(task);
                expectedFieldCount += 3;
                if (task instanceof Deadline) {
                    expectedFieldCount++;
                } else if (task instanceof Event) {
                    expectedFieldCount += 2;
                }
            }
            if (data.size() != expectedFieldCount) {
                throw new IOException("Unexpected fields or an incorrect task count.");
            }
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid data format in the task file.", e);
        }
        return tasks;
    }

    private Task readTask(Properties data, int index) throws IOException {
        String prefix = "task." + index + ".";
        String type = requireProperty(data, prefix + "type");
        String description = requireProperty(data, prefix + "description");
        String done = requireProperty(data, prefix + "done");

        if (!done.equals("true") && !done.equals("false")) {
            throw new IOException("Invalid completion status for task " + index + ".");
        }

        Task task;
        if (type.equals("T")) {
            task = new Todo(description);
        } else if (type.equals("D")) {
            String by = requireProperty(data, prefix + "by");
            task = new Deadline(description, by);
        } else if (type.equals("E")) {
            String from = requireProperty(data, prefix + "from");
            String to = requireProperty(data, prefix + "to");
            task = new Event(description, from, to);
        } else {
            throw new IOException("Unknown task type for task " + index + ".");
        }

        if (done.equals("true")) {
            task.markAsDone();
        }
        return task;
    }

    private String requireProperty(Properties data, String key) throws IOException {
        String value = data.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IOException("Missing or empty field: " + key);
        }
        return value;
    }
}