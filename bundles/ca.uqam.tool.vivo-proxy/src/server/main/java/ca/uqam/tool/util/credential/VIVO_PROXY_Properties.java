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
public class VIVO_PROXY_Properties {
	private static final String VIVO_PROXY_PROPERTIES = "vivo-proxy.properties";
    private static final String SPARQL_ENDPOINT_TYPE = "sparql-endPoint-type";
	public static final String SPARQL_ENDPOINT_TYPE_VIVO = "vivo";
	public static final String SPARQL_ENDPOINT_TYPE_NEPTUNE = "neptune";
	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";
	private static final String VIVO_URL = "vivo-url";
	private static final String VIVO_SITE = "vivo-site";
	private static final String VIVO_SPARQL_UPDATE_URL = "sparql-update-url";
	private static final String VIVO_SPARQL_QUERY_URL = "sparql-query-url";
	private static Properties loginProperties;
	public static String getSparqlEndpointType() {
		VIVO_PROXY_Properties.getInstance();
		return loginProperties.getProperty(SPARQL_ENDPOINT_TYPE);
	}
	public static void setSparqlEndpointType(String sparqlEndpointType) {
		VIVO_PROXY_Properties.getInstance();
		loginProperties.setProperty(SPARQL_ENDPOINT_TYPE, sparqlEndpointType);
	}

	private static VIVO_PROXY_Properties thisInstance;
	public static VIVO_PROXY_Properties getInstance()
	{
		if (thisInstance == null)
			thisInstance = new VIVO_PROXY_Properties();

		return thisInstance;
	}
	/**
	 * 
	 */
	private VIVO_PROXY_Properties(){
		try (InputStream input = VIVO_PROXY_Properties.class.getClassLoader().getResourceAsStream(VIVO_PROXY_PROPERTIES)) {
			if (input == null) {
				System.out.println("Sorry, unable to find " + VIVO_PROXY_PROPERTIES);
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
		VIVO_PROXY_Properties.getInstance();
		return loginProperties.getProperty(PASSWORD);    
	}
	/**
	 * @return the userName
	 */
	static public String getUserName() {
		VIVO_PROXY_Properties.getInstance();
		return loginProperties.getProperty(USERNAME);
	}
	static public String getVivoUrl() {
		VIVO_PROXY_Properties.getInstance();
		return loginProperties.getProperty(VIVO_URL);
	}
	static public String getVivoSite() {
		VIVO_PROXY_Properties.getInstance();
		return loginProperties.getProperty(VIVO_SITE);
	}

	static public String getIndvNS() {
		VIVO_PROXY_Properties.getInstance();
		return loginProperties.getProperty("indvNS");
	}
	static public String getSparqlQueryURL() {
		VIVO_PROXY_Properties.getInstance();
		return loginProperties.getProperty(VIVO_SPARQL_QUERY_URL);
	}
	static public String getSparqlUpdateURL() {
		VIVO_PROXY_Properties.getInstance();
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
		VIVO_PROXY_Properties.getInstance();
		loginProperties.setProperty(VIVO_SPARQL_QUERY_URL, sparqlQueryURL);
	}
	public static void setSparqlUpdateURL(String sparqlUpdateURL) {
		VIVO_PROXY_Properties.getInstance();
		loginProperties.setProperty(VIVO_SPARQL_UPDATE_URL, sparqlUpdateURL);
	}
	public static void setViVOAdminLogin(String viVOAdminLogin) {
		VIVO_PROXY_Properties.getInstance();
		loginProperties.setProperty(USERNAME, viVOAdminLogin);
	}
	public static void setViVOAdminPassword(String viVOAdminPassword) {
		VIVO_PROXY_Properties.getInstance();
		loginProperties.setProperty(PASSWORD, viVOAdminPassword);
	}
	public static void setVivoSiteUrl(String vivoURL) {
		VIVO_PROXY_Properties.getInstance();
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
		VIVO_PROXY_Properties.setVivoSiteUrl("http://toto.com:8080/vivo/toto");
		//get the property value and print it out
		System.out.println(VIVO_PROXY_Properties.getUserName());
		System.out.println(VIVO_PROXY_Properties.getPasswd());
	}

}
