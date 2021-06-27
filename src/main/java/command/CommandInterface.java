package command;

import exceptions.IncorrectArgumentException;
import exceptions.ValidationException;

/**
 * Interface for commands
 */
public interface CommandInterface {

    String getDescription();

    void exe(String arg) throws IncorrectArgumentException, ValidationException;
}
