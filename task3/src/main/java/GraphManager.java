package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.neo4j.driver.v1.*;
import main.java.models.Book;
import java.util.*;

public class GraphManager implements AutoCloseable
{
	/*
	//Configuration configuration = new Configuration.Builder()
	//	.uri("bolt://neo4j:test@localhost")
	//	.build();
	Neo4jSessionFactory sessionFactory = Neo4jSessionFactory.getInstance();
	private static final int DEPTH_LIST = 0;
	private static final int DEPTH_ENTITY = 1;
	//protected Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
	public void setup()	{
	
	}
	
	public void exit() {
	
	}
	
	Book find(Long id) {
		Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
		return session.load(getEntityType(), id, DEPTH_ENTITY);
	}
	
	Class<Book> getEntityType() {
		return Book.class;
	}*/
	private final Driver driver;
	public GraphManager(String uri, String user, String password ) {
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
	}
	
	@Override
	public void close() {
		driver.close();
	}
	
	//************************query di prova**********************************
	private static List<Book> matchBooks(Transaction tx, int userId, boolean rated)
	{
		List<Book> tmpBooks = new ArrayList<>();
		String query;
		if(rated) query = "MATCH (p:User)-[r:RATED]->(b:Book) WHERE p.user_id=\""+userId+"\" RETURN DISTINCT b.original_title, b.authors LIMIT 10";
		else query = "MATCH (p:User),(b:Book) WHERE NOT (p)-[:RATED]->(b) AND p.user_id=\""+userId+"\" RETURN b.original_title, b.authors LIMIT 10;"; //"MATCH (a:Book) RETURN DISTINCT a.original_title, a.authors LIMIT 10";
		
		StatementResult result = tx.run(query);
		while ( result.hasNext() ) {
			Record tmpRes = result.next();
			Book b = new Book();
			b.setTitle(tmpRes.get(0).asString());
			b.setAuthor(tmpRes.get(1).asString());
			tmpBooks.add(b);
		}
		//System.out.println(tmpBooks.toString());
		return tmpBooks;
	}
	
	public List<Book> getBooks(int userId, boolean rated) //rated indica se voglio la lista di libri letti&votati oppure no
	{
		try (Session session = driver.session()) {
			return session.readTransaction( new TransactionWork<List<Book>>() {
				@Override
				public List<Book> execute(Transaction tx) {
					return matchBooks(tx, userId, rated);
				}
			} );
		}
	}
	//**************************************************************************
}
