package main.java;

import java.util.*;
import org.neo4j.ogm.annotation.*;

@NodeEntity(label = "User")
public class UserNode extends Entity
{
	int user_id;
	
	@Relationship(type = "RATED")
	Set<Rated> ratedBooks;
	
	@Relationship(type = "TO_READ")
	Set<Book> toReadBooks;
	
	//Neo4j-OGM also also requires a public no-args constructor to be able to construct objects from all our annotated entities.
	UserNode() { }
}
