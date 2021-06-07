package command;

import java.io.IOException;
import java.io.Serializable;

public abstract class CommandAbstract implements Serializable {
    protected abstract void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException;
    protected abstract void writeInfo();
    private static final long serialVersionUID = 32L;
}
