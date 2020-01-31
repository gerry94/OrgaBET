package main.java.models;

//@RelationshipEntity(type = "RATED")
public class Rated
{
//	@StartNode
	UserNode user;
	
//	@EndNode
	Book book;
	
	int rating;
}
