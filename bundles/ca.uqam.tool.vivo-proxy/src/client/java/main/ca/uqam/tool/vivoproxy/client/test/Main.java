package ca.uqam.tool.vivoproxy.client.test;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.PersonApi;
import io.swagger.client.model.Person;

public class Main {

    public static void main(String[] args) throws ApiException {
        ApiClient client = new ApiClient();
        client.setBasePath("http://localhost:9090/vivoproxy/");
        client.setUsername("vivo@uqam.ca");
        client.setPassword("Vivo2435....");
        Person aPerson = new Person();
        aPerson.setFirstName("Michel");
        aPerson.setLastName("Héon");
        PersonApi persApi = new PersonApi(client);
        persApi.createPerson(aPerson);
    }
}
