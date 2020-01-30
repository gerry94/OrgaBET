package main.java;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
import javafx.geometry.*;
import main.java.models.Book;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;
    private static Parent root;
    public static LibraryManager lm;
    public static GraphManager gm;
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        lm = new LibraryManager();
        lm.setup();

        //gm = new GraphManager("bolt://localhost:7687", "neo4j", "test");
        gm = new GraphManager();
        gm.setup();
        Book b=gm.find(Long.parseLong("101"));
        System.out.println(b.toString());
    /*
        final Configuration configuration = new Configuration.Builder()
                .uri("bolt://localhost:7687")
                .credentials("neo4j", "test")
                .build();

        SessionFactory sessionFactory = new SessionFactory(configuration, "main.java");
        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        System.out.println("==> There are " + session.countEntitiesOfType(Book.class) + " books in the DB.");
        tx.close();
        */
        stage = primaryStage;
        stage.setTitle("BookRater");
        changeScene(0); //0: login
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void changeScene(int type) throws IOException {
        switch(type) {
            case 0:
                root = FXMLLoader.load(Main.class.getResource("/Login.fxml"));
                stage.setScene(new Scene(root, 640, 400));
                break;
            case 1:
                root = FXMLLoader.load(Main.class.getResource("/UserHome.fxml"));
                stage.setScene(new Scene(root, 230, 360));
                break;
            case 2:
            	root = FXMLLoader.load(Main.class.getResource("/Suggestions.fxml"));
                stage.setScene(new Scene(root, 800, 640));
            	break;
            case 3:
            	root = FXMLLoader.load(Main.class.getResource("/WishList.fxml"));
                stage.setScene(new Scene(root, 960, 650));
            	break;
            case 4:
            	root = FXMLLoader.load(Main.class.getResource("/Catalogue.fxml"));
                stage.setScene(new Scene(root, 1000, 640));
            	break;
            case 5:
            	//root = FXMLLoader.load(Main.class.getResource("/ReadBooks.fxml"));
                //stage.setScene(new Scene(root, 800, 640));
            	break;
            case 6:
                root = FXMLLoader.load(Main.class.getResource("/RatingPopUp.fxml"));
                stage.setScene(new Scene(root, 360, 230));
                break;
            default:
                break;
        }

        stage.show();
        centerStage(stage);
    }

    public static void exit() { Platform.exit(); }

    public static void centerStage(Stage s) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        s.setX((primScreenBounds.getWidth() - s.getWidth()) / 2);
        s.setY((primScreenBounds.getHeight() - s.getHeight()) / 2);
    }
}
