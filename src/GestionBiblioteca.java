import java.util.*;

public class GestionBiblioteca {

	public static void main(String[] args) {
		
		DBManager dbm = new DBManager(3306, "feanor", "password", "lsdb");
		String command;
		
		dbm.start();
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("================= Welcome in BibliOS =================");
		String user_name = "";
		
		do {
			System.out.println("Please, insert your access code found in your library card:");
			System.out.print(">");
			String usrname = scan.next();
			
			user_name = dbm.login(usrname);
			if(user_name == "")
				System.out.println("Incorrect credentials: please try again or contact a librarian for help.");
		} while(user_name == "");
		
		if(user_name.compareTo("Giulio")== 0)
			System.out.println("GIRATI!!!!!!!!!");
		System.out.println("Welcome " + user_name +". The following commands are available: ");
		System.out.println("");
		System.out.println("!list --> visualize the catalog for this library.");
		System.out.println("!borrow --> request to borrow a specific book.");
		System.out.println("!return --> return to the library a previously borrowed book.");
		System.out.println("!exit --> logout and close the program.");
		System.out.println("");
		
		while(true)
		{
			System.out.print(">");
			command = scan.next();
			
				switch(command)
				{
				case "!list":
					List<String> elenco = dbm.list();
					for(int i=0; i<elenco.size(); i++)
						System.out.println(elenco.get(i));
					break;
				case "!exit":
					System.out.println("Closing program...");
					dbm.stop();
					scan.close();
					System.exit(0);
					break;
				default:
					System.out.println("Command non yet implemented...");
					break;
			}
		}
	}

}
