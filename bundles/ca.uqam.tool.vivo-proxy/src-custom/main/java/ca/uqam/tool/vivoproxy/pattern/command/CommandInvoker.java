package ca.uqam.tool.vivoproxy.pattern.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import ca.uqam.tool.vivoproxy.pattern.command.receiver.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;

public class CommandInvoker {
    private final static Logger LOGGER = Logger.getLogger(CommandInvoker.class.getName());

//    /*
//     * CommandReceiver has Singleton
//     */
//    private CommandInvoker() {}
//    private static class CommandReceiverHolder {
//        static final CommandInvoker SINGLE_INSTANCE = new CommandInvoker();
//    }
//    public static CommandInvoker getInstance() {
//        return CommandReceiverHolder.SINGLE_INSTANCE;
//    }
//    /*
//     * CommandReceiver definition
//     */
    private final HashMap<String, Command> commandMap = new HashMap<>();
    private List<Command> history = new ArrayList<Command>();

    private Receiver session;

    public void register(String commandName, Command command) {
        LOGGER.info("Register ("+commandName+")");
        commandMap.put(commandName, command);
    }
    public void register(Command command) {
        register(command.getName(), command);
    }
    public CommandResult execute(String cmdName) {
        Command command = commandMap.get(cmdName);
        if (command == null) {
            throw new IllegalStateException("no command registered for " + cmdName);
        }
        this.history.add(command); // optional 
        return command.execute(session);
    }
    public CommandResult execute(Command command) {
        return execute(command.getName());
    }
    public void setSession(Receiver session) {
        this.session = session;
    }
}
