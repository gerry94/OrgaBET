package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class WishListController extends Controller
{
	@FXML
	private Label welcome_msg;
	
	@FXML
	private TableView<?> book_table;
	
	@FXML
	private TableColumn<?, ?> idCol;
	
	@FXML
	private TableColumn<?, ?> titleCol;
	
	@FXML
	private TableColumn<?, ?> authorCol;
	
	@FXML
	private TableColumn<?, ?> categoryCol;
	
	@FXML
	private TableColumn<?, ?> copiesCol;
	
	@FXML
	private Button next_but;
	
	@FXML
	private Button previous_but;
	
	@FXML
	private Label page_count;
	
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
}
