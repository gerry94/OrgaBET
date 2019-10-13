import java.util.*;

public class GestioneBiblioteca {
	
	public static final String REDC="\033[0;31m", GREENC="\033[0;32m", ENDC="\033[0m";
	
	public static void printMsg(boolean privilege)
	{
		System.out.println("!help --> view available commands.");
		
		if(privilege)
		{
		      System.out.println("!add --> add a new book in the library's Catalogue.");
		      System.out.println("!remove --> remove a book from the library's Catalogue.");
		      System.out.println("!create --> register a new user in BibliOS.");
		}
		else
		{
			System.out.println("!borrow --> request to borrow a specific book.");
			System.out.println("!return --> return to the library a previously borrowed book.");
		}
		System.out.println("!list --> visualize the catalogue for this library.");
		System.out.println("!logout --> logout from the program.");
		System.out.println("!exit --> logout and close the program.");
		System.out.println("");
	}
	
	public static String login(DBManager dbm, Scanner scan)
	{		
		System.out.print("\033[H\033[2J");  //"clear" the screen
	    System.out.flush(); 
	    
		System.out.println("====================== Welcome in BibliOS ======================");
		String user_name = "", idUser = "";
		
		do {
			System.out.println("Please, insert your access code found in your library card:");
			System.out.print(">");
			idUser = scan.next();
			
			if(!idUser.matches("[a-zA-Z0-9]+") || idUser.contentEquals("")) //checks that input is alphanumeric only
			{
				System.out.println(REDC + "Invalid input: please try again or contact a librarian for help." + ENDC);
				continue;
			}
			else
			{
				user_name = dbm.login(idUser);
				if(user_name.contentEquals(""))
					System.out.println("Incorrect credentials: please try again or contact a librarian for help.");
			}
		} while(user_name.equals(""));
		
		System.out.println("Welcome " + user_name +". The following commands are available: ");
		System.out.println("");
		return idUser;
		
	}
	
	public static void main(String[] args) {
		DBManager dbm = new DBManager(3306, "feanor", "password", "lsdb");
		String command, cin;
		dbm.start();
		
		Scanner scan = new Scanner(System.in).useDelimiter("\n");
		
		String idUser=login(dbm,scan);
		boolean privilege = dbm.check_privilege(idUser);
		printMsg(privilege);
		
		while(true)
		{
			System.out.print(">");
			command = scan.next();
			if(command.matches("[a-zA-Z!]+")) //only a-z character and ! are allowed
			{
				switch(command)
				{
					case "!help":
						printMsg(privilege);
						break;

					case "!list":
						System.out.println("Books in the Catalogue:\n");
						System.out.println("========================================================================================================================");
						System.out.printf("%-15s %-60s %-25s %-15s %n", "Book Code", "Title", "Author", "Availability");
						System.out.println("========================================================================================================================");
						
						List<String> elenco = dbm.list(privilege);
						String avail;
						
						for(int i=0; i<elenco.size(); i+=4)
						{
							if(elenco.get(i+3) == "(Not Available)")
								avail = REDC + elenco.get(i+3) + ENDC;
							else avail = GREENC + elenco.get(i+3) + ENDC;
							
							System.out.printf("%-15s %-60s %-25s %-15s %n", elenco.get(i), elenco.get(i+1), elenco.get(i+2), avail);
						}
						System.out.println("========================================================================================================================");
						System.out.println("");
						break;
					
					case "!borrow":
						System.out.println("Please insert the code of the book you wish to borrow:");
						System.out.print(">");
						
						String idBook = scan.next();
						
						int code = dbm.availability(idBook);
						
						if(code == 0)
							System.out.println("Book not available. Please check availability by using the !list command.");
						else if(code >=1)
						{
							//1) stampo titolo e autore e chiedo conferma del prestito
							List<String> bookInfo = dbm.getBookInfo(idBook);
							System.out.println("You chose to borrow: " + bookInfo.get(0) + ", written by " + bookInfo.get(1));
							System.out.println("Confirm this choice? (y/n): ");
							System.out.print(">");
							
							cin = scan.next();
							System.out.println("");
							
							if(cin.matches("[yY]{1}")) //only lowercase or uppercase y allowed, 1 char long
							{
								if(dbm.borrow(idBook, idUser))
									System.out.println("Borrowed.");
								else System.out.println("ERROR: Unable to borrow, please contact a librarian for help.");
							}
							else System.out.println("Operation aborted.");
						}
						else System.out.println("ERROR: Book not found. Check the Book Code.");
							
						break;
					
					case "!return":
						System.out.println("Please insert the code of the book you wish to return:");
						System.out.print(">");
						String bookId= scan.next();
						
						if(!bookId.matches("[a-zA-Z0-9]+"))
							System.out.println("ERROR: Book not found. Check the Book Code.");
						else if(!dbm.checkIfBorrowed(bookId, idUser)) //(availCode >= 1)
							System.out.println("ERROR: You haven't borrowed this book. Check the Book Code.");
						else
						{
							List<String> bookinf = dbm.getBookInfo(bookId);
							System.out.println("You are returning: " + bookinf.get(0) + ", written by " + bookinf.get(1));
							System.out.println("Confirm this choice? (y/n): ");
							System.out.print(">");
							cin = scan.next();
							
							System.out.println("");
							if(cin.matches("[yY]{1}")) //only lowercase or uppercase y allowed, 1 char long
							{
								if(dbm.bookReturn(bookId, idUser))
									System.out.println("Returned.");
								else System.out.println(REDC+"You have not borrowed this book, please check the bookcode."+ENDC);
							}
							else System.out.println("Operation aborted.");
						}
						break;
					
					case "!logout":
						idUser=login(dbm,scan);
						privilege = dbm.check_privilege(idUser);
						printMsg(privilege);
						break;
					case "!exit":
						System.out.println("Closing program...");
						dbm.stop();
						scan.close();
						System.exit(0);
						break;
					
					case "!add":
						if(privilege)
						{
							System.out.println("Insert book ID: ");
							System.out.print(">");
							idBook = scan.next();
							
							String Title="", Author="";
							
							if(!dbm.checkBookExistance(idBook))
							{
								System.out.println("Insert Title: ");
								System.out.print(">");
								Title = scan.next();
								
								System.out.println("Insert Author: ");
								System.out.print(">");
								Author = scan.next();
							}
							else System.out.println("Book is already registered in the Catalogue.");
							
							System.out.println("How many copies are to be inserted? ");
							System.out.print(">");
							int n = 0;
							
							try {
							  n = scan.nextInt();
							} catch (InputMismatchException e) { System.out.println(REDC + "ERROR: value is not an integer" + ENDC); break;}
							
							if(n <= 0)
							{
								System.out.println(REDC + "ERROR: Invalid input." + ENDC);
								break;
							}
							else
							{
								dbm.addBook(idBook, Title, Author, n);
								System.out.println(GREENC + "Operation completed correctly." + ENDC);
							}
							
							break;
						}
					case "!remove":
						if(privilege)
						{
							System.out.println("Insert the ID of the book to be removed: ");
							System.out.print(">");
							idBook = scan.next();
							
							if(!idBook.matches("[a-zA-Z0-9]+"))
							{
								System.out.println(REDC + "Invalid book ID." + ENDC);
								break;
							}
							else if(!dbm.checkBookExistance(idBook))
							{
								System.out.println("Book ID doesn't match any in the Catalogue.");
								break;
							}
							
							System.out.println("How many copies are to be removed? ");
							System.out.print(">");
							int n = 0;
							
							try {
								n = scan.nextInt();
							} catch(InputMismatchException e) { System.out.println(REDC + "ERROR: value is not an integer" + ENDC); break;}
							
							if(n <= 0)
							{
								System.out.println(REDC + "ERROR: Invalid input." + ENDC);
								break;
							}
							
							List<String> bookInfo = dbm.getBookInfo(idBook);
							System.out.println("You are removing " + n + " copies of " + bookInfo.get(0) + ", written by " + bookInfo.get(1));
							System.out.println("Confirm this choice? (y/n): ");
							System.out.print(">");
							
							cin = scan.next();
							System.out.println("");
							
							if(cin.matches("[yY]{1}")) //only lowercase or uppercase y allowed, 1 char long
								dbm.removeBook(idBook, n);
							else System.out.println("Operation aborted.");
							break;
						}
					case "!create":
						if(privilege)
						{
							System.out.println("Insert the user id:");
							System.out.print(">");
							String newid = scan.next();
							if(!newid.matches("[a-zA-Z0-9]+"))
							{
								System.out.println(REDC + "Invalid User ID format." + ENDC);
								break;
							}
							
							System.out.println("Insert the user's name:");
							System.out.print(">");
							String newname = scan.next();
							if(!newname.matches("[a-zA-Z]+"))
							{
								System.out.println(REDC + "Invalid Name format." + ENDC);
								break;
							}
							
							System.out.println("Insert the user's surname:");
							System.out.print(">");
							String newsurname = scan.next();
							if(!newsurname.matches("[a-zA-Z]+"))
							{
								System.out.println(REDC + "Invalid surname format." + ENDC);
								break;
							}
							
							dbm.createuser(newid, newname, newsurname);
							break;
						}
					default:
						System.out.println(REDC + "Invalid command." + ENDC + "Type '!help' to view available commands.");
						break;
			} //switch
		}
		else System.out.println(REDC + "Invalid command." + ENDC + "Type '!help' to view available commands.");
		}
			
	} //while
}
