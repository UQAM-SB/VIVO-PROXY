package ca.uqam.tool.vivoproxy.pattern.command;

import com.squareup.okhttp.Response;

import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;

public class CommandResult {
    private Object result;
    private static String hostName = "http://localhost:8080";
    private static String vivoSiteName = "vivo";

    public Object getResult() {
        return result;
    }
    public Response getOkhttpResult() {
    	if (result instanceof com.squareup.okhttp.Response) {
            return (Response)this.getResult();
    	}
        return null;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static CommandResult asCommandResult(Object response) {
        CommandResult cmdResult = new CommandResult();
        cmdResult.setResult(response);
        return cmdResult;
    }
    public static String getHostName() {
        return hostName;
    }
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public static String getVivoSiteName() {
        return vivoSiteName;
    }
    public void setVivoSiteName(String vivoSiteName) {
        this.vivoSiteName = vivoSiteName;
    }
    protected String getSiteUrl() {
        return getHostName()+"/"+getVivoSiteName();
    }
}
