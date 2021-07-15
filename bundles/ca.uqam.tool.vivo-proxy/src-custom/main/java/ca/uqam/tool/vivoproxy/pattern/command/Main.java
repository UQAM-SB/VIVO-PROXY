package ca.uqam.tool.vivoproxy.pattern.command;

import java.io.IOException;

/**
 * Ref: Command Design Pattern: https://dzone.com/articles/command-design-pattern-in-java
 */

import java.util.logging.Logger;

import com.squareup.okhttp.Request;

import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
import ca.uqam.vivo.vocabulary.VIVO;;

public class Main {
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * Main class for testing 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String username = "vivo@uqam.ca";
        String password = "Vivo1234.";
        String firstName = "Pierre1";
        String lastName = "Jacques2";
        String middleName = "Jean2";
        String organisationName = "Collège du vieux Montréal";
        CommandFactory cf = CommandFactory.getInstance(); 
        CommandInvoker invoker = new CommandInvoker();
        Command loginCommand = cf.createLogin(username, password);
        Command addPersonCommand = cf.createAddPerson(firstName, "2", lastName, VIVO.GraduateStudent.getURI());
        invoker.register(loginCommand);
        String U_Title = "Université du Québec à Montréal";
//        invoker.register(cf.createAddPerson(firstName, "1", lastName, VIVO.GraduateStudent.getURI()));
 //       invoker.register(cf.createOrganization("Université du Québec à Montréal", VIVO.University.getURI()));
        PositionOfPerson pop = new PositionOfPerson();;
        pop.setOrganisationIRI("http://localhost:8080/vivo/individual/n7843");
        pop.setVivoOrganisationTypeIRI(VIVO.University.getURI());
        pop.setPersonIRI("http://localhost:8080/vivo/individual/n492");
        pop.setPositionTitleLabel("Professeur");
        pop.setPositionTypeIRI(VIVO.FacultyPosition.getURI());
        pop.setOrganisationLabel(U_Title);
		invoker.register(cf.createPositionFor(pop ));
        		
        
//        invoker.register(cf.createAddPerson(firstName, "2", lastName, VIVO.EmeritusProfessor.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "3", lastName, VIVO.Librarian.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "4", lastName, VIVO.FacultyMember.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "5", lastName, VIVO.Student.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "6", lastName, VIVO.UndergraduateStudent.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "7", lastName, VIVO.GraduateStudent.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "8", lastName, VIVO.GraduateStudent.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "9", lastName, VIVO.GraduateStudent.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "10", lastName, VIVO.GraduateStudent.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "11", lastName, VIVO.GraduateStudent.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "12", lastName, VIVO.GraduateStudent.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "13", lastName, VIVO.GraduateStudent.getURI()));
//        invoker.register(cf.createAddPerson(firstName, "14", lastName, VIVO.GraduateStudent.getURI()));
        invoker.register(cf.createLogout());
        invoker.execute();

//        CommandSession vivoSession = new CommandSession();
//        invoker.setSession(vivoSession);
//        Command addPersonCommand = cf.createAddPerson(firstName, "2", lastName, VIVO.GraduateStudent.getURI());
 //       Command addMichelCommand = cf.createAddPerson("Michel", "2", "Héon", VIVO.GraduateStudent.getURI());
 //       Command addOrganizationCommand = cf.createOrganization(organisationName, VivoReceiver.VIVO_COLLEGE);
 //       Command addMemberOfCommand = cf.createAddMemberOf("n4851", "n868", "Professeur",  "2001", "2021", VivoReceiver.VIVO_COLLEGE);
//        invoker.execute();
//        invoker.execute(addPersonCommand);
//        invoker.execute(addMichelCommand);
//        invoker.execute(addOrganizationCommand);
        
        LOGGER.info("Done!");
    }
}
