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
	
	public void login(String id) {	
		if (loggedUser!=null)
			return;
		String name=null;
		String surname=null;
		try {
			entityManager=factory.createEntityManager();
			entityManager.getTransaction().begin();
			User user = entityManager.find(User.class, id);
			entityManager.getTransaction().commit();
			loggedUser=user.getUserId();
			privilege=user.getPrivilege();
			name=user.getName();
			surname=user.getSurname();
		}catch (Exception ex) {
    		ex.printStackTrace();
    		System.out.println("A problem occurred with the login.");
    	}
		finally {
			entityManager.close();
		}
		if(loggedUser!=null) {
			System.out.println("Welcome, "+name+" "+surname);		
		}
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
					System.out.println("Book queried for return");
				}
				else if(loan.getStatus()==0)
				{
					User user = entityManager.find(User.class, loggedUser);
					user.removeLoan(loan.getBook());
					System.out.println("Loan removed");
				}
			}else
				System.out.println("Loan not present");
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
    		ex.printStackTrace();
    		System.out.println("A problem occurred with the book return request.");
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
				System.out.println("User already registered.");
			else {
				entityManager.persist(user);
				System.out.println("User added.");
			}
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
	*/
//book table operations
	
	public void addBook(long isbn, String author, String title, int numCopies) {
		Book book=new Book();
		book.setBookId(isbn);
		book.setAuthor(author);
		book.setTitle(title);
		book.setCopies(numCopies);
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
		int copies=book.getCopies();
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
				if(numCopies==book.getCopies()) {
					entityManager.getTransaction().commit();
					entityManager.close();
					removeBook(bookId);
					return;
				}
				else
					book.setCopies(book.getCopies()-numCopies);
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
			int newNumCopies=book.getCopies()+numCopies;
			book.setCopies(newNumCopies);
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
