import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.cell.*;
import javafx.scene.text.Text;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LibrarianLoansController implements Initializable {

    @FXML
    private Text welcome_msg;
    @FXML
    private TableView<Loan> loan_table;
    @FXML
    private TableColumn loanUserIdCol;
    @FXML
    private TableColumn loanBookIdCol;
    @FXML
    private TextField output_msg;
    @FXML
    private TableView<Loan> return_table;
    @FXML
    private TableColumn returnUserIdCol;
    @FXML
    private TableColumn returnBookIdCol;
    @FXML
    private MenuButton search_filter;
    @FXML
    private Button search_but;
    @FXML
    private Button logout_but;
    @FXML
    private TextField search_field;
    @FXML
    private Button loan_but;
    @FXML
    private Button return_but;
    @FXML
    private Button back_but;

    @FXML
    void logout(ActionEvent event) throws IOException {

        Main.lm.logout();
        Main.changeScene(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome_msg.setText("Welcome " + Controller.getUsername());
/*
        returnUserIdCol.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        loanUserIdCol.setCellValueFactory(new PropertyValueFactory<User, String>("id"));

        returnBookIdCol.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
        loanBookIdCol.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));

        loan_table.setItems(Main.lm.browseLoans());
        return_table.setItems(Main.lm.browseLoans());
*/
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.changeScene(2);
    }

}
