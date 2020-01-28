import org.graalvm.compiler.graph.Graph;

import java.util.*;

public class BookRater {
	
	public static final String REDC="\033[0;31m", GREENC="\033[0;32m", ENDC="\033[0m";
	
	public static void printMsg(boolean privilege)
	{
		System.out.println("!help --> view available commands.");
		
		if(privilege)
		{
			System.out.println("!add --> add a new book in the library's Catalogue.");
			System.out.println("!remove --> remove a book from the library's Catalogue.");
		}
		else
		{
			System.out.println("!catalogue --> view the Books not already read, from the library's catalogue.");
			System.out.println("!list --> view the list of read books.");
			System.out.println("!wishlist --> view the list of desired books.");
			System.out.println("!suggest --> view a selection of recommended books, based on your ratings.");
		}
		
		System.out.println("!logout --> logout from the program.");
		if(privilege)
			System.out.println("!exit --> logout and shutdown the system.");
		System.out.println("");
	}
	
	public static String login(Scanner scan)
	{		
		System.out.print("\033[H\033[2J");  //"clear" the screen
	    	System.out.flush();
	    
		System.out.println("====================== Welcome in BookRater ======================");
		String user_name = "", idUser = "";
		
		do {
			System.out.println("Please, insert your credentials:");
			System.out.print(">");
			System.out.flush(); 
			idUser = scan.next();
			
			if(!idUser.matches("[a-zA-Z0-9]+") || idUser.contentEquals("")) //checks that input is alphanumeric only
			{
				System.out.println(REDC + "Invalid input: please try again or contact a librarian for help." + ENDC);
				continue;
			}
			else
			{
				List<String> result = new ArrayList<String>();
                result = lm.login(login_code.getText());
                
				if (result == null){
                    idUser = "";
					System.out.println("Incorrect credentials: please try again or contact a librarian for help.");
                }
                else {
                     user_name = result.get(0);
                     idUser = login_code.getText();
                     privilege = Integer.parseInt(result.get(1));}
                     }
		} while(user_name.equals(""));
		
		System.out.println("Welcome " + user_name +". The following commands are available: ");
		System.out.println("");
		return idUser;
		
	}
	
	public static void main(String[] args) {
		public static LibraryManager lm = new LibraryManager();
        lm.setup();
		GraphManager gm = new GraphManager("bolt://localhost:7687", "neo4j", "test");
		
		String command, cin, option;
		
		dbm.start();
		
		Scanner scan = new Scanner(System.in).useDelimiter("\n");
		
		String idUser=login(dbm,scan);
		boolean privilege = dbm.check_privilege(idUser);
		printMsg(privilege);
		
		while(true)
		{
			System.out.print(">");
			System.out.flush();
			
			command = scan.next();
			if(command.matches("[a-zA-Z!]+")) //only a-z character and ! are allowed
			{
				switch(command)
				{
					case "!help":
						printMsg(privilege);
						break;
					case "!catalogue":
						System.out.println("Books in the Catalogue (not read yeat):\n");
						System.out.println("========================================================================================================================");
						System.out.printf("%-15s %-60s %-25s %-15s %n", "Book Code", "Title", "Author", "Availability");
						System.out.println("========================================================================================================================");
						System.out.println("lista e cazzate varie");
						
						System.out.println("The following additional commands are available: ");
						System.out.println("\t!add --> add a specific book to your wishlist.");
						System.out.println("\t!back --> return to main menu");
						
						System.out.print(">");
						System.out.flush();
						
						option = scan.next();
						if(option.matches("[a-zA-Z!]+")) //only a-z character and ! are allowed
						{
							switch (option)
							{
								case("!add"):
									break;
								default: break;
							}
						}
						break;
					case "!list":
						System.out.println("Books that you have read:\n");
						System.out.println("========================================================================================================================");
						System.out.printf("%-15s %-60s %-25s %n", "Book Code", "Title", "Author");
						System.out.println("========================================================================================================================");
						
						//print di prova - non sono realmente i libri di questo utente
						List<String> lista = gm.getBooks();
						for(String s : lista) {
							System.out.printf("%-15s %-60s %-25s %n", "...", s,"...");
						}
						System.out.println("\nThe following additional commands are available: ");
						System.out.println("\t!add --> add a specific book to your wishlist.");
						System.out.println("\t!tag --> add a metadata TAG to one of the books you have read.");
						System.out.println("\t!rate --> rate a book with a score from 1 to 5 stars.");
						System.out.println("\t!back --> return to main menu");
						
						System.out.print(">");
						System.out.flush();
						
						option = scan.next();
						if(option.matches("[a-zA-Z!]+")) //only a-z character and ! are allowed
						{
							switch(option) {
								case("!tag"):
									break;
								case("!rate"):
									break;
								default:
									break;
							}
						}
						break;
					
					case "!wishlist":
						System.out.println("List of your desired Books:\n");
						System.out.println("========================================================================================================================");
						System.out.printf("%-15s %-60s %-25s %-15s %n", "Book Code", "Title", "Author", "Availability");
						System.out.println("========================================================================================================================");
						System.out.println("The following additional commands are available: ");
						System.out.println("\t!remove --> remove a book from this list.");
						System.out.println("\t!back --> return to main menu");
						System.out.print(">");
						System.out.flush();
						
						option = scan.next();
						if(option.matches("[a-zA-Z!]+")) //only a-z character and ! are allowed
						{
							switch (option)
							{
								case ("!remove"):
									break;
								default:
									break;
							}
						}
						break;
					
					case "!suggest":
						System.out.println("List of suggested Books based on your preferences:\n");
						System.out.println("========================================================================================================================");
						System.out.printf("%-15s %-60s %-25s %-15s %n", "Book Code", "Title", "Author", "Availability");
						System.out.println("========================================================================================================================");
						
						break;
					
					case "!logout":
						lm.logout();
						break;
					case "!exit":
						if(privilege){
							System.out.println("Closing program...");
							lm.exit();
							System.exit(0);
							break;
						}
					case "!add":
						if(privilege)
						{
							System.out.println("Insert book ID: ");
							System.out.print(">");
							System.out.flush();
							
							break;
						}
					case "!remove":
						if(privilege)
						{
							System.out.println("Insert the ID of the book to be removed: ");
							System.out.print(">");
							System.out.flush();
							
							break;
						}
					default:
						System.out.println(REDC + "Invalid command." + ENDC + "Type '!help' to view available commands.");
						break;
			} //switch
		}
		else System.out.println(REDC + "Invalid command." + ENDC + "Type '!help' to view available commands.");
		}
	} //main
}
