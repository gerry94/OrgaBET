
public class Main {

    public static void main(String[] args) {
    	
        LibraryManager lm= new LibraryManager();
        lm.setup();
        lm.login("AR5012715");
        lm.addUser("AR5050103", "Marco", "Dilaria");
        lm.addBook(00000000,"Mario Barbarulo", "Manuale del trapano", 2);
        lm.addBook(00430000,"Paolo Corsini", "Dalle porte and or not...", 3);
        lm.logout();
        lm.login("AR5012715");
        //lm.borrowBook(0);
        lm.returnBook(0);
        lm.exit();
    }
}
