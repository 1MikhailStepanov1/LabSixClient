package command;

import exceptions.IncorrectArgumentException;
import utility.Invoker;

public class Exit extends CommandAbstract {
    private static final long serialVersionUID = 32L;
    transient final Invoker invoker;

    public Exit(Invoker invoker) {
        super("End programme without saving to the file");
        this.invoker = invoker;
    }

    @Override
    public void exe(String arg) throws IncorrectArgumentException{
        if (arg.length() > 0){
            throw new IncorrectArgumentException("Command doesn't need argument");
        }else {
            invoker.requestExit(this);
        }
    }
}
