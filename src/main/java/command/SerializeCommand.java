package command;

import java.io.Serializable;

public class SerializeCommand implements Serializable {
    private CommandAbstract commandAbstract;
    private Object object;
    private String arg;

    public SerializeCommand (CommandAbstract commandAbstract, String arg, Object object){
        this.commandAbstract = commandAbstract;
        this.arg = arg;
        this.object = object;
    }

    public SerializeCommand (CommandAbstract commandAbstract, String arg){
        this.commandAbstract = commandAbstract;
        this.arg = arg;
        this.object = null;
    }

    public SerializeCommand (CommandAbstract commandAbstract, Object object){
        this.commandAbstract = commandAbstract;
        this.arg = null;
        this.object = object;
    }
    public SerializeCommand (CommandAbstract commandAbstract){
        this.commandAbstract = commandAbstract;
        this.arg = null;
        this.object = null;
    }

    public CommandAbstract getCommandAbstract(){
        return commandAbstract;
    }

    public String getArg(){
        return arg;
    }

    public Object getObject(){
        return object;
    }

}
