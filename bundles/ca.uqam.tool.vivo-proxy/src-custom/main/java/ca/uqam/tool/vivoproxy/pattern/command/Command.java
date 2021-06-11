package ca.uqam.tool.vivoproxy.pattern.command;

import ca.uqam.tool.vivoproxy.pattern.command.receiver.Receiver;

public interface Command {
    public CommandResult execute(Receiver vivo);
    public String getName();
    public void setName(String name);
}
