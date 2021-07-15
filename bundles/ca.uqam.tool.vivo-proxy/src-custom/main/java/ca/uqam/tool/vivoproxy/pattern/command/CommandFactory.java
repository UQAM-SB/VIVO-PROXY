package ca.uqam.tool.vivoproxy.pattern.command;

import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddOrganizationCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddPersonCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.CreatePositionForCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.LoginCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.LogoutCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.SparqlDescribeCommand;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;

public class CommandFactory {
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
        return login;
    }
    public Command createLogout() {
        LogoutCommand aCommand = new LogoutCommand();
        return aCommand;
    }
    public Command createAddPerson(String firstName, String middleName, String lastName, String vivoPersonType) {
        AddPersonCommand addPersonCmd = new AddPersonCommand(firstName, middleName,lastName, vivoPersonType);
        return addPersonCmd;
        
    }
    public Command createOrganization(String organisationName, String vivoOrganisationType) {
        AddOrganizationCommand addOrganizationCommand = new AddOrganizationCommand(organisationName, vivoOrganisationType);
        return addOrganizationCommand;
    }
//    public Command createAddMemberOf(String personUri, String organizationUri, String roleLabel, String startField_year, String endField_year, String vivoOrganisationType) {
//        AddMemberOfCommand addOrganizationCommand = new AddMemberOfCommand( personUri,  organizationUri,  roleLabel,  startField_year,  endField_year,  vivoOrganisationType);
//        register(addOrganizationCommand);
//        return addOrganizationCommand;
//    }
    public Command createSparqlDescribeCommand(String login, String passwd, String iri) {
        return createSparqlDescribeCommand(login, passwd, iri, "text/turtle");
    }
    public Command createSparqlDescribeCommand(String login, String passwd, String iri, String MINE_TYPE) {
        SparqlDescribeCommand sparqlDescribeCommand = new SparqlDescribeCommand(login, passwd, iri, MINE_TYPE);
        return sparqlDescribeCommand;
    }
    public Command createPositionFor(PositionOfPerson body) {
        CreatePositionForCommand createPositionForCommand = new CreatePositionForCommand(body);
        return createPositionForCommand;
    }
}
