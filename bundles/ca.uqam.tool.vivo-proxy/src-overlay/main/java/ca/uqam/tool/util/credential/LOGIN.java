package ca.uqam.tool.util.credential;

public class LOGIN extends AbstractCredential{
    private static  String PASSWD = "Vivo1234.";
    private static  String USER_NAME = "vivo@uqam.ca";
    /**
     * @return the passwd
     */
    static public String getPasswd() {
        return PASSWD;
    }
    /**
     * @return the login
     */
    static public String getUserName() {
        return USER_NAME;
    }
}
