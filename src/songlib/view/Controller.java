package songlib.view;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addBtn;
    
    @FXML
    private Button delBtn;
    
    @FXML
    private Button editBtn;
    
    @FXML
    private TextArea songList;
    
    @FXML
    private Label statusBar;

    @FXML
    public void onButton(ActionEvent e) {
    	if (e.getSource() == addBtn){
    		statusBar.setText("added!");
    		return;
    	}
    }

    @FXML
    void initialize() {
    	
    	try {
            Scanner s = new Scanner(new File("songs.csv")).useDelimiter("\"(,\")?");
            while (s.hasNext()) {
            	
                songList.appendText(s.next() + "\n"); // adds each song in list to textarea
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);	//no file
        }
    	
    	assert addBtn != null : "fx:id=\"addBtn\" was not injected: check your FXML file 'Layout.fxml'.";
        assert songList != null : "fx:id=\"songList\" was not injected: check your FXML file 'Layout.fxml'.";
        assert delBtn != null : "fx:id=\"delBtn\" was not injected: check your FXML file 'Layout.fxml'.";
        assert editBtn != null : "fx:id=\"editBtn\" was not injected: check your FXML file 'Layout.fxml'.";
        assert statusBar != null : "fx:id=\"statusBar\" was not injected: check your FXML file 'Layout.fxml'.";


    }
}
