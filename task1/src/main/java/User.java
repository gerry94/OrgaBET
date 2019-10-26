import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class User implements Serializable{

	@Id
	//The id variable is linked to the column of the table with the name userId 
	//therefore we must use the following annotation.
	@Column(name="idUser")
	private String id;
	
	private String name;
	private String surname;
	
	@Column(columnDefinition = "tinyint(4) default 0")
	private int privilege;
	//private String document;
	
	public User() {
	}
	
	public String getUserId() {
		return id;
	}
	
	public void setUserId(String id) {
		this.id=id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname=surname;
	}
	
	public int getPrivilege() {
		return privilege;
	}
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Loan> loans = new ArrayList<>();
	
	//helper method to sync a loan addition
	public void addLoan(Book book) {
		Loan loan = new Loan(this, book);
		loans.add(loan);
		book.getLoans().add(loan);
	}
	
	//helper method to sync a loan removal
	public void removeLoan(Book book) {
		Loan loan = new Loan(this, book);
		loans.remove(loan);
		book.getLoans().remove(loan);
		loan.setUser( null );
		loan.setBook( null );
	}
}
