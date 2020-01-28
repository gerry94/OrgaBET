import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;

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
	
	//************************query di prova**********************************
	private static List<String> matchBooks(Transaction tx )
	{
		List<String> names = new ArrayList<>();
		StatementResult result = tx.run( "MATCH (a:Book) RETURN DISTINCT a.title LIMIT 15" );
		while ( result.hasNext() )
		{
			names.add( result.next().get( 0 ).asString() );
		}
		System.out.println(names);
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
	//****************************************************************************
}
