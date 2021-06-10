package ca.uqam.tool.vivoproxy.pattern.command;

import com.squareup.okhttp.Response;

public class CommandResult {
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static CommandResult asCommandResult(Object response) {
        CommandResult cmdResult = new CommandResult();
        cmdResult.setResult(response);
        return cmdResult;
    }
}
