package command;

/**
 * Abstract command class contains name and description
 */
public class CommandAbstract implements CommandInterface {
    private final String description;

    public CommandAbstract(String description) {
        this.description = description;
    }

    /**
     * @return Description of the command
     */
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void exe(String arg) {

    }
}
