package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;
import java.util.List;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;

/**
 * @author Michel Héon; Université du Québec à Montréal
 * @filename AddTypeToIndividualCommand.java
 * @date 22 sept. 2021
 *
 */
public class AddLabelsToIndividualCommand extends Command {
	private String IRI;
	private List<LinguisticLabel> labels;

    /**
     * @param indvType
     */
    public AddLabelsToIndividualCommand(String IRI, List<LinguisticLabel> labels) {
    	super();
    	this.setIRI(IRI);
    	this.setLabels(labels);
        setName(toString());
    }

	@Override
    public CommandResult execute(Receiver vivo) {
        CommandResult result = null;
        try {
           result = ((VivoReceiver)vivo).addLabels(IRI, getLabels());
            setCommandResult(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	public String getIRI() {
		return IRI;
	}

	public void setIRI(String iRI) {
		IRI = iRI;
	}

	public List<LinguisticLabel> getLabels() {
		return labels;
	}

	public void setLabels(List<LinguisticLabel> labels) {
		this.labels = labels;
	}


}
