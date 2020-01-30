package main.java.models;

import org.neo4j.ogm.annotation.*;

/*
Since every entity requires an id, we’re going to create an Entity superclass.
This is an abstract class, so you’ll see that the nodes do not inherit an Entity label, which is exactly what we want.
If you plan on implementing hashCode and equals make sure it does not make use of the native id.
 */

abstract class Entity {
	
	@Id @GeneratedValue
	private Long id;
	
	public Long getId() {
		return id;
	}
}
