package ca.uqam.tool.vivoproxy.pattern.command;

import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddMemberOfCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddOrganizationCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddPersonCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.LoginCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.SparqlDescribeCommand;

public class CommandFactory {
    private CommandInvoker invoker;
    /*
     * CommandReceiver has Singleton
     */
    private CommandFactory() {}
    private static class CommandFactoryHolder {
        static final CommandFactory SINGLE_INSTANCE = new CommandFactory();
    }
    public static CommandFactory getInstance() {
        return CommandFactoryHolder.SINGLE_INSTANCE;
    }
    public Command createLogin(String username, String password) {
        LoginCommand login = new LoginCommand(username, password);
        register(login);
        return login;
    }
    public Command createAddPerson(String firstName, String middleName, String lastName, String vivoPersonType) {
        AddPersonCommand addPersonCmd = new AddPersonCommand(firstName, middleName,lastName, vivoPersonType);
        register(addPersonCmd);
        return addPersonCmd;
        
    }
    public Command createOrganization(String organisationName, String vivoOrganisationType) {
        AddOrganizationCommand addOrganizationCommand = new AddOrganizationCommand(organisationName, vivoOrganisationType);
        register(addOrganizationCommand);
        return addOrganizationCommand;
    }
    public Command createAddMemberOf(String personUri, String organizationUri, String roleLabel, String startField_year, String endField_year, String vivoOrganisationType) {
        AddMemberOfCommand addOrganizationCommand = new AddMemberOfCommand( personUri,  organizationUri,  roleLabel,  startField_year,  endField_year,  vivoOrganisationType);
        register(addOrganizationCommand);
        return addOrganizationCommand;
    }
    private void register(Command aCommand) {
        if (getInvoker() != null )getInvoker().register(aCommand);
    }
    public CommandInvoker getInvoker() {
        return invoker;
    }
    public void setInvoker(CommandInvoker invoker) {
        this.invoker = invoker;
    }
    public Command createSparqlDescribeCommand(String login, String passwd, String iri) {
        SparqlDescribeCommand sparqlDescribeCommand = new SparqlDescribeCommand(login, passwd, iri);
        register(sparqlDescribeCommand);
        return sparqlDescribeCommand;
    }
}
