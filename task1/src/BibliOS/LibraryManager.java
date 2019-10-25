package BibliOS;
import java.util.List;
import javax.persistence.*;

public class LibraryManager {
	private static EntityManager entityManager;
	private static EntityManagerFactory factory;
	private User loggedUser=null;
	
	public void setup()	{
		factory = Persistence.createEntityManagerFactory("lsdb");
	}
	
	public void exit() {
		factory.close();
	}
	
	public int login(String id) {	
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			loggedUser = entityManager.find(User.class, id);
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
    		ex.printStackTrace();
    		System.out.println("A problem occurred with the login.");
    	}
		finally {
			entityManager.close();
		}
		if(loggedUser!=null) {
			System.out.println("Welcome, "+loggedUser.getName()+" "+loggedUser.getSurname());
			return loggedUser.getPrivilege();
		}
		return -1;
	}
	
	public void logout() {
		loggedUser=null;
	}
	
	public boolean isLogged() {
		if(loggedUser!=null)
			return true;
		return false;
	}
	
	public void borrowBook(long bookId)	{
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			Book book = entityManager.find(Book.class, bookId);
			loggedUser.addLoan(book);
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the borrow request.");
		}
		finally {
			entityManager.close();
		}
	}
	
	public void returnBook(long bookId) {
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			Book book = entityManager.find(Book.class, bookId);
			loggedUser.removeLoan(book);
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
    		ex.printStackTrace();
    		System.out.println("A problem occurred with the book return request.");
    	}
		finally {
			entityManager.close();
		}
			
	}
	
	public void addUser(String id,String name, String surname) {
		User user=new User();
		user.setUserId(id);
		user.setName(name);
		user.setSurname(surname);
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(user);
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			//controllare che succede se provo ad aggiungere duplicato.
			ex.printStackTrace();
			System.out.println("A problem occurred with the user registration.");
		}
		finally {
			entityManager.close();
		}
	}
	//da implementare
	public void findBook() {
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
	//da implementare
	public void browseBook() {
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
	
	public void addBook(long isbn, String author, String title, int numCopies) {
		Book book=new Book();
		book.setBookId(isbn);
		book.setAuthor(author);
		book.setTitle(title);
		book.setCopies(numCopies);
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(book);
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred while trying to add a book.");
		}
		finally {
			entityManager.close();
		}
	}
	//da implementare
	public void getAvailableCopies(long bookId) {
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
	
	public void removeBook(long bookId) {
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			Book book = entityManager.find(Book.class, bookId);
			entityManager.remove(book);
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the login!");
		}
		finally {
			entityManager.close();
		}
	}
	//da implementare, deve decidere se rimuovere il libro o rimuovere copie e controllare se si puo' fare.
	public void removeCopies(long bookId, int numCopies) {
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
	
	public void addCopies(long bookId, int numCopies) {
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			Book book = entityManager.find(Book.class, bookId);
			int newNumCopies=book.getCopies()+numCopies;
			book.setCopies(newNumCopies);
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A problem occurred with the login!");
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
