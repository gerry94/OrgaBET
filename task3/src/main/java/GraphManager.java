package main.java;

import main.java.models.Tag;
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
		else query = "MATCH ()-[r:RATED]->(b:Book) WHERE NOT (:User {user_id:\""+userId+"\"})-[:RATED]->(b) RETURN b.book_id, b.original_title, b.authors, AVG(r.rating) LIMIT 10";
		
		// ""MATCH ()-[r:RATED]->(b:Book) RETURN b.book_id, b.original_title, b.authors, AVG(r.rating) LIMIT 10";
		//"MATCH (p:User {user_id:\""+userId+"\"}),(b:Book) WHERE NOT (p)-[:RATED]->(b) MATCH ()-[r:RATED]->(b) RETURN b.book_id, b.original_title, b.authors, AVG(r.rating) LIMIT 10;";
		
		System.out.println("Query: "+query);
		StatementResult result = tx.run(query);
		while ( result.hasNext() ) {
			Record tmpRes = result.next();
			Book b = new Book();
			b.setBookId(Integer.parseInt(tmpRes.get(0).asString()));
			b.setTitle(tmpRes.get(1).asString());
			b.setAuthor(tmpRes.get(2).asString());
			b.setAvgRating(tmpRes.get(3).asDouble());
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
	
	private static void deleteWish(Transaction tx, int userId, int bookId) {
		String query = "MATCH (u:User {user_id:\""+userId+"\"})-[r:TO_READ]->(b:Book {book_id:\""+bookId+"\"}) DELETE r";
		System.out.println("Query: "+query);
		tx.run(query);
	}
	
	public boolean removeWish(int userId, int bookId) {
		try (Session session = driver.session()) {
			return session.writeTransaction( new TransactionWork<Boolean>() {
				@Override
				public Boolean execute(Transaction tx) {
					deleteWish(tx, userId, bookId);
					return true;
				}
			} );
		}
	}

	private static void insertTag(Transaction tx, int bookId, String tag) {
		String query = "MATCH (b:Book {book_id:\""+bookId+"\"}) MERGE (t:Tag {tag_name:\""+tag+"\"}) MERGE (b)-[r:TAGGED_AS]->(t) ON CREATE SET r.count=1 ON MATCH SET r.count=r.count+1";
		System.out.println("Query: "+query);
		tx.run(query);
	}
	
	public boolean addTag(int bookId, String tag) {
		try (Session session = driver.session()) {
			return session.writeTransaction( new TransactionWork<Boolean>() {
				@Override
				public Boolean execute(Transaction tx) {
					insertTag(tx, bookId, tag);
					return true;
				}
			} );
		}
	}

	private static void insertRating(Transaction tx, int bookId, int userId, int rating) {
		String query = "MATCH (u:User {user_id:\""+userId+"\"}),(b:Book {book_id:\""+bookId+"\"}) MERGE (u)-[r:RATED]->(b) SET r.rating="+rating;
		System.out.println("Query: "+query);
		tx.run(query);
		
		//removes book from WishList, in case it was there
		tx.run("MATCH (u:User {user_id:\""+userId+"\"})-[r:TO_READ]->(b:Book {book_id:\""+bookId+"\"}) DELETE r");
	}
	
	public boolean addRating(int bookId, int userId, int rating) {
		try (Session session = driver.session()) {
			return session.writeTransaction( new TransactionWork<Boolean>() {
				@Override
				public Boolean execute(Transaction tx) {
					insertRating(tx, bookId, userId, rating);
					return true;
				}
			} );
		}
	}


	private static Integer countTotWish(Transaction tx, int bookId) {
		String query = "MATCH (:User)-[r:TO_READ]->(b:Book) WHERE b.book_id = '" + bookId + "' RETURN count(r) as totWish";
		System.out.println("Query: "+query);
		StatementResult result = tx.run(query);
		Record tmpRes = result.next();
		Integer totWish = Integer.parseInt(tmpRes.get(0).toString());

		return totWish;
	}

	public Integer getTotWish(int bookId) {
		try (Session session = driver.session()) {
			return session.writeTransaction( new TransactionWork<Integer>() {
				@Override
				public Integer execute(Transaction tx) {
					return countTotWish(tx, bookId);
				}
			} );
		}
	}

	private static List<Tag> matchTags(Transaction tx, int bookId) {
		List<Tag> tag_list = new ArrayList<>();
		String query = "MATCH (b:Book)-[r:TAGGED_AS]->(t:Tag) WHERE b.book_id = '" + bookId + "' RETURN t.tag_id, t.tag_name";
		System.out.println("Query: "+query);

		StatementResult result = tx.run(query);
		while(result.hasNext()) {
			Record tmpRes = result.next();
			Tag t = new Tag();
			t.setTag_id(Integer.parseInt(tmpRes.get(0).asString()));
			t.setTag_name(tmpRes.get(1).asString());
			tag_list.add(t);
		}

		return tag_list;
	}

	public List<Tag> getTags(int bookId) {
		try (Session session = driver.session()) {
			return session.writeTransaction( new TransactionWork<List<Tag>>() {
				@Override
				public List<Tag> execute(Transaction tx) {
					return matchTags(tx, bookId);
				}
			} );
		}
	}

	private static Integer countTotTag(Transaction tx, int bookId) {
		String query = "MATCH (b:Book)-[r:TAGGED_AS]->(t:Tag) WHERE b.book_id = '" + bookId + "' RETURN count(r) as totTags";
		System.out.println("Query: "+query);
		StatementResult result2 = tx.run(query);
		Record tmpRes = result2.next();
		Integer totTags = Integer.parseInt(tmpRes.get(0).toString());

		return totTags;
	}

	public Integer getTotTag(int bookId) {
		try (Session session = driver.session()) {
			return session.writeTransaction( new TransactionWork<Integer>() {
				@Override
				public Integer execute(Transaction tx) {
					return countTotTag(tx, bookId);
				}
			} );
		}
	}

}