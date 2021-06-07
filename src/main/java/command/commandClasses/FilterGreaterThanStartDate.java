package command.commandClasses;

import command.CommandAbstract;
import command.CommandReceiver;

import java.io.IOException;

public class FilterGreaterThanStartDate extends CommandAbstract {
    private static final long serialVersionUID = 32L;
    transient private CommandReceiver commandReceiver;
    private final int commandType = 2;

    public FilterGreaterThanStartDate(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public FilterGreaterThanStartDate() {
    }

    public int getCommandType(){
        return commandType;
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length == 2){
            commandReceiver.filterGreaterThanStartDate(args[0]);
        }else {
            System.out.println("Command is incorrect. Please try again.");
        }
    }

    @Override
    protected void writeInfo() {
        System.out.println("Show elements with value of field \"StartDate\", which is bigger than indicated one");
    }
}
