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
    private Button next_but;
    @FXML
    private Button previous_but;
    @FXML
    private Label page_count;
    @FXML
    private TextField isbn_field;

    public int tableOffset, currentPage, totalPages;

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
        tableOffset = 0;
        book_table.setItems(Main.lm.browseBooks(tableOffset));

        previous_but.setDisable(true);
        totalPages = ((Main.lm.getNumBooks() + 9)/10);
        currentPage = 1;

        page_count.setText("Page " + currentPage + " of " + totalPages);
    }

    public void updateTable(ObservableList<Book> list)
    {
        book_table.setItems(list);
    }

    @FXML
    public void nextPage(ActionEvent ev) {
        output_msg.clear();

        tableOffset++;
        currentPage++;
        page_count.setText("Page " + currentPage + " of " + totalPages);

        if(currentPage == totalPages) next_but.setDisable(true);
        if(previous_but.isDisabled()) previous_but.setDisable(false);

        updateTable(Main.lm.searchBooks(0, search_field.getText(), tableOffset));
    }

    @FXML
    public void previousPage(ActionEvent ev) {
        output_msg.clear();

        tableOffset--;
        currentPage--;
        page_count.setText("Page " + currentPage + " of " + totalPages);

        if(currentPage <= 1) previous_but.setDisable(true);
        if(next_but.isDisabled()) next_but.setDisable(false);

        updateTable(Main.lm.searchBooks(0, search_field.getText(), tableOffset));
    }

    @FXML
    void addBook(ActionEvent event) {
        if((isbn_field.getText().isEmpty()) || (title_field.getText().isEmpty()) || (author_field.getText().isEmpty()) || (category_field.getText().isEmpty()) || (copies_field.getText().isEmpty()))
        {
            //error log
            output_msg.setText("ERROR: You have an error in the input boxes. Please check for typos and retry.");
        }
        else if(!copies_field.getText().matches("[0-9]+"))
                output_msg.setText("ERROR: You have an error in the numCopies field, only numbers are allowed. Please check for typos and retry.");
        else if(!isbn_field.getText().matches("[0-9]+"))
            output_msg.setText("ERROR: You have an error in the ISBN field, only numbers are allowed. Please check for typos and retry.");

        //finally, add book and refresh table
        output_msg.setText(Main.lm.addBook(Long.parseLong(isbn_field.getText()), title_field.getText(), author_field.getText(), category_field.getText(), Integer.parseInt(copies_field.getText())));
        updateTable(Main.lm.searchBooks(0, search_field.getText(), tableOffset));
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.changeScene(2);
    }

}
