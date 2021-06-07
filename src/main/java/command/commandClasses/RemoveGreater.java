package command.commandClasses;

import command.CommandAbstract;
import command.CommandReceiver;

import java.io.IOException;

public class RemoveGreater extends CommandAbstract {
    private static final long serialVersionUID = 32L;
    transient private CommandReceiver commandReceiver;
    private final int commandType = 1;

    public RemoveGreater(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public RemoveGreater() {
    }

    public int getCommandType(){
        return commandType;
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1) {
            System.out.println("Command is incorrect. Please try again.");
        } else {
            commandReceiver.removeGreater();
        }
    }

    @Override
    protected void writeInfo() {
        System.out.println("Delete all elements from collection which are bigger than indicated one");
    }
}
