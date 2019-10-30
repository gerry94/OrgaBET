import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.text.Text;

public class LibrarianUIController implements Initializable {

    @FXML
    private Button loan_but;
    @FXML
    private TextField output_msg;
    @FXML
    private Button logout_but;
    @FXML
    private Button book_but;
    @FXML
    private Text welcome_msg;
    @FXML
    private Button user_but;

    @FXML
    void logout(ActionEvent event) throws IOException {

        Main.lm.logout();
        Main.changeScene(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome_msg.setText("Welcome " + Controller.getUsername());

        ObservableList<Book> bookList = FXCollections.observableArrayList();
    }
    @FXML
    void books(ActionEvent event) throws IOException {
    	Main.changeScene(3);
    }

    @FXML
    void users(ActionEvent event) throws IOException {
    	Main.changeScene(4);
    }

    @FXML
    void loans(ActionEvent event) throws IOException {
    	Main.changeScene(5);
    }
}