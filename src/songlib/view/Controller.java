package songlib.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {
	
	private boolean editing;
	private boolean adding;
	
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
    private ListView<String> songList;
    
    @FXML
    private Label statusBar;


    @FXML
    private Button doneBtn;

    @FXML
    private Button cancelBtn;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField artistField;
    
    @FXML
    private TextField albumField;
    
    @FXML
    private TextField yearField;
    
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label artistLabel;
    
    @FXML
    private Label albumLabel;
    
    @FXML
    private Label yearLabel;
    
    @FXML
    public void onButton(ActionEvent e) {	 		
    	
    	if (e.getSource() == addBtn){
    		setStatus( "adding.." );
    		addMode( true );
    		return;
    	}
    	else if (e.getSource() == delBtn){
    		setStatus( "deleted!" );
    		return;
    	}
    	else if (e.getSource() == editBtn){
    		setStatus( "editing.." );
    		editMode( true );
    		return;
    	} 
    	else if (e.getSource() == cancelBtn){
			setStatus( "canceled." );
			editMode( false );
			addMode( false );
    		return;
    	}
    	else if (e.getSource() == doneBtn){
			setStatus( "done!" );
			if (editing)
				editMode( false );
			else if (adding)
				addMode( false );
    		return;
    	}  	
    }
    
    @FXML
    public void setStatus( String status ){
    	statusBar.setText( status );
    	return;
    }
    
    @FXML
    public void editMode( boolean b ){
    	if ( b == true){	//if asked to go into edit mode
    		if (!editing && !adding ){
	    		editing = true;
	    		editBtn.setDisable(true);
	    		fieldsOn(b);
    		}    		
    	} else {	//if asked to exit out of edit mode
    		if ( editing || adding ){
    			editing = false;
    			editBtn.setDisable(false);
    			fieldsOn(b);
    		}
    	}
    	return;
    }
    
    @FXML
    public void addMode ( boolean b ){
    	if (b == true){
    		if (!editing && !adding ){
    			adding = true;
    			addBtn.setDisable(true);
    			fieldsOn(b);
    		}
    	} else {
    		if (editing || adding ){
    			adding = false;
    			addBtn.setDisable(false);
    			fieldsOn(b);
    		}
    	}
    	return;
    }
    
    @FXML
    public void fieldsOn( boolean b ) {
    	if (b == true ) {
    		nameLabel.setVisible(false);
    		artistLabel.setVisible(false);
    		albumLabel.setVisible(false);
    		yearLabel.setVisible(false);
    		
			nameField.setVisible(true);
    		artistField.setVisible(true);
    		albumField.setVisible(true);
    		yearField.setVisible(true);
    		
    		nameField.setEditable(true);
    		artistField.setEditable(true);
    		albumField.setEditable(true);
    		yearField.setEditable(true);
    		
    		nameField.setDisable(false);
    		artistField.setDisable(false);
    		albumField.setDisable(false);
    		yearField.setDisable(false);
    		
    		doneBtn.setDisable(false);
    		cancelBtn.setDisable(false);
    	} else {
    		nameLabel.setVisible(true);
    		artistLabel.setVisible(true);
    		albumLabel.setVisible(true);
    		yearLabel.setVisible(true);
			
			nameField.setVisible(false);
    		artistField.setVisible(false);
    		albumField.setVisible(false);
    		yearField.setVisible(false);
			
			nameField.setEditable(false);
    		artistField.setEditable(false);
    		albumField.setEditable(false);
    		yearField.setEditable(false);
    		
    		nameField.setDisable(true);
    		artistField.setDisable(true);
    		albumField.setDisable(true);
    		yearField.setDisable(true);
    		
    		doneBtn.setDisable(true);
    		cancelBtn.setDisable(true);
    	}
    	return;
    }

    @FXML
    void initialize() {
    	editing = false;
    	ObservableList<String> songs = FXCollections.observableArrayList();
    	String songTitle;
    	
    	String csvFile = "songs.csv";
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";

    	try {
    		
    		br = new BufferedReader(new FileReader(csvFile));
    		while ((line = br.readLine()) != null) {

    			String[] song = line.split(cvsSplitBy);
    			songTitle = song[0];
    			songs.add(songTitle);
    		}
    		
    		FXCollections.sort(songs);
    		songList.setItems(songs);
    		
    		setStatus("done reading song list!");
    		

    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}

    	
        assert addBtn != null : "fx:id=\"addBtn\" was not injected: check your FXML file 'Layout.fxml'.";
        assert delBtn != null : "fx:id=\"delBtn\" was not injected: check your FXML file 'Layout.fxml'.";
        assert editBtn != null : "fx:id=\"editBtn\" was not injected: check your FXML file 'Layout.fxml'.";
        assert statusBar != null : "fx:id=\"statusBar\" was not injected: check your FXML file 'Layout.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'Layout.fxml'.";
        assert artistField != null : "fx:id=\"artistField\" was not injected: check your FXML file 'Layout.fxml'.";
        assert albumField != null : "fx:id=\"albumField\" was not injected: check your FXML file 'Layout.fxml'.";
        assert yearField != null : "fx:id=\"yearField\" was not injected: check your FXML file 'Layout.fxml'.";
        assert nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'Layout.fxml'.";
        assert artistLabel != null : "fx:id=\"artistLabel\" was not injected: check your FXML file 'Layout.fxml'.";
        assert albumLabel != null : "fx:id=\"albumLabel\" was not injected: check your FXML file 'Layout.fxml'.";
        assert yearLabel != null : "fx:id=\"yearLabel\" was not injected: check your FXML file 'Layout.fxml'.";
        assert songList != null : "fx:id=\"songList\" was not injected: check your FXML file 'Layout.fxml'.";
        assert doneBtn != null : "fx:id=\"doneBtn\" was not injected: check your FXML file 'Layout.fxml'.";
        assert cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'Layout.fxml'.";

    }
}
