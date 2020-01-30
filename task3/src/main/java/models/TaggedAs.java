package main.java;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "TAGGED_AS")
public class TaggedAs
{
	@StartNode
	Book book;
	
	@EndNode
	Tag tag;
	
	int count;
}
