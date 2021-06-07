package command.commandClasses;

import command.CommandAbstract;
import command.CommandReceiver;

import java.io.IOException;

public class Update extends CommandAbstract {
    private static final long serialVersionUID = 32L;
    transient private CommandReceiver commandReceiver;
    private final int commandType = 3;

    public Update(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public Update() {
    }

    public int getCommandType(){
        return commandType;
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length == 2){
            commandReceiver.update(args[0]);
        }else {
            System.out.println("Command is incorrect. Please, try again.");
        }
    }

    @Override
    protected void writeInfo() {
        System.out.println("Update element with indicated id");
    }
}
