import com.thoughtworks.xstream.*;

public class XMLConfig {
	
	private final String port, username, password;
	
	XMLConfig(String xml_path)
	{
		XMLConfig p = (XMLConfig)createXStream().fromXML(xml_path);
		this.port = p.port;
		this.username = p.username;
		this.password = p.password;
	}
	
	public final XStream createXStream()
	{
		XStream xs = new XStream();
		xs.useAttributeFor(XMLConfig.class, "port");
		xs.useAttributeFor(XMLConfig.class, "username");
		xs.useAttributeFor(XMLConfig.class, "password");
		return xs;
	}
	
	public String toString()
	{
		return createXStream().toXML(this);
	}
	
	public String getPort() { return this.port; }
	public String getUsername() { return this.username; }
	public String getPassword() { return this.password; }
	
}
