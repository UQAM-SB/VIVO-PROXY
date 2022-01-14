package ca.uqam.tool.util.credential;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * @author Michel Héon; Université du Québec à Montréal
 * @filename LOGIN.java
 * @date 22 sept. 2021
 */
public class LOGIN {
	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";
	private static final String VIVO_URL = "vivo-url";
	private static final String VIVO_SITE = "vivo-site";
	private static final String VIVO_SPARQL_UPDATE_URL = "vivo-sparql-update-url";
	private static final String VIVO_SPARQL_QUERY_URL = "vivo-sparql-query-url";
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
	/**
	 * @return the passwd
	 */
	static public String getPasswd() {
		LOGIN.getInstance();
		return loginProperties.getProperty(PASSWORD);    
	}
	/**
	 * @return the userName
	 */
	static public String getUserName() {
		LOGIN.getInstance();
		return loginProperties.getProperty(USERNAME);
	}
	static public String getVivoUrl() {
		LOGIN.getInstance();
		return loginProperties.getProperty(VIVO_URL);
	}
	static public String getVivoSite() {
		LOGIN.getInstance();
		return loginProperties.getProperty(VIVO_SITE);
	}

	static public String getIndvNS() {
		LOGIN.getInstance();
		return loginProperties.getProperty("indvNS");
	}
	static public String getSparqlQueryURL() {
		LOGIN.getInstance();
		return loginProperties.getProperty(VIVO_SPARQL_QUERY_URL);
	}
	static public String getSparqlUpdateURL() {
		LOGIN.getInstance();
		return loginProperties.getProperty(VIVO_SPARQL_UPDATE_URL);
	}
	static public String getVivoSiteUrl() {
    	if (getVivoSite().isEmpty()){
            return getVivoUrl();
    	} else {
            return getVivoUrl()+getVivoSite();
    	}
    }
	public static void setSparqlQueryURL(String sparqlQueryURL) {
		LOGIN.getInstance();
		loginProperties.setProperty(VIVO_SPARQL_QUERY_URL, sparqlQueryURL);
	}
	public static void setSparqlUpdateURL(String sparqlUpdateURL) {
		LOGIN.getInstance();
		loginProperties.setProperty(VIVO_SPARQL_UPDATE_URL, sparqlUpdateURL);
	}
	public static void setViVOAdminLogin(String viVOAdminLogin) {
		LOGIN.getInstance();
		loginProperties.setProperty(USERNAME, viVOAdminLogin);
	}
	public static void setViVOAdminPassword(String viVOAdminPassword) {
		LOGIN.getInstance();
		loginProperties.setProperty(PASSWORD, viVOAdminPassword);
	}
	public static void setVivoSiteUrl(String vivoURL) {
		LOGIN.getInstance();
		try {
			URL vivoSiteUrl = new URL(vivoURL);
			loginProperties.setProperty(VIVO_SITE,vivoSiteUrl.getPath());
			loginProperties.setProperty(VIVO_URL,vivoSiteUrl.toExternalForm().replace(vivoSiteUrl.getPath(), ""));
			System.out.println(loginProperties);
		} catch (MalformedURLException e) {
			// TODO Bloc catch auto-généré
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		LOGIN.setVivoSiteUrl("http://toto.com:8080/vivo/toto");
		//get the property value and print it out
		System.out.println(LOGIN.getUserName());
		System.out.println(LOGIN.getPasswd());
	}

}
