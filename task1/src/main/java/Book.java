import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Book implements Serializable{

	@Id
	//The id variable is linked to the column of the table with the name bookId 
	//therefore we must use the following annotation.
	@Column(name = "ISBN")
	private long id;

	private String title;
	private String author;
	private int numCopies;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Loan> loans= new ArrayList<>();
	
	public Book() {
	}
	    
	public long getBookId() {
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
	
	public int getCopies() {
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
	
}

