package ca.uqam.tool.util.credential;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

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
	private static Properties loginProperties;
	private static LOGIN login_instance;
	public static LOGIN getInstance()
	{
		if (login_instance == null)
			login_instance = new LOGIN();
		try (InputStream input = LOGIN.class.getClassLoader().getResourceAsStream("vivo-proxy.properties")) {
			if (input == null) {
				System.out.println("Sorry, unable to find vivo-proxy.properties");
			}

			loginProperties = new Properties();
			//load a properties file from class path, inside static method
			loginProperties.load(input);


		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return login_instance;
	}
	private LOGIN(){
	}
//	public static void main(String[] args) {
//		LOGIN.getInstance();
//		//get the property value and print it out
//		System.out.println(LOGIN.getUserName());
//		System.out.println(LOGIN.getPasswd());
//	}
	/**
	 * @return the passwd
	 */
	static public String getPasswd() {
		LOGIN.getInstance();
		return loginProperties.getProperty("password");    
	}
	/**
	 * @return the userName
	 */
	static public String getUserName() {
		LOGIN.getInstance();
		return loginProperties.getProperty("username");
	}
}
