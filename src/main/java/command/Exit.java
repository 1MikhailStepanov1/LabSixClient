package command;

import utility.Invoker;

public class Exit extends CommandAbstract {
    private static final long serialVersionUID = 32L;
    transient final Invoker invoker;

    public Exit(Invoker invoker) {
        super("End programme without saving to the file");
        this.invoker = invoker;
    }

    @Override
    public void exe(String arg){
        if (arg.length() > 0){
            System.out.println("This command doesn't require argument. Please, try again.");
            return;
        }else {
            invoker.requestExit(this);
        }
    }
}
