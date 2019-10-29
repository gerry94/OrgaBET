import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.text.Text;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class LibrarianLoansController implements Initializable {

	@FXML
    private Button search_but;

    @FXML
    private Button loan_but;

    @FXML
    private TextField output_msg;

    @FXML
    private Button logout_but;

    @FXML
    private Button back_but;

    @FXML
    private MenuButton search_filter;

    @FXML
    private Button return_but;

    @FXML
    private TableView<?> return_table;

    @FXML
    private TableView<?> loan_table;

    @FXML
    private Text welcome_msg;

    @FXML
    private TextArea search_field;

    @FXML
    void ffffff00(ActionEvent event) {

    }

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
    void back(ActionEvent event) throws IOException {
    	Main.changeScene(2);
    }

}