import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;
    private static Parent root;
    public static LibraryManager lm;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        lm = new LibraryManager();
        lm.setup();
        stage = primaryStage;
        stage.setTitle("BibliOS");
        changeScene(0); //0: login, 1: user, 2: librarian_base, 3:librarian_book, 4:librarian_user, 5:librarian_loans
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void changeScene(int type) throws IOException {
        switch(type) {
            case 0:
                root = FXMLLoader.load(Main.class.getResource("/BibliosLogin.fxml"));
                break;
            case 1:
                root = FXMLLoader.load(Main.class.getResource("/UserInterface.fxml"));
                break;
            default:
                break;
        }
        stage.setScene(new Scene(root, 640, 400));
        stage.show();
    }
}
