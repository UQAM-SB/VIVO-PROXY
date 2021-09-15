package ca.uqam.tool.vivoproxy.pattern.command.concrete;
import java.io.IOException;
import java.util.List;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;

public class SparqlDescribeCommand extends Command {

    private String name;
    private String login;
    private String passwd;
    private String iri;
    private List<String> iris;
	private String MINE_TYPE;

    public SparqlDescribeCommand(String login, String passwd, String iri) {
        super();
        this.setLogin(login);
        this.setPasswd(passwd);
        this.setIri(iri);
        this.setMINE_TYPE("text/turtle");
        setName(toString());
    }

    public SparqlDescribeCommand(String login, String passwd, String iri, String MINE_TYPE) {
        super();
        this.setLogin(login);
        this.setPasswd(passwd);
        this.setIri(iri);
        this.setMINE_TYPE(MINE_TYPE);
        setName(toString());
    }

    public SparqlDescribeCommand(String login, String passwd, List<String> iris, String MINE_TYPE) {
        super();
        this.setLogin(login);
        this.setPasswd(passwd);
        this.setIris(iris);
        this.setMINE_TYPE(MINE_TYPE);
        setName(toString());	}

	public CommandResult execute(Receiver receiver) {
        CommandResult result = null;
        try {
        	if (iri != null) {
        		result = ((VivoReceiver)receiver).DESCRIBE(login, passwd, iri, MINE_TYPE);
        	} else {
        		result = ((VivoReceiver)receiver).DESCRIBE(login, passwd, iris, MINE_TYPE);
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
     * @return the iri
     */
    public String getIri() {
        return iri;
    }

    /**
     * @param iri the iri to set
     */
    public void setIri(String iri) {
        this.iri = iri;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SparqlDescribeCommand [" + (name != null ? "name=" + name + ", " : "")
                + (login != null ? "login=" + login + ", " : "") + (iri != null ? "iri=" + iri + ", " : "")
                + (MINE_TYPE != null ? "MINE_TYPE=" + MINE_TYPE : "") + "]";
    }

	public List<String> getIris() {
		return iris;
	}

	public void setIris(List<String> iris) {
		this.iris = iris;
	}

}
