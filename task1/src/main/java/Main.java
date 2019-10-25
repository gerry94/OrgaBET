import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("BibliOS");
        Parent root = FXMLLoader.load(getClass().getResource("BibliosLogin.fxml"));
        primaryStage.setScene(new Scene(root, 640, 300));
        primaryStage.show();

        DBManager dbm = new DBManager(3306, "test", "password", "lsdb");
        dbm.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
