package ca.uqam.tool.vivoproxy.swagger.api.impl;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.tool.vivoproxy.swagger.api.OrganizationApiService;
import ca.uqam.vocab.proxy.model.LinguisticLabel;
import ca.uqam.vocab.proxy.model.Organization;

public class OrganizationApiServiceImplTest {
	   /**
     * @param args
     * @throws IOException
     * @throws OWLOntologyCreationException
     * @throws OWLOntologyStorageException
     * @throws NotFoundException 
     */
    public static void main(String[]  args) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException, NotFoundException {
        Organization organ = new Organization();
        LinguisticLabel label = new LinguisticLabel();
        label.setLabel("Test Organization 2");
        label.language("en-US");
        organ.setOrganizationType("http://vivoweb.org/ontology/core#University");
        organ.addNamesItem(label);
        OrganizationApiService service = new OrganizationApiServiceImpl();
        Response response = service.createOrganization(organ, null);
        System.out.println(response);
        System.out.println(response.getEntity().toString());
        System.out.println("Done!");
    }
}
