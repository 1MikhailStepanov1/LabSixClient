package command.commandClasses;

import command.CommandAbstract;
import command.CommandReceiver;

import java.io.IOException;

public class Add extends CommandAbstract {
    private static final long serialVersionUID = 32L;
    transient private CommandReceiver commandReceiver;
    private final int commandType = 1;

    public Add(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public Add() {
    }

    public int getCommandType(){
        return commandType;
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1) {
            System.out.println("Command does not require argument. Please try again.");
        } else {
            commandReceiver.add();
        }
    }

    @Override
    protected void writeInfo() {
        System.out.println("Add new element to collection.");
    }
}
