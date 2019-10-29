import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.text.Text;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class LibrarianBooksController implements Initializable {

    @FXML
    private Button search_but;

    @FXML
    private Button logout_but;

    @FXML
    private TextArea category_field;

    @FXML
    private Button back;

    @FXML
    private TableView<Book> book_table;
    @FXML
    private TableColumn<Book, Long> idCol;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> categoryCol;
    @FXML
    private TableColumn<Book, Integer> copiesCol;
  
    @FXML
    private Button add_but;

    @FXML
    private Text welcome_msg;

    @FXML
    private TextArea title_field;

    @FXML
    private TextArea copies_field;

    @FXML
    private TextField output_msg;

    @FXML
    private MenuButton search_filter;

    @FXML
    private Button remove_but;

    @FXML
    private TextArea author_field;

    @FXML
    private TextArea search_field;

    @FXML
    void ffffff00(ActionEvent event) {

    }

    @FXML
    void  Copies(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) throws IOException {

        Main.lm.logout();
        Main.changeScene(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome_msg.setText("Welcome " + Controller.getUsername());

        //associating the table's column with the corresponding attributes of the book class
        idCol.setCellValueFactory(new PropertyValueFactory<Book, Long>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Book, String>("category"));
        copiesCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("numCopies"));

        //filling the table with the list returned by the query
        book_table.setItems(Main.lm.browseBooks(0));
    }


    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.changeScene(2);
    }

}
