package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;
import java.util.UUID;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.VivoReceiver;

public class LoginCommand implements Command {
    private String name;
    public LoginCommand(String username, String password) {
        super();
        this.password = password;
        this.username = username;
        setName("Login:"+UUID.randomUUID().toString());
    }

    private String password;
    private String username;


    public CommandResult execute(VivoReceiver vivo) {
        CommandResult result = null;
        try {
            result = vivo.login(username, password);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
