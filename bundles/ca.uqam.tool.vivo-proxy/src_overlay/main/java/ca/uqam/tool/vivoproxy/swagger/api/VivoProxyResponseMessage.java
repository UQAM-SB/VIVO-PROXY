package ca.uqam.tool.vivoproxy.swagger.api;

import javax.xml.bind.annotation.XmlTransient;

@javax.xml.bind.annotation.XmlRootElement
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-16T06:02:27.010-04:00")
public class VivoProxyResponseMessage {
    public static final int ERROR = 1;
    public static final int WARNING = 2;
    public static final int INFO = 3;
    public static final int OK = 4;
    public static final int TOO_BUSY = 5;

    int code;
    String type;
    String message;

    public VivoProxyResponseMessage(){}

    public VivoProxyResponseMessage(int code, String message){
        this.code = code;
        switch(code){
        case ERROR:
            setType("error");
            break;
        case WARNING:
            setType("warning");
            break;
        case INFO:
            setType("info");
            break;
        case OK:
            setType("ok");
            break;
        case TOO_BUSY:
            setType("too busy");
            break;
        default:
            setType("unknown");
            break;
        }
        this.message = message;
    }

    @XmlTransient
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
