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
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();
			User user = entityManager.find(User.class, id);
			entityManager.getTransaction().commit();
			loggedUser = user.getId();
			privilege = user.getPrivilege();
			name = user.getName();
			name = name.concat(" ");
			name = name.concat(user.getSurname());
			result.add(name);
			result.add(Integer.toString(privilege));
		}catch (Exception ex) {
    		ex.printStackTrace();
    		System.out.println("A problem occurred with the login.");
    	}
		finally {
			entityManager.close();
		}
		if(loggedUser != null) {
			return result;	
		}
		return null;
	}
	
	public void logout() {
		loggedUser = null;
	}
	
	public boolean isLogged() {
		if(loggedUser != null)
			return true;
		return false;
	}

//Loan Operations	

	//browses a specific user's loans (reserved to librarians only)
	public ObservableList<Loan> browseUserLoans(String userid) {
		
		User user=null;
		try {
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();

			user = entityManager.find(User.class, userid);

			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the browseUserLoans()");
		}
		finally {
			entityManager.close();
		}
		return user.getLoans();
	}

	public String findUser(String userid) {
		String resultStr=null;
		User user=null;
		try {
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();

			user = entityManager.find(User.class, userid);
			if(user.getPrivilege() == 0) resultStr = user.getName() + " " + user.getSurname();

			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			//ex.printStackTrace();
			System.out.println("A problem occurred with the LibraryManager.findUser().");
		}
		finally {
			entityManager.close();
		}
		return resultStr;
	}

	//browses the user's personal loans
	public List<Loan> browseLoans() {
		User user=null;
		try {
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();

			user = entityManager.find(User.class, loggedUser);

			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the browseloans.");
		}
		finally {
			entityManager.close();
		}
		return user.getLoans();
	}
	
	
	//For user to request a book
	public String borrowBook(long bookId) { //response is returned to the caller in order to display a comprehensive output msg to the user interface
		String response = "";
		try {
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();
			LoanId loanid = new LoanId(loggedUser,bookId);
			Loan loan = entityManager.find(Loan.class, loanid);
			if(loan != null)
				return "You already borrowed this book.";
			else {	
				Book book = entityManager.find(Book.class, bookId);
				User user = entityManager.find(User.class, loggedUser);
				if(Available(book) > 0) {
					user.addLoan(book);
					response ="Succesfully requested the book: " + book.getTitle() +".";
				}
				else
					response = "This book is not available.";
			}
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			return "A problem occurred with the loan request.";
		}
		finally {
			entityManager.close();
			return response;
		}
	}
	
	public void validateBorrow(String userid, long bookId) {
		try {
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();

			LoanId loanid = new LoanId(userid, bookId);
			Loan loan = entityManager.find(Loan.class, loanid);

			if(loan.getStatus() == 0)
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
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();

			LoanId loanid = new LoanId(userid, bookId);
			Loan loan = entityManager.find(Loan.class, loanid);

			if(loan.getStatus() == 2)
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
	//browse user list
	
	public ObservableList<User> browseUsers(int offset) {
		ObservableList<User> users = FXCollections.observableArrayList();
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();

			Query q = entityManager.createNativeQuery("SELECT u.idUser, u.name, u.surname, u.privilege FROM User u ORDER BY u.idUser LIMIT 10 OFFSET ? ", User.class);
			q.setParameter(1, offset);

			users = q.getResultList();

			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the login!");
		}
		finally {
			entityManager.close();
		}
		return users;
	}
	
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

//book table operations
	
	public ObservableList<Book> browseBooks(int offset) {
		ObservableList<Book> books = FXCollections.observableArrayList();
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();

			Query q = entityManager.createNativeQuery("SELECT b.ISBN, b.title, b.author, b.numCopies, b.category FROM Book b ORDER BY b.title LIMIT 10 OFFSET ? ", Book.class);

			q.setParameter(1, offset);

			books = q.getResultList();
			

			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the browse books!");
		}
		finally {
			entityManager.close();
		}
		return books;
	}

	
	public ObservableList<Book> searchBooks(int option, String title, int offset) { //option 0: title, 1:author
		ObservableList<Book> books = FXCollections.observableArrayList();
		title = "%"+title+"%";
		offset = offset*10;

		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();

			Query q;
			if(option == 0) q = entityManager.createNativeQuery("SELECT b.ISBN, b.title, b.author, b.category, b.numCopies FROM Book b WHERE b.title LIKE ? ORDER BY b.title LIMIT 10 OFFSET ? ", Book.class);
			else q = entityManager.createNativeQuery("SELECT b.ISBN, b.title, b.author, b.category, b.numCopies FROM Book b WHERE b.author LIKE ? ORDER BY b.title LIMIT 10 OFFSET ? ", Book.class);

			q.setParameter(1, title);
			q.setParameter(2, offset);

			books = q.getResultList();

			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the search by title!");
		}
		finally {
			entityManager.close();
		}
		return books;
	}

	public int getNumBooks() { //returns the number of books in the catalogue
		int result = 0;

		try {
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();

			Query q = entityManager.createNativeQuery("SELECT COUNT(*) FROM Book;");
			result = ((Number)q.getSingleResult()).intValue();

			entityManager.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return result;
	}

	public String addBook(long isbn, String title, String author, String category, int numCopies) {
		String result = "";
		Book book=new Book();
		book.setId(isbn);
		book.setAuthor(author);
		book.setTitle(title);
		book.setCategory(category);
		book.setNumCopies(numCopies);
		try {
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();

			Book exists = entityManager.find(Book.class, isbn);
			if(exists != null)
				result ="Book already registered.";
			else {
				entityManager.persist(book);
				result = "Book successfully added.";
			}
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			result = "A problem occurred with the book addition.";
		}
		finally {
			entityManager.close();
		}
		return result;
	}

	public int Available(Book book) {
		int copies = book.getNumCopies();
		int available = copies - book.getLoans().size();
		return available;
	}

	public String removeBook(long bookId) {
		String result = ""; //return a comprehensive message to the user interface
		try {
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();

			Book book = entityManager.find(Book.class, bookId);

			if(book != null) {
				result = "Successfully removed book.";
				entityManager.remove(book);
			}
			else result = "Selected book doesn't exist.";

			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			result = "A problem occurred with the LibraryManager.removeBook().";
		}
		finally {
			entityManager.close();
		}
		return result;
	}

	public void removeCopies(long bookId, int numCopies) {
		try {
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();

			Book book = entityManager.find(Book.class, bookId);
			int available = Available(book);

			if(numCopies <= available){
				if(numCopies == book.getNumCopies()) {
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
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();

			Book book = entityManager.find(Book.class, bookId);
			int newNumCopies = book.getNumCopies() + numCopies;
			book.setNumCopies(newNumCopies);

			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred in the LibraryManager.addCopies().");
		}
		finally {
			entityManager.close();
		}
	}
	
	/*	function skeleton --delete before delivery--

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
