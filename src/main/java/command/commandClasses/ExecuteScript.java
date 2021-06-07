package command.commandClasses;

import command.CommandAbstract;
import command.CommandReceiver;

import java.io.IOException;
import java.util.HashSet;

public class ExecuteScript extends CommandAbstract {
    private static final long serialVersionUID = 32L;
    transient private CommandReceiver commandReceiver;
    private HashSet<String> pathNames;
    private String path;

    public ExecuteScript(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public ExecuteScript() {
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        try{
            if (args.length == 2){
                path = args[1];
                commandReceiver.executeScript(args[1]);
            } else {
                System.out.println("Command is incorrect. Please, try again.");
            }
        } catch (StackOverflowError error){
            System.out.println("Stack overflow has been detected.");
        }
    }

    @Override
    protected void writeInfo() {
        System.out.println("Read and execute script from entered file.");
    }
}
