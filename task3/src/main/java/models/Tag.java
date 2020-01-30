package main.java.models;

import org.neo4j.ogm.annotation.*;

import java.util.Set;

@NodeEntity
public class Tag extends Entity
{
	int tag_id;
	String tag_name;
	
	@Relationship(type = "TAGGED_AS", direction = Relationship.INCOMING)
	Set<TaggedAs> taggedAsSet;
	
	//Neo4j-OGM also also requires a public no-args constructor to be able to construct objects from all our annotated entities.
	Tag() { }
}
