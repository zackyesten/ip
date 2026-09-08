/**
 * Represents an invalid command or another expected task-manager error.
 */
public class ZackException extends Exception {
    public ZackException(String message) {
        super(message);
    }
}