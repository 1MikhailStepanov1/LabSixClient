package command.commandClasses;

import command.CommandAbstract;
import command.CommandReceiver;

import java.io.IOException;

public class Info extends CommandAbstract {
    private static final long serialVersionUID = 32L;
    transient private CommandReceiver commandReceiver;
    private final int commandType = 0;


    public Info(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public Info() {


    }
    public int getCommandType(){
        return commandType;
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1) {
            System.out.println("Command is incorrect. Please try again.");
        } else {
            commandReceiver.info();
        }
    }

    @Override
    protected void writeInfo() {
        System.out.println("Show information about collection (type, initialization time and etc.)");
    }
}
