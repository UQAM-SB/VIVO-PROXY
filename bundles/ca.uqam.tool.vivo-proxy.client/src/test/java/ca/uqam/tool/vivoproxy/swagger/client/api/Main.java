package ca.uqam.tool.vivoproxy.swagger.client.api;

import java.util.Locale;

import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
import ca.uqam.tool.vivoproxy.swagger.client.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.client.model.ModelAPIResponse;
import ca.uqam.tool.vivoproxy.swagger.client.model.Organization;
import ca.uqam.vocab.vivo.VIVO;


public class Main {

	public static void main(String[] args) throws ApiException {
		ApiClient apiClient = new ApiClient();
		apiClient.setBasePath("http://vivo-proxy.ca-central-1.elasticbeanstalk.com/vivo-proxy");
		Organization org = new Organization();
		org.setId("test4");
		LinguisticLabel cLabelFr = new LinguisticLabel();
		cLabelFr.setLabel("Dept Test4");
		cLabelFr.setLanguage(Locale.CANADA_FRENCH.toLanguageTag());
		org.addNamesItem(cLabelFr);
		org.addOverviewItem(cLabelFr);
		org.setOrganizationType(VIVO.AcademicDepartment.getURI());
		OrganizationApi orgApi = new OrganizationApi(apiClient);
		ModelAPIResponse resu = orgApi.createOrganization(org);
		System.out.println(resu.toString());
		System.out.println("Done");
	}

}
