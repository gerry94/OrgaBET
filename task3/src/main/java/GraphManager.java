package main.java;

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
	
	//forse va chiamata browseBooks?
	private static List<Book> matchBooks(Transaction tx, int userId, boolean rated)
	{
		List<Book> tmpBooks = new ArrayList<>();
		String query;
		if(rated) query = "MATCH (p:User)-[r:RATED]->(b:Book) WHERE p.user_id=\""+userId+"\" RETURN DISTINCT b.book_id, b.original_title, b.authors LIMIT 10";
		else query = "MATCH (p:User),(b:Book) WHERE NOT (p)-[:RATED]->(b) AND p.user_id=\""+userId+"\" RETURN b.book_id, b.original_title, b.authors LIMIT 10;"; //"MATCH (a:Book) RETURN DISTINCT a.original_title, a.authors LIMIT 10";
		
		StatementResult result = tx.run(query);
		while ( result.hasNext() ) {
			Record tmpRes = result.next();
			Book b = new Book();
			b.setBookId(Integer.parseInt(tmpRes.get(0).asString()));
			b.setTitle(tmpRes.get(1).asString());
			b.setAuthor(tmpRes.get(2).asString());
			tmpBooks.add(b);
		}
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
	
	private static List<Book> browseWishList(Transaction tx, int userId)
	{
		List<Book> tmpBooks = new ArrayList<>();
		String query = "MATCH (p:User)-[r:TO_READ]->(b:Book) WHERE p.user_id=\""+userId+"\" RETURN DISTINCT b.original_title, b.authors LIMIT 10";
		
		StatementResult result = tx.run(query);
		while ( result.hasNext() ) {
			Record tmpRes = result.next();
			Book b = new Book();
			b.setTitle(tmpRes.get(0).asString());
			b.setAuthor(tmpRes.get(1).asString());
			tmpBooks.add(b);
		}
		return tmpBooks;
	}
	
	public List<Book> getWishList(int userId)
	{
		try (Session session = driver.session()) {
			return session.readTransaction( new TransactionWork<List<Book>>() {
				@Override
				public List<Book> execute(Transaction tx) {
					return browseWishList(tx, userId);
				}
			} );
		}
	}
	
	private static void insertWish(Transaction tx, int userId, int bookId) {
		String query = "MATCH (u:User) WHERE u.user_id=\""+userId+"\" MATCH (b:Book) WHERE b.book_id=\""+bookId+"\" CREATE (u)-[:TO_READ]->(b)";
		System.out.println("Query: "+query);
		tx.run(query);
	}
	
	public boolean addWish(int userId, int bookId) {
		try (Session session = driver.session()) {
			return session.writeTransaction( new TransactionWork<Boolean>() {
				@Override
				public Boolean execute(Transaction tx) {
					insertWish(tx, userId, bookId);
					return true;
				}
			} );
		}
	}
	
	//============================query incompleta========================================
	//manca da incrementare attributo count nella relazione TAGGED AS
	private static void insertTag(Transaction tx, int userId, int bookId, String tag) {
		String query = ""; //MATCH (b:Book) WHERE b.book_id="1" MERGE (t:Tag {tag_name: "AAAA"}) MERGE (b)-[ta:TAGGED_AS]->(t)
		System.out.println("Query: "+query);
		tx.run(query);
	}
	
	public boolean addTag(int userId, int bookId, String tag) {
		try (Session session = driver.session()) {
			return session.writeTransaction( new TransactionWork<Boolean>() {
				@Override
				public Boolean execute(Transaction tx) {
					insertTag(tx, userId, bookId, tag);
					return true;
				}
			} );
		}
	}
	
	//=========================================================================
	
	//markRead() dovrà anche eliminare un libro dalla wishlist, se presente?
}
