package ca.uqam.tool.vivoproxy.pattern.command.concrete;
import java.io.IOException;
import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;

public class SparqlDescribeByLabelCommand extends Command {

    private String name;
    private String login;
    private String passwd;
    private String label;
	private String MINE_TYPE;

    public SparqlDescribeByLabelCommand(String login, String passwd, String label) {
        super();
        this.setLogin(login);
        this.setPasswd(passwd);
        this.setLabel(label);
        this.setMINE_TYPE("text/turtle");
        setName(toString());
    }

    public SparqlDescribeByLabelCommand(String login, String passwd, String label, String MINE_TYPE) {
        super();
        this.setLogin(login);
        this.setPasswd(passwd);
        this.setLabel(label);
        this.setMINE_TYPE(MINE_TYPE);
        setName(toString());
    }

	@Override
	public CommandResult execute(Receiver receiver) {
        CommandResult result = null;
        try {
        	if (label != null) {
        		result = ((VivoReceiver)receiver).DescribeByLabel(login, passwd, label, MINE_TYPE);
        	} 
            setCommandResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the passwd
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * @param passwd the passwd to set
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * @return the mINE_TYPE
     */
    public String getMINE_TYPE() {
        return MINE_TYPE;
    }

    /**
     * @param mINE_TYPE the mINE_TYPE to set
     */
    public void setMINE_TYPE(String mINE_TYPE) {
        MINE_TYPE = mINE_TYPE;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


}
