package command.commandClasses;

import command.CommandAbstract;
import command.CommandReceiver;

import java.io.IOException;

public class RemoveLower extends CommandAbstract {
    private static final long serialVersionUID = 32L;
    transient private CommandReceiver commandReceiver;
    private final int commandType = 1;

    public RemoveLower(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public RemoveLower() {
    }

    public int getCommandType(){
        return commandType;
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1){
            System.out.println("Command iis incorrect. Please try again.");
        }else{
            commandReceiver.removeLower();
        }
    }

    @Override
    protected void writeInfo() {
        System.out.println("Delete all elements from collection which are smaller than indicated one");
    }
}
