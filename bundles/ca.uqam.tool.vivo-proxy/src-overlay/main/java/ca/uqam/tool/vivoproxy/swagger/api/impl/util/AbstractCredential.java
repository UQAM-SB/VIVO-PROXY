package ca.uqam.tool.vivoproxy.swagger.api.impl.util;

public abstract class AbstractCredential {

    private static String PASSWD = null;
    private static String LOGIN = null;
    /**
     * @return the passwd
     */
    static public String getPasswd() {
        return PASSWD;
    }
    /**
     * @return the login
     */
    static public String getLogin() {
        return LOGIN;
    }
}
