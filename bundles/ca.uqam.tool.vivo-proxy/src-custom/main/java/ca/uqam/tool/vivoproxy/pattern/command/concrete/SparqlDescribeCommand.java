package ca.uqam.tool.vivoproxy.pattern.command.concrete;
import java.io.IOException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.SparqlQueryReceiver;

public class SparqlDescribeCommand implements Command {

    private String name;
    private String login;
    private String passwd;
    private String iri;

    public SparqlDescribeCommand(String login, String passwd, String iri) {
        super();
        this.setLogin(login);
        this.setPasswd(passwd);
        this.setIri(iri);
        setName(toString());
    }

    public CommandResult execute(Receiver receiver) {
        CommandResult result = null;
        try {
            
            result = ((SparqlQueryReceiver)receiver).DESCRIBE(login, passwd, iri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SparqlDescribeCommand [" + (name != null ? "name=" + name + ", " : "")
                + (login != null ? "login=" + login + ", " : "") + (passwd != null ? "passwd=secret" + ", " : "")
                + (iri != null ? "iri=" + iri : "") + "]";
    }

}
