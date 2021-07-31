package ca.uqam.tool.vivoproxy.swagger.api;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
    info = @Info(
        title = "Swagger Server", 
        version = "1.0.0", 
        description = "Proxy API for VIVO Data Manipulation",
        termsOfService = "http://swagger.io/terms/",
        contact = @Contact(email = "vivo@uqam.ca"),
        license = @License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE2.0.html"
        )
    )
)
public class Bootstrap {
}
