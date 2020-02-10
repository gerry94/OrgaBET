package main.java;

import org.neo4j.driver.v1.*;
import main.java.models.Book;
import java.util.*;

public class GraphManager implements AutoCloseable
{
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
		if(rated) query = "MATCH (p:User {user_id:\""+userId+"\"})-[r:RATED]->(b:Book) RETURN b.book_id, b.original_title, b.authors, r.rating LIMIT 10";
		else query = "MATCH (p:User {user_id:\""+userId+"\"}),(b:Book) WHERE NOT (p)-[:RATED]->(b) RETURN b.book_id, b.original_title, b.authors, b.average_rating LIMIT 10;";
		//"MATCH ()-[r:RATED]->(b:Book) WHERE NOT (:User {user_id:\""+userId+"\"})-[:RATED]->(b) RETURN b.book_id, b.original_title, b.authors, AVG(r.rating) LIMIT 10";
		
		System.out.println("Query: "+query);
		StatementResult result = tx.run(query);
		while ( result.hasNext() ) {
			Record tmpRes = result.next();
			Book b = new Book();
			b.setBookId(Integer.parseInt(tmpRes.get(0).asString()));
			b.setTitle(tmpRes.get(1).asString());
			b.setAuthor(tmpRes.get(2).asString());
			Double avg;
			
			try {
				avg = tmpRes.get(3).asDouble();
			} catch(Exception e) {
				avg = Double.parseDouble(tmpRes.get(3).asString());
			}
			
			b.setAvgRating( (Math.floor(avg*10) /10) );
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
		String query = "MATCH (p:User)-[r:TO_READ]->(b:Book) WHERE p.user_id=\""+userId+"\" RETURN DISTINCT b.book_id, b.original_title, b.authors LIMIT 10";
		
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
	
	public void addWish(int userId, int bookId) {
		try (Session session = driver.session()) {
			session.writeTransaction(new TransactionWork<Boolean>()
			{
				@Override
				public Boolean execute(Transaction tx)
				{
					insertWish(tx, userId, bookId);
					return true;
				}
			});
		}
	}
	
	private static void deleteWish(Transaction tx, int userId, int bookId) {
		String query = "MATCH (u:User {user_id:\""+userId+"\"})-[r:TO_READ]->(b:Book {book_id:\""+bookId+"\"}) DELETE r";
		System.out.println("Query: "+query);
		tx.run(query);
	}
	
	public void removeWish(int userId, int bookId) {
		try (Session session = driver.session()) {
			session.writeTransaction(new TransactionWork<Boolean>()
			{
				@Override
				public Boolean execute(Transaction tx)
				{
					deleteWish(tx, userId, bookId);
					return true;
				}
			});
		}
	}

	private static void insertTag(Transaction tx, int bookId, String tag) {
		String query = "MATCH (b:Book {book_id:\""+bookId+"\"}) MERGE (t:Tag {tag_name:\""+tag+"\"}) MERGE (b)-[r:TAGGED_AS]->(t) ON CREATE SET r.count=1 ON MATCH SET r.count=r.count+1";
		System.out.println("Query: "+query);
		tx.run(query);
	}
	
	public void addTag(int bookId, String tag) {
		try (Session session = driver.session()) {
			session.writeTransaction(new TransactionWork<Boolean>()
			{
				@Override
				public Boolean execute(Transaction tx)
				{
					insertTag(tx, bookId, tag);
					return true;
				}
			});
		}
	}

	private static void insertRating(Transaction tx, int bookId, int userId, int rating) {
		String query = "MATCH (u:User {user_id:\""+userId+"\"}),(b:Book {book_id:\""+bookId+"\"}) MERGE (u)-[r:RATED]->(b) SET r.rating="+rating;
		System.out.println("Query: "+query);
		tx.run(query);
		
		//computes the new avg rating
		String query2 = "MATCH ()-[r:RATED]->(b:Book {book_id:\""+bookId+"\"}) WITH b, AVG(r.rating) AS new_rating SET b.average_rating=new_rating RETURN b";
		tx.run(query2);
		
		//removes book from WishList, in case it was there
		tx.run("MATCH (u:User {user_id:\""+userId+"\"})-[r:TO_READ]->(b:Book {book_id:\""+bookId+"\"}) DELETE r");
	}
	
	public void addRating(int bookId, int userId, int rating) {
		try (Session session = driver.session()) {
			session.writeTransaction(new TransactionWork<Boolean>()
			{
				@Override
				public Boolean execute(Transaction tx)
				{
					insertRating(tx, bookId, userId, rating);
					return true;
				}
			});
		}
	}
	
	private static void deleteBook(Transaction tx, int bookId) {
		String query = "MATCH (b:Book {book_id:\""+bookId+"\"}) DETACH DELETE b";
		System.out.println("Query: "+query);
		tx.run(query);
	}
	
	public void removeBook(int bookId) {
		try (Session session = driver.session()) {
			session.writeTransaction(new TransactionWork<Boolean>()
			{
				@Override
				public Boolean execute(Transaction tx)
				{
					deleteBook(tx, bookId);
					return true;
				}
			});
		}
	}
}