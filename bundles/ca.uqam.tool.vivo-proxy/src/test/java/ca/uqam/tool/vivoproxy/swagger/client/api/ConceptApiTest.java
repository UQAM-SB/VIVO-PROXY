/*
 * VIVO Proxy API
 * Proxy API for VIVO Data Manipulation
 *
 * OpenAPI spec version: 1.0.0
 * Contact: vivo@uqam.ca
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package ca.uqam.tool.vivoproxy.swagger.client.api;

import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
import ca.uqam.tool.vivoproxy.swagger.client.model.Concept;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ConceptApi
 */
@Ignore
public class ConceptApiTest {

    private final ConceptApi api = new ConceptApi();

    /**
     * Create a concept(skos:Concept) in VIVO
     *
     * This can only be done by the logged in person.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createConceptTest() throws ApiException {
        Concept body = null;
        String response = api.createConcept(body);

        // TODO: test validations
    }
}
