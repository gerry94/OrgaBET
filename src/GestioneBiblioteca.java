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
		System.out.println("!list --> visualize the catalog for this library.");
		System.out.println("!exit --> logout and close the program.");
		System.out.println("");
	}
	
	public static void main(String[] args) {
		//cleans the terminal prompt
		System.out.print("\033[H\033[2J");  
	    System.out.flush();
	    
		DBManager dbm = new DBManager(3306, "feanor", "password", "lsdb");
		String command;
		
		dbm.start();
		
		Scanner scan = new Scanner(System.in).useDelimiter("\n");
		
		System.out.println("====================== Welcome in BibliOS ======================");
		String user_name = "", idUser = "";
		
		do {
			System.out.println("Please, insert your access code found in your library card:");
			System.out.print(">");
			idUser = scan.next();
			
			user_name = dbm.login(idUser);
			if(user_name == "")
				System.out.println("Incorrect credentials: please try again or contact a librarian for help.");
		} while(user_name == "");
		
		System.out.println("Welcome " + user_name +". The following commands are available: ");
		System.out.println("");
		
		boolean privilege = dbm.check_privilege(idUser);
		printMsg(privilege);
		
		while(true)
		{
			System.out.print(">");
			command = scan.next();
			
				switch(command)
				{
					case "!list":
						System.out.println("Books in the Catalogue:\n");
						System.out.println("========================================================================================================================");
						System.out.printf("%-15s %-60s %-25s %-15s %n", "Book Code", "Title", "Author", "Availability");
						System.out.println("========================================================================================================================");
						
						List<String> elenco = dbm.list();
						String avail;
						
						for(int i=0; i<elenco.size(); i+=4)
						{
							if(elenco.get(i+3) == "(Available)")
								avail = GREENC + elenco.get(i+3) + ENDC;
							else avail = REDC + elenco.get(i+3) + ENDC;
							
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
						else if(code == 1)
						{
							//1) stampo titolo e autore e chiedo conferma del prestito
							List<String> bookInfo = dbm.getBookInfo(idBook);
							System.out.println("You chose to borrow: " + bookInfo.get(0) + ", written by " + bookInfo.get(1));
							System.out.println("Confirm this choice? (y/n): ");
							System.out.print(">");
							
							String cin = scan.next();
							System.out.println("");
							
							if(cin.compareTo("y") == 0)
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
							else System.out.println(GREENC + "Book is already registered in the Catalogue." + ENDC);
							System.out.println("How many copies are to be inserted? ");
							System.out.print(">");
							int n = scan.nextInt();
							
							dbm.addBook(idBook, Title, Author, n);
							break;
						}
					case "!remove":
						if(privilege)
						{
							System.out.println("Insert the ID of the book to be removed: ");
							System.out.print(">");
							idBook = scan.next();
							
							if(!dbm.checkBookExistance(idBook))
							{
								System.out.println(REDC + "Invalid book ID." + ENDC);
								break;
							}
							
							System.out.println("How many copies are to be removed? ");
							System.out.print(">");
							int n = scan.nextInt();
							
							List<String> bookInfo = dbm.getBookInfo(idBook);
							System.out.println("You are removing " + n + " copies of " + bookInfo.get(0) + ", written by " + bookInfo.get(1));
							System.out.println("Confirm this choice? (y/n): ");
							System.out.print(">");
							
							String cin = scan.next();
							System.out.println("");
							
							if(cin.compareTo("y") == 0)
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
							
							System.out.println("Insert the user's name:");
							System.out.print(">");
							String newname = scan.next();
							
							System.out.println("Insert the user's surname:");
							System.out.print(">");
							String newsurname = scan.next();
							
							dbm.createuser(newid, newname, newsurname);
							break;
						}
					default:
						System.out.println(REDC + "Invalid command." + ENDC + "Type '!help' to view available commands.");
						break;
			}
		}
	}

}
