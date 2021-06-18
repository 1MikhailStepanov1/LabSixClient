package command;

/**
 * Interface for commands
 */
public interface CommandInterface {

    String getDescription();

    void exe(String arg);
}
