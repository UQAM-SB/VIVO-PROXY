package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.model.Image;

public class AddImageToIndividualCommand extends Command {
	private Image image;

    public AddImageToIndividualCommand(Image image) {
        super();
        setImage(image);
        setName(toString());
    }

    @Override
	public CommandResult execute(Receiver vivo) {
        try {
            CommandResult result = ((VivoReceiver)vivo).addImageToIndividual(image);
            this.setCommandResult(result);
            return  result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public String toString() {
		return "AddImageToIndividualCommand [image=" + image + "]";
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
