package ca.uqam.tool.vivoproxy.pattern.command;

import java.util.logging.Logger;

import com.squareup.okhttp.Request;

import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;;

public class Main {
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * Main class for testing
     * @param args
     */
    public static void main(String[] args) {
        String username = "vivo@uqam.ca";
        String password = "Vivo2435....";
        String firstName = "Pierre1";
        String lastName = "Jacques2";
        String middleName = "Jean2";
        String organisationName = "Collège du vieux Montréal";
        CommandFactory cf = CommandFactory.getInstance();
        VivoReceiver vivoSession = new VivoReceiver();
        CommandInvoker invoker = new CommandInvoker();
        invoker.setSession(vivoSession);
        cf.setInvoker(invoker);
        Command loginCommand = cf.createLogin(username, password);
        Command addPersonCommand = cf.createAddPerson(firstName, "2", lastName, VivoReceiver.VIVO_GRADUATE_STUDENT);
        Command addMichelCommand = cf.createAddPerson("Michel", "2", "Héon", VivoReceiver.VIVO_GRADUATE_STUDENT);
        Command addOrganizationCommand = cf.createOrganization(organisationName, VivoReceiver.VIVO_COLLEGE);
 //       Command addMemberOfCommand = cf.createAddMemberOf("n4851", "n868", "Professeur",  "2001", "2021", VivoReceiver.VIVO_COLLEGE);
        invoker.execute(loginCommand);
        invoker.execute(addPersonCommand);
        invoker.execute(addMichelCommand);
        invoker.execute(addOrganizationCommand);
        
        LOGGER.info("Done!");
    }

    private static void createAddNewMemberOf() {
        //Select Person
        Request request = new Request.Builder()
                .url("http://192.168.7.23:8080/vivo/display/n4851")
                .method("GET", null)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", "http://192.168.7.23:8080/vivo/people")
                .addHeader("Cookie", "JSESSIONID=EF0DA5B5368375FC47A6F38E29F69C31")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .build();
        // Select memberof
        request = new Request.Builder()
                /*
                 * http://192.168.7.23:8080/vivo/editRequestDispatch?
                 * subjectUri=http://localhost:8080/vivo/individual/n4851
                 * &predicateUri=http://purl.obolibrary.org/obo/RO_0000053
                 * &domainUri=http://xmlns.com/foaf/0.1/Person
                 * &rangeUri=http://vivoweb.org/ontology/core#MemberRole
                 * */
                .url("http://192.168.7.23:8080/vivo/editRequestDispatch?subjectUri=http%3A%2F%2Flocalhost%3A8080%2Fvivo%2Findividual%2Fn4851&predicateUri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FRO_0000053&domainUri=http%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2FPerson&rangeUri=http%3A%2F%2Fvivoweb.org%2Fontology%2Fcore%23MemberRole")
                .method("GET", null)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", "http://192.168.7.23:8080/vivo/display/n4851")
                .addHeader("Cookie", "JSESSIONID=EF0DA5B5368375FC47A6F38E29F69C31")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .build();
        request = new Request.Builder()
                /*
"http://192.168.7.23:8080/vivo/edit/process?
roleActivityType=http://vivoweb.org/ontology/core#College&
activityLabel=
&activityLabelDisplay=Collège de Hull
&roleToActivityPredicate=
&existingRoleActivity=http://localhost:8080/vivo/individual/n868
&roleLabel=Professeur
&startField-year=2001
&endField-year=2021
&editKey=50439720
                 *
                 */
                .url("http://192.168.7.23:8080/vivo/edit/process?roleActivityType=http%3A%2F%2Fvivoweb.org%2Fontology%2Fcore%23College&activityLabel=&activityLabelDisplay=Coll%C3%A8ge+de+Hull&roleToActivityPredicate=&existingRoleActivity=http%3A%2F%2Flocalhost%3A8080%2Fvivo%2Findividual%2Fn868&roleLabel=Professeur&startField-year=2001&endField-year=2021&editKey=50439720")
                .method("GET", null)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", "http://192.168.7.23:8080/vivo/editRequestDispatch?subjectUri=http%3A%2F%2Flocalhost%3A8080%2Fvivo%2Findividual%2Fn4851&predicateUri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FRO_0000053&domainUri=http%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2FPerson&rangeUri=http%3A%2F%2Fvivoweb.org%2Fontology%2Fcore%23MemberRole")
                .addHeader("Cookie", "JSESSIONID=EF0DA5B5368375FC47A6F38E29F69C31")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .build();
        // TODO Auto-generated method stub
        
    }

}
