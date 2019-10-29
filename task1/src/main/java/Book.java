import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Book{

	@Id
	//The id variable is linked to the column of the table with the name bookId 
	//therefore we must use the following annotation.
	@Column(name = "ISBN")
	private long id;

	private String title;
	private String author;
	private String category;
	private int numCopies;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Loan> loans= new ArrayList<>();
	
	public Book() {
	}
	    
	public long getId() {
		return id;
	}
	
	public void setBookId(long id) {
		this.id=id;
	}
	 
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title=title;
	}
	    
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author=author;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category=category;
	}
	
	public int getNumCopies() {
		return numCopies;
	}
	
	public void setCopies(int num) {
		this.numCopies=num;
	}
	
	//return lista dei prestiti attivi sul libro in questione
	public List<Loan> getLoans()
	{
			return this.loans;
	}
	
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Book that = (Book) o;
		return Objects.equals( title, that.title ) &&
				Objects.equals( author, that.author )&&
				Objects.equals( category, that.category )&&
				Objects.equals( numCopies, that.numCopies );
	}
	
}

