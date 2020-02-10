package main.java.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

//@NodeEntity
@Getter
@Setter
public class Tag extends Entity
{
	int tag_id;
	String tag_name;
	
//	@Relationship(type = "TAGGED_AS", direction = Relationship.INCOMING)
	Set<TaggedAs> taggedAsSet;
	
	//Neo4j-OGM also also requires a public no-args constructor to be able to construct objects from all our annotated entities.
	public Tag() { }

	public void setTag_id(int parseInt) {
		this.tag_id = parseInt;
	}

	public void setTag_name(String asString) {
		this.tag_name = asString;
	}
}
