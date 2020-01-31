package main.java;

/*
import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
 */

public class Neo4jSessionFactory
{
	/*
	ConfigurationSource props = new ClasspathConfigurationSource("META-INF/neo4j.properties");
	Configuration configuration = new Configuration.Builder(props).build();
	SessionFactory sessionFactory = new SessionFactory(configuration, "main.java.models");
	private static Neo4jSessionFactory factory = new Neo4jSessionFactory();
	
	public static Neo4jSessionFactory getInstance() {
		return factory;
	}
	
	// prevent external instantiation
	private Neo4jSessionFactory() {
	}
	
	public Session getNeo4jSession() {
		return sessionFactory.openSession();
	}

	 */
}