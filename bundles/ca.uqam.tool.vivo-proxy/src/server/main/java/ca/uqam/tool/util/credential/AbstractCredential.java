package ca.uqam.tool.util.credential;

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
