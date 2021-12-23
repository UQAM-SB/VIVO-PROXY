package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;
import java.util.UUID;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;


public class ReindexSolrCommand extends Command {
    private String name;
    public ReindexSolrCommand() {
        super();
        setName("Logout:"+UUID.randomUUID().toString());
    }
    @Override
	public CommandResult execute(Receiver receiver) throws IOException {
        CommandResult result = null;
        try {
            result = ((VivoReceiver)receiver).ReindexSolr();
            setCommandResult(result);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }


}
