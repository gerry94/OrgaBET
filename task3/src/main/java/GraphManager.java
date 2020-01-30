package main.java;

//import org.neo4j.driver.v1.*;

import main.java.models.Book;
import org.neo4j.ogm.session.*;

public class GraphManager //implements AutoCloseable
{
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
	}
	/*private final Driver driver;
	public GraphManager(String uri, String user, String password ) {
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
	}
	
	@Override
	public void close() {
		driver.close();
	}
	
	//************************query di prova**********************************
	private static List<String> matchBooks(Transaction tx )
	{
		List<String> names = new ArrayList<>();
		StatementResult result = tx.run( "MATCH (a:Book) RETURN DISTINCT a.title LIMIT 15" );
		while ( result.hasNext() ) {
			names.add(result.next().get(0).asString());
		}
		return names;
	}
	
	public List<String> getBooks()
	{
		try ( Session session = driver.session() )
		{
			return session.readTransaction( new TransactionWork<List<String>>()
			{
				@Override
				public List<String> execute( Transaction tx )
				{
					return matchBooks( tx );
				}
			} );
		}
	}
	//***************************************************************************
	 */
	
}
