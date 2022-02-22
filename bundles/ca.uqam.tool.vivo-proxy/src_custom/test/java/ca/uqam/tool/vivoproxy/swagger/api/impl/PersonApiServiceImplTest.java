package ca.uqam.tool.vivoproxy.swagger.api.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.vocab.proxy.model.Person;
import ca.uqam.vivo.vocabulary.VIVO;

public class PersonApiServiceImplTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateUsersWithListInput() throws NotFoundException {
		PersonApiServiceImpl apiServ = new PersonApiServiceImpl();
		List<Person> personsList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Person person = new Person();
			person.setFirstName("firstName b "+i);
			person.setMiddleName("middleName "+i);
			person.setLastName("lastName "+i);
			person.setPersonType(VIVO.EmeritusProfessor.toString());
			personsList.add(person);
		}
		Response resp = apiServ.createUsersWithListInput(personsList, null);
		System.out.println(resp);
	}
	@Test
	public void testCreateUsersInput() throws NotFoundException {
		PersonApiServiceImpl apiServ = new PersonApiServiceImpl();
		Person person = new Person();
		person.setFirstName("firstName b ");
		person.setMiddleName("middleName ");
		person.setLastName("lastName ");
		person.setPersonType(VIVO.EmeritusProfessor.toString());
		Response resp = apiServ.createPerson(person, null);
		System.out.println(resp);
	}

}
