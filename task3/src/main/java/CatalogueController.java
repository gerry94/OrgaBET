package main.java;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CatalogueController extends Controller {
	
	@FXML
	private Button logout_but;
	
	@FXML
	private Label welcome_msg;
	
	@FXML
	private TableView<Object> book_table;
	
	@FXML
	private TableColumn<?, ?> idCol;
	
	@FXML
	private TableColumn<?, ?> titleCol, authorCol, ratingCol, readCol;
	@FXML
	private Button next_but;
	
	@FXML
	private Button previous_but;
	
	@FXML
	private Label page_count;
	
	@FXML
	private Button back;
	
	@FXML
	private Button mark_but;
	
	@FXML
	private Button add_but;
	
	public void initialize(URL url, ResourceBundle resourceBundle) {
		welcome_msg.setText("Welcome, " + Controller.getUsername());
		
		//blocking table column resize
		titleCol.setResizable(false);
		authorCol.setResizable(false);
		ratingCol.setResizable(false);
		readCol.setResizable(false);
		
		//associating the table's column with the corresponding attributes of the book class
		/*titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		categoryCol.setCellValueFactory(new PropertyValueFactory<Book, String>("category"));
		
		//filling the table with the list returned by the query
		
		updateTable(Main.lm.browseBooks(tableOffset, filterAvailable));
		tableOffset = 0;
		
		previous_but.setDisable(true);
		totalPages = ((Main.lm.getNumBooks() + 9)/10);
		currentPage = 1;
		
		page_count.setText("Page " + currentPage + " of " + totalPages);*/
	}
	
	/*public void updateTable(ObservableList<Book> list) {
		book_table.setItems(list);
	}*/
	
	@FXML
	void addToWishList(ActionEvent event) {
	
	}
	
	@FXML
	void markAsRead(ActionEvent event) throws IOException
	{
		Main.changeScene(6); //open the rating popup
	}
	
	@FXML
	void nextPage(ActionEvent event) {
	
	}
	
	@FXML
	void previousPage(ActionEvent event) {
	
	}
	
}

