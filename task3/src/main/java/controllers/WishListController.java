package main.java.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.Main;
import main.java.models.Book;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WishListController extends Controller
{
	@FXML
	private Label welcome_msg;
	
	@FXML
	private TableView<Book> book_table;
	
	@FXML
	private TableColumn idCol, titleCol, authorCol, ratingCol;
	
	@FXML
	private Button mark_but, next_but, previous_but;
	
	@FXML
	private Label page_count;
	
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		welcome_msg.setText("Welcome, " + Controller.getUsername());
		
		//blocking table column resize
		titleCol.setResizable(false);
		authorCol.setResizable(false);
		ratingCol.setResizable(false);
		
		//associating the table's column with the corresponding attributes of the book class
		idCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookId"));
		titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		
		List<Book> tmpBooks = Main.gm.getWishList(Main.lm.getIdNode());
		
		ObservableList<Book> books = FXCollections.observableArrayList();
		for (Book b : tmpBooks)
			books.add(b);
		updateTable(books);
	}
	
	public void updateTable(ObservableList<Book> list) {
		book_table.setItems(list);
	}
	
	@FXML
	void nextPage(ActionEvent event)
	{
	
	}
	
	@FXML
	void previousPage(ActionEvent event)
	{
	
	}
	@FXML
	void removeSelected(ActionEvent event) {
	
	}
	
	@FXML
	void markRead(ActionEvent event) {
	
	}
}
