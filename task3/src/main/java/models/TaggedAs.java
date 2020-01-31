package main.java.models;

//@RelationshipEntity(type = "TAGGED_AS")
public class TaggedAs
{
//	@StartNode
	Book book;
	
//	@EndNode
	Tag tag;
	
	int count;
}
