package command.commandClasses;

import command.CommandAbstract;
import command.CommandReceiver;

import java.io.IOException;

public class GroupCountingByPosition extends CommandAbstract {
    private static final long serialVersionUID = 32L;
    transient private CommandReceiver commandReceiver;
    private final int commandType = 0;

    public GroupCountingByPosition(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public GroupCountingByPosition() {
    }

    public int getCommandType(){
        return commandType;
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length == 1){
            commandReceiver.groupCountingByPosition(args[0]);
        } else {
            System.out.println("Command is incorrect. Please try again.");
        }
    }

    @Override
    protected void writeInfo() {
        System.out.println("Group elements by field \"Position\" and show amount of elements in each group");
    }
}
