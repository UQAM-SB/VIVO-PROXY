package ca.uqam.tool.vivoproxy.pattern.command;

public interface Command {
    public CommandResult execute(VivoReceiver vivo);
    public String getName();
    public void setName(String name);
}
