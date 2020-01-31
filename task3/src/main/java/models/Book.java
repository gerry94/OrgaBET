package main.java.models;

import java.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//@NodeEntity
@Getter
@Setter
@ToString
public class Book {
	//@Property(name = "book_id")
	private int bookId;
	
	//@Property(name = "original_title") //specify when attribute is different from neo4j attribute name
	private String title;
	
	//@Property(name = "authors")
	private String author;
	
	//@Relationship(type = "TAGGED_AS") //default is outgoing
	Set<TaggedAs> tags;
	
	//@Relationship(type = "RATED", direction = Relationship.INCOMING)
	Set<Rated> ratings;
	
	//@Relationship(type = "TO_READ", direction = Relationship.INCOMING)
	Set<User> wantsToRead;
	
	//Neo4j-OGM also also requires a public no-args constructor to be able to construct objects from all our annotated entities.
	public Book() { }
	/*
	public void setTitle(String t) {
		this.title = t;
	}
	
	public void setAuthor(String a) {
		this.author = a;
	}*/
}