package ca.uqam.tool.vivoproxy.swagger.api.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import ca.uqam.tool.vivoproxy.swagger.api.ApiResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.tool.vivoproxy.swagger.api.OrganizationApiService;
import ca.uqam.tool.vivoproxy.swagger.model.Organization;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-10T18:18:23.710-04:00")
public class OrganizationApiServiceImpl extends OrganizationApiService {
    private static final String PASSWD = "Vivo2435....";
    private static final String LOGIN = "vivo@uqam.ca";
    @Override
    public Response createOrganization(Organization body, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        run(body);
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "overlay magic!")).build();
    }
    private void run(Organization body) {
        // TODO Auto-generated method stub
        
    }
    public static void main(String[] args) {
        OrganizationApiServiceImpl organizationApiServiceImpl = new OrganizationApiServiceImpl();
        Organization organization = new Organization();
        String username = LOGIN;
        String password = PASSWD;
    }
}

