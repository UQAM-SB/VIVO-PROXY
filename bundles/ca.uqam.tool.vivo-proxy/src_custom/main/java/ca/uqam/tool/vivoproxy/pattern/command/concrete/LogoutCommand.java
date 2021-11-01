package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;
import java.util.UUID;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;


public class LogoutCommand extends Command {
    private String name;
    public LogoutCommand() {
        super();
        setName("Logout:"+UUID.randomUUID().toString());
    }
    public CommandResult execute(Receiver receiver) {
        CommandResult result = null;
        try {
            result = ((VivoReceiver)receiver).logout();
            setCommandResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
