package ca.uqam.tool.vivoproxy.pattern.command;

import java.util.List;

import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddAuthorOfDocumentCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddConceptCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddDocumentCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddHasResearchAreaCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddImageToIndividualCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddOrganizationCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddPersonCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddPersonListCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddResearchAreaOfCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddTypeToIndividualCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.CreatePositionForCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.LoginCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.LogoutCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.SparqlDescribeByLabelCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.SparqlDescribeCommand;
import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.Document;
import ca.uqam.tool.vivoproxy.swagger.model.Image;
import ca.uqam.tool.vivoproxy.swagger.model.IndividualType;
import ca.uqam.tool.vivoproxy.swagger.model.Person;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
import ca.uqam.tool.vivoproxy.swagger.model.ResourceToResource;

/**
 * @author Michel Héon; Université du Québec à Montréal
 * @filename CommandFactory.java
 * @date 22 sept. 2021
 */
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
    /**
     * @param username
     * @param password
     * @return
     */
    public Command createLogin(String username, String password) { 
        LoginCommand login = new LoginCommand(username, password);
        return login;
    }
    /**
     * @return
     */
    public Command createLogout() {
        LogoutCommand aCommand = new LogoutCommand();
        return aCommand;
    }
    /**
     * @param organisationName
     * @param vivoOrganisationType
     * @return
     */
    public Command createOrganization(String organisationName, String vivoOrganisationType) {
        AddOrganizationCommand addOrganizationCommand = new AddOrganizationCommand(organisationName, vivoOrganisationType);
        return addOrganizationCommand;
    }
    /**
     * @param login
     * @param passwd
     * @param iri
     * @return
     */
    public Command createSparqlDescribeCommand(String login, String passwd, String iri) {
        return createSparqlDescribeCommand(login, passwd, iri, "text/turtle");
    }
    /**
     * @param login
     * @param passwd
     * @param iri
     * @param MINE_TYPE
     * @return
     */
    public Command createSparqlDescribeCommand(String login, String passwd, String iri, String MINE_TYPE) {
        SparqlDescribeCommand sparqlDescribeCommand = new SparqlDescribeCommand(login, passwd, iri, MINE_TYPE);
        return sparqlDescribeCommand;
    }
    /**
     * @param login
     * @param passwd
     * @param label
     * @param MINE_TYPE
     * @return
     */
    public Command createSparqlDescribeByLabelCommand(String login, String passwd, String label, String MINE_TYPE) {
    	SparqlDescribeByLabelCommand sparqlDescribeByLabelCommand = new SparqlDescribeByLabelCommand(login, passwd, label, MINE_TYPE);
        return sparqlDescribeByLabelCommand;
    }

    /**
     * @param body
     * @return
     */
    public Command createPositionFor(PositionOfPerson body) {
        CreatePositionForCommand createPositionForCommand = new CreatePositionForCommand(body);
        return createPositionForCommand;
    }
	/**
	 * @param person
	 * @return
	 */
	public Command createAddPerson(Person person) {
        AddPersonCommand addPersonCmd = new AddPersonCommand(person);
		return addPersonCmd;
	}
	/**
	 * @param personsList
	 * @return
	 */
	public Command createAddPerson(List<Person> personsList) {
		AddPersonListCommand addPersonCmd = new AddPersonListCommand(personsList);
		return addPersonCmd;
	}
	
	/**
	 * @param indvType
	 * @return
	 */
	public Command createAddTypeToIndividual(IndividualType indvType) {
		AddTypeToIndividualCommand addTypeToIndividualCommand = new AddTypeToIndividualCommand(indvType);
		return addTypeToIndividualCommand;
	}
	/**
	 * @param login
	 * @param passwd
	 * @param uris
	 * @param MINE_TYPE
	 * @return
	 */
	public Command createSparqlDescribeCommand(String login, String passwd, List<String> uris, String MINE_TYPE) {
        SparqlDescribeCommand sparqlDescribeCommand = new SparqlDescribeCommand(login, passwd, uris, MINE_TYPE);
        return sparqlDescribeCommand;
	}
	/**
	 * @param login
	 * @param passwd
	 * @param concept
	 * @param MINE_TYPE
	 * @return
	 */
	public Command createAddConceptCommand(Concept concept) {
		AddConceptCommand command = new AddConceptCommand(concept);
        return command;
	}
	/**
	 * @param login
	 * @param passwd
	 * @param resourcesToLink
	 * @param MINE_TYPE
	 * @return
	 */
	public Command createAddResearchAreaOfCommand(String login, String passwd, ResourceToResource resourcesToLink, String MINE_TYPE) {
		AddResearchAreaOfCommand command = new AddResearchAreaOfCommand(login, passwd, resourcesToLink, MINE_TYPE);
        return command;
	}
	/**
	 * @param login
	 * @param passwd
	 * @param resourcesToLink
	 * @param MINE_TYPE
	 * @return
	 */
	public Command createAddHasResearchAreaCommand(String login, String passwd, ResourceToResource resourcesToLink, String MINE_TYPE) {
		AddHasResearchAreaCommand command = new AddHasResearchAreaCommand(login, passwd, resourcesToLink, MINE_TYPE);
        return command;
	}
	/**
	 * @param document
	 * @return
	 */
	public AddDocumentCommand createAddDocumentCommand(Document document) {
		AddDocumentCommand addDocumentCmd = new AddDocumentCommand(document);
		return addDocumentCmd;
	}
	/**
	 * @param image
	 * @return
	 */
	public AddImageToIndividualCommand createAddImageToIndividualCommand(Image image) {
		AddImageToIndividualCommand addImageToIndividualCommand = new AddImageToIndividualCommand(image);
		return addImageToIndividualCommand;
	}
	/**
	 * @param author
	 * @return
	 */
	public Command addAuthorOfDocument(AuthorOfADocument author) {
		AddAuthorOfDocumentCommand addDocumentCmd = new AddAuthorOfDocumentCommand(author);
		return addDocumentCmd;
	}
}
