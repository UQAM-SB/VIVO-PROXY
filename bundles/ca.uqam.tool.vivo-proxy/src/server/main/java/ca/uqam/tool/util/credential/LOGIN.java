package ca.uqam.tool.util.credential;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Michel Héon; Université du Québec à Montréal
 * @filename LOGIN.java
 * @date 22 sept. 2021
 */
public class LOGIN {
	private static Properties loginProperties;
	private static LOGIN login_instance;
	public static LOGIN getInstance()
	{
		if (login_instance == null)
			login_instance = new LOGIN();

		return login_instance;
	}
	/**
	 * 
	 */
	private LOGIN(){
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
	}
	public static void main(String[] args) {
		LOGIN.getInstance();
		//get the property value and print it out
		System.out.println(LOGIN.getUserName());
		System.out.println(LOGIN.getPasswd());
	}
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
	static public String getVivoUrl() {
		LOGIN.getInstance();
		return loginProperties.getProperty("vivo-url");
	}
	static public String getVivoSite() {
		LOGIN.getInstance();
		return loginProperties.getProperty("vivo-site");
	}

	static public String getIndvNS() {
		LOGIN.getInstance();
		return loginProperties.getProperty("indvNS");
	}
	static public String getSparqlQueryURL() {
		LOGIN.getInstance();
		return loginProperties.getProperty("vivo-sparql-query-url");
	}
	static public String getSparqlUpdateURL() {
		LOGIN.getInstance();
		return loginProperties.getProperty("vivo-sparql-update-url");
	}

}
