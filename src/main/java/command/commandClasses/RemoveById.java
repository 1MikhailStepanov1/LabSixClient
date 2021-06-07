package command.commandClasses;

import command.CommandAbstract;
import command.CommandReceiver;

import java.io.IOException;

public class RemoveById extends CommandAbstract {
    private static final long serialVersionUID = 32L;
    transient private CommandReceiver commandReceiver;
    private final int commandType = 2;

    public RemoveById(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public RemoveById() {
    }

    public int getCommandType(){
        return commandType;
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length == 2) {
            commandReceiver.removeById(args[0]);
        } else {
            System.out.println("Command is incorrect.");
        }
    }

    @Override
    protected void writeInfo() {
        System.out.println("Delete element with indicated id");
    }
}
