package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;
import java.util.UUID;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;


public class LoginCommand extends Command {
    private String name;
    public LoginCommand(String username, String password) {
        super();
        this.password = password;
        this.username = username;
        setName("Login:"+UUID.randomUUID().toString());
    }

    private String password;
    private String username;


    @Override
	public CommandResult execute(Receiver receiver) {
        CommandResult result = null;
        try {
            result = ((VivoReceiver)receiver).login(username, password);
            setCommandResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
 
}
