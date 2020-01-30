package main.java;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "RATED")
public class Rated
{
	@StartNode
	UserNode user;
	
	@EndNode
	Book book;
	
	int rating;
}
