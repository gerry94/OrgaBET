package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import main.java.Main;

import java.io.IOException;

public class RatingPopUpController extends Controller {
	
	@FXML
	private MenuButton rating_but;
	
	@FXML
	private Button confirm_but;
	
	@FXML
	void confirmRating(ActionEvent event) throws IOException
	{
		Main.changeScene(4);
	}
	
}
