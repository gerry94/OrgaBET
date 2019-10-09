
public class GestionBiblioteca {

	public static void main(String[] args) {
		
		DBManager dbm = new DBManager(3306, "feanor", "password", "lsdb");
		
		dbm.start();
		dbm.stop();

	}

}
