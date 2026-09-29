package zack;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Deadline extends Task {
    private final LocalDate by;

    public Deadline(String description, String by) {
        super(description);
        try {
            this.by = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Use a valid date in yyyy-MM-dd format, for example: 2026-10-02.", e);
        }
    }

    public String getBy() {
        return by.toString();
    }

    public LocalDate getDueDate() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + by.format(DateTimeFormatter.ofPattern("MMM dd uuuu", Locale.ENGLISH)) + ")";
    }
}