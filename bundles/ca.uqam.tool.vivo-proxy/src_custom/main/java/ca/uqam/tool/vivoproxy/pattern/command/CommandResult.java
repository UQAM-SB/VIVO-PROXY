package ca.uqam.tool.vivoproxy.pattern.command;

import com.squareup.okhttp.Response;

import ca.uqam.tool.util.credential.LOGIN;

public class CommandResult {
    private Object result;
    private static String hostName = LOGIN.getVivoUrl();
    private static String vivoSiteName = LOGIN.getVivoSite();

    public Object getResult() {
        return result;
    }
    public boolean isOkhttpResult() {
    	if (result instanceof com.squareup.okhttp.Response) {
            return true;
    	}
        return false;
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
        CommandResult.hostName = hostName;
    }
    public static String getVivoSiteName() {
        return vivoSiteName;
    }
    public void setVivoSiteName(String vivoSiteName) {
        CommandResult.vivoSiteName = vivoSiteName;
    }
    protected String getSiteUrl() {
    	if (getVivoSiteName().isEmpty()){
            return getHostName();
    	} else {
            return getHostName()+"/"+getVivoSiteName();
    	}
    }
}
