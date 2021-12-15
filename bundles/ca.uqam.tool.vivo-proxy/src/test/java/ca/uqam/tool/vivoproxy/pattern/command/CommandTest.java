package ca.uqam.tool.vivoproxy.pattern.command;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.squareup.okhttp.Response;

import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.model.Person;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
import ca.uqam.vocab.vivo.VIVO;

public class CommandTest {
	private final static Logger LOGGER = Logger.getLogger(CommandTest.class.getName());
	private String username;
	private String password;

	@Before
	public void setUp() throws Exception {
		username = "vivo@uqam.ca";
		password = "Vivo1234.";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddMultiplePersons() throws Exception {
		CommandInvoker invoker = new CommandInvoker();
		CommandFactory cf = CommandFactory.getInstance(); 
		Command loginCommand = cf.createLogin(username, password);
		invoker.register(loginCommand);
		
		List<Person> personsList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Person person = new Person();
			LinguisticLabel fn = new LinguisticLabel();
			fn.setLabel("firstName b "+i);
			LinguisticLabel ln = new LinguisticLabel();
			ln.setLabel("lastName "+i);
			person.addFirstNameItem(fn);
			person.addLastNameItem(ln);
			person.setPersonType(VIVO.EmeritusProfessor.toString());
			personsList.add(person);
		}
		Command createAddPersonCmd = cf.createAddPerson(personsList);
		invoker.register(createAddPersonCmd);
		invoker.register(cf.createLogout());
		invoker.execute();
		CommandResult results = createAddPersonCmd.getCommandResult();
		System.out.println(((List<String>) results.getResult()).get(0));
	}
	@Test
	public void testAddPerson() throws Exception {
		Person person = new Person();
		String firstName = "Pierre";
		String lastName = "Jacques";
		LinguisticLabel fn = new LinguisticLabel();
		fn.setLabel(firstName);
		LinguisticLabel ln = new LinguisticLabel();
		ln.setLabel(lastName);
		person.addFirstNameItem(fn);
		person.addLastNameItem(ln);
		
		person.setPersonType(VIVO.FacultyMember.toString());
		CommandFactory cf = CommandFactory.getInstance(); 
		CommandInvoker invoker = new CommandInvoker();
		Command loginCommand = cf.createLogin(username, password);
		Command addPersonCommand = cf.createAddPerson(person);
		invoker.register(loginCommand);
		invoker.register(addPersonCommand);
		invoker.register(cf.createLogout());
		invoker.execute();
		Response result = null;
		String persUri = null;
		try {
			result = addPersonCommand.getCommandResult().getOkhttpResult();
			persUri = VivoReceiverHelper.getUriResponse(result);
		} catch (Exception e) {
			fail("Fail to get URI of "+person.toString() + "return code ="+result);
		}
		assertNotNull(result);
		assertNotNull(persUri);
		LOGGER.info("Person added at: " + persUri);	
		LOGGER.info("testAddPerson Done!");	
	}

	@Test
	public void testCreateOrganisation() throws Exception {
		fail("Not yet finish implemented");
		String organisationName = "Collège du vieux Montréal";
		CommandFactory cf = CommandFactory.getInstance(); 
		CommandInvoker invoker = new CommandInvoker();
		Command loginCommand = cf.createLogin(username, password);
		invoker.register(loginCommand);
		String U_Title = "Université du Québec à Montréal";
		PositionOfPerson pop = new PositionOfPerson();;
		pop.setOrganisationIRI("http://localhost:8080/vivo/individual/n7843");
//		pop.setVivoOrganisationTypeIRI(VIVO.University.getURI());
		pop.setPersonIRI("http://localhost:8080/vivo/individual/n492");
//		pop.setPositionTitleLabel("Professeur");
		pop.setPositionTypeIRI(VIVO.FacultyPosition.getURI());
//		pop.setOrganisationLabel(U_Title);
		invoker.register(cf.createPositionFor(pop ));
		invoker.register(cf.createLogout());
		invoker.execute();

		LOGGER.info("Done!");	}
}
