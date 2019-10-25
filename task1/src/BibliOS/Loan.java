package BibliOS;
@Entity
public class Loan {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	private Book book;
	
	@Column(columnDefinition = "int default 0")
	private int status;
	
	public Loan() {
	}
	
	public Loan(User user, Book book) {
		this.user=user;
		this.book=book;
	}
	public User getUser() {
		return user;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setUser(User user) {
		this.user=user;
	}
	
	public void setBook(Book book) {
		this.book=book;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status=status;
	}
}
