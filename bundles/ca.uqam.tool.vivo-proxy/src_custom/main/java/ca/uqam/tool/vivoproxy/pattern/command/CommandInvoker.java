package ca.uqam.tool.vivoproxy.pattern.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;

public class CommandInvoker {
    private final static Logger LOGGER = Logger.getLogger(CommandInvoker.class.getName());
    private VivoReceiver vivoReceiver ;

public CommandInvoker() {
		super();
	}
    private HashMap<String, Command> commandMap = new HashMap<>();
    private List<Command> history = new ArrayList<Command>();

    public void register(String commandName, Command command) {
        LOGGER.info("Register ("+commandName+")");
        commandMap.put(commandName, command);
    }
    public void register(Command command) {
        register(command.getName(), command);
        history.add(command);
    }
    public CommandResult execute() throws IOException {
    	CommandResult commandResult = null;
		vivoReceiver=new VivoReceiver();
    	
    	for (Iterator iterator = history.iterator(); iterator.hasNext();) {
			Command command =  (Command) iterator.next();
			commandResult = command.execute(vivoReceiver);
//	        LOGGER.fine("result ("+command.getName()+ ": " +commandResult.getOkhttpResult().code()+") ("+commandResult.getOkhttpResult().body().string()+")");
//			int code = commandResult.getOkhttpResult().code();
//	        LOGGER.fine("result ("+command.getName()+ ") code (" + code +")");
	        LOGGER.fine("result ("+command.getName()+ ")");
		}
		return commandResult;
    }
    /*
     * Reset commands register stack
     */
	public void flush() {
	     commandMap = new HashMap<>();
	     history = new ArrayList<Command>();
	}
    
}
