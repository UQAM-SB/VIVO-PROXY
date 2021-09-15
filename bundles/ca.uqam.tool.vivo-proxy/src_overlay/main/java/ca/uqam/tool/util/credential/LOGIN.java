package ca.uqam.tool.util.credential;



/*
 * 
Rename YOU_LOGIN.java to LOGIN.java
Replace     
    USER_NAME = "YOUR-VIVO_LOGIN";
    PASSWD = "YOUR-VIVO_LOGIN-PASSWD";
    
    by your VIVO_root credential (see :
            rootUser.emailAddress = 
            rootUser.passwordChangeRequired = false
            rootUser.password = 
      
    properties in the $VIVO_HOME/config/runtimes.properties
 */
public class LOGIN extends AbstractCredential{
    private static  String USER_NAME = "vivo@uqam.ca";
    private static  String PASSWD = "Vivo1234.";
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
