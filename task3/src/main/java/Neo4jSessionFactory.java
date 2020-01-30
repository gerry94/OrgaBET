package main.java;

import org.neo4j.ogm.config.*;
import org.neo4j.ogm.session.*;

public class Neo4jSessionFactory
{
	private final static Configuration configuration = new Configuration.Builder()
						.uri("bolt://localhost:7687")
						.credentials("neo4j", "test")
						.build();
	private final static SessionFactory sessionFactory = new SessionFactory(configuration, "main.java");
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
}