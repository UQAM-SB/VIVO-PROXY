package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;


public class SparqlQueryReceiver extends CommandResult implements Receiver {

    private Response response;
    private String hostName;
    private String vivoSiteName;
    public CommandResult DESCRIBE(String username, String passwd, String IRI) throws IOException{
        String bodyValue = 
                "email="+username+
                "&password="+passwd+
                "&query=DESCRIBE <"+IRI+">";

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body = RequestBody.create(mediaType, "email=vivo@uqam.ca&password=Vivo2435....&query=DESCRIBE <http://dbpedia.org/resource/Connecticut>");
        RequestBody body = RequestBody.create(mediaType, bodyValue);
        Request request = new Request.Builder()
                .url(getSiteUrl()+"/api/sparqlQuery")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        return CommandResult.asCommandResult(response);
    }
    public static void main(String[] args) throws IOException {
        String username = "vivo@uqam.ca";
        String password = "Vivo2435....";
        SparqlQueryReceiver describe = new SparqlQueryReceiver();
        String iri = "http://localhost:8080/vivo/individual/n6870";
        describe.setHostName("http://localhost:8080");
        describe.setVivoSiteName("vivo");
        CommandResult resu = describe.DESCRIBE(username, password, iri);
        Response response = (Response)resu.getResult();
        System.out.println(response.body().string());
            
    }
}
