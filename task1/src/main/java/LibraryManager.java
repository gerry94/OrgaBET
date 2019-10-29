import javafx.collections.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

public class LibraryManager {
	private static EntityManager entityManager;
	private static EntityManagerFactory factory;
	private String loggedUser=null;
	int privilege;
	
	public void setup()	{
		factory = Persistence.createEntityManagerFactory("bibliosDB");
	}
	
	public void exit() {
		factory.close();
	}
	
	public List<String> login(String id) {	
		if (loggedUser!=null)
			return null;
		String name = null;
		List<String> result = new ArrayList<String>();
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			User user = entityManager.find(User.class, id);
			entityManager.getTransaction().commit();
			loggedUser=user.getUserId();
			privilege=user.getPrivilege();
			name=user.getName();
			name=name.concat(" ");
			name=name.concat(user.getSurname());
			result.add(name);
			result.add(Integer.toString(privilege));
		}catch (Exception ex) {
    		ex.printStackTrace();
    		System.out.println("A problem occurred with the login.");
    	}
		finally {
			entityManager.close();
		}
		if(loggedUser!=null) {
			return result;	
		}
		return null;
	}
	
	public void logout() {
		loggedUser=null;
	}
	
	public boolean isLogged() {
		if(loggedUser!=null)
			return true;
		return false;
	}
	
//Loan Operations	

	//browses a specific user's loans (reserved to librarians only)
	public List<Loan> browseUserLoans(String userid) {
		User user=null;
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			user=entityManager.find(User.class, userid);
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the login!");
		}
		finally {
			entityManager.close();
		}
		return user.getLoans();
	}

	//browses the user's personal loans
	public List<Loan> browseLoans() {
		User user=null;
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			user=entityManager.find(User.class, loggedUser);
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the login!");
		}
		finally {
			entityManager.close();
		}
		return user.getLoans();
	}
	
	//For user to request a book
	public void borrowBook(long bookId)	{
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			LoanId loanid= new LoanId(loggedUser,bookId);
			Loan loan=entityManager.find(Loan.class, loanid);
			if(loan!=null)
				System.out.println("You already borrowed this book.");
			else {	
				Book book = entityManager.find(Book.class, bookId);
				User user = entityManager.find(User.class, loggedUser);
				if(Available(book)>0) {
					user.addLoan(book);
					System.out.println("Book borrowed.");
				}
				else
					System.out.println("This book is not available.");
			}
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the loan request.");
		}
		finally {
			entityManager.close();
		}
	}
	
	public void validateBorrow(String userid, long bookId) {
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			LoanId loanid= new LoanId(loggedUser,bookId);
			Loan loan=entityManager.find(Loan.class, loanid);
			if(loan.getStatus()==0)
				loan.setStatus(1);
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the loan validation!");
		}
		finally {
			entityManager.close();
		}
	}
	
	//for user to request a return
	public void returnBook(long bookId) {
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			LoanId loanid= new LoanId(loggedUser,bookId);
			Loan loan=entityManager.find(Loan.class, loanid);
			if(loan!=null) {
				if(loan.getStatus()==1) {
					loan.setStatus(2);
				}
				else if(loan.getStatus()==0)
				{
					User user = entityManager.find(User.class, loggedUser);
					user.removeLoan(loan.getBook());
				}
			}
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
    		ex.printStackTrace();
    		System.out.println("A problem occurred with the book return request.");
    	}
		finally {
			entityManager.close();
		}
			
	}
	
	public void validateReturn(String userid, long bookId) {
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			LoanId loanid= new LoanId(loggedUser,bookId);
			Loan loan=entityManager.find(Loan.class, loanid);
			if(loan.getStatus()==2)
				loan.getUser().removeLoan(loan.getBook());
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the loan validation!");
		}
		finally {
			entityManager.close();
		}
	}

//User table operations
	
	public void addUser(String id,String name, String surname) {
		User user=new User();
		user.setUserId(id);
		user.setName(name);
		user.setSurname(surname);
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			User exists=entityManager.find(User.class, id);
			if(exists!=null)
				System.out.println("Book already registered.");
			else
				entityManager.persist(user);
			entityManager.getTransaction().commit();

		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the user registration.");
		}
		finally {
			entityManager.close();
		}
	}
	//da implementare
	/*
	public Book findBook() {
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the login!");
		}
		finally {
			entityManager.close();
			return book;
		}
	}
	*/	


//book table operations
	
	public ObservableList<Book> browseBooks(int offset) {
		ObservableList<Book> books = FXCollections.observableArrayList();
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();

			Query q = entityManager.createNativeQuery("SELECT b.ISBN, b.title, b.author, b.category, b.numCopies FROM Book b ORDER BY b.ISBN LIMIT 10 OFFSET ? ", Book.class);
			q.setParameter(1, offset);

			List<Book> tmpBook = q.getResultList();
			for(Book b: tmpBook)
				books.add(b);

			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the login!");
		}
		finally {
			entityManager.close();
		}
		return books;
	}
	
	public void addBook(long isbn, String author, String title, String category, int numCopies) {
		Book book=new Book();
		book.setId(isbn);
		book.setAuthor(author);
		book.setTitle(title);
		book.setCategory(category);
		book.setNumCopies(numCopies);
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			Book exists=entityManager.find(Book.class, isbn);
			if(exists!=null)
				System.out.println("Book already registered.");
			else {
				entityManager.persist(book);
				System.out.println("Book added.");
			}
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the book addition.");
		}
		finally {
			entityManager.close();
		}
	}

	public int Available(Book book) {
		int copies=book.getNumCopies();
		int available= copies - book.getLoans().size();
		return available;
	}

	public void removeBook(long bookId) {
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			Book book = entityManager.find(Book.class, bookId);
			entityManager.remove(book);
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred while removing the book");
		}
		finally {
			entityManager.close();
		}
	}

	public void removeCopies(long bookId, int numCopies) {
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			Book book = entityManager.find(Book.class, bookId);
			int available= Available(book);
			if(numCopies<=available){
				if(numCopies==book.getNumCopies()) {
					entityManager.getTransaction().commit();
					entityManager.close();
					removeBook(bookId);
					return;
				}
				else
					book.setNumCopies(book.getNumCopies()-numCopies);
			}
			else
				System.out.println("There are not enough copies to be removed");
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the copyremoval.");
		}
		finally {
			entityManager.close();
		}
	}
	
	public void addCopies(long bookId, int numCopies) {
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			Book book = entityManager.find(Book.class, bookId);
			int newNumCopies=book.getNumCopies()+numCopies;
			book.setNumCopies(newNumCopies);
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred while adding copies");
		}
		finally {
			entityManager.close();
		}
	}
	
	/*
	public void function() {
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the login!");
		}
		finally {
			entityManager.close();
		}
	}
	*/

}
