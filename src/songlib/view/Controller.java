/*
 * Calvin Lee, Bartosz Kidacki
 * Rutgers CS213
 */

package songlib.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	
	class songObject 
	{
		String title;
		String artist;
		String album;
		String year;
		
		songObject(String t, String a){
			this.title = t;
			this.artist = a;
			this.album = "n/a";
			this.year = "n/a";
		}
	}
	public class songComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			songObject song1 = (songObject) o1;
			songObject song2 = (songObject) o2;
			return song1.title.compareTo(song2.title);
					
		}
	}
	songComparator songComp = new songComparator();
	
	private boolean editing;
	private boolean adding;
	ArrayList<songObject> songsArray = new ArrayList<songObject>();
	ObservableList<String> songTitles = FXCollections.observableArrayList();
	
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
    	if (b == true){		//if asked to go into add mode
    		if (!editing && !adding ){
    			adding = true;
    			
    			fieldsOn(b);
    		}
    	} else {	//if asked to exit out of edit mode
    		if (editing || adding ){
    			adding = false;
    			
    			fieldsOn(b);
    		}
    	}
    	return;
    }
    
    @FXML
    public void fieldsOn( boolean b ) {
    	if (b == true ) {
    		nameLabel.setVisible(false);	//make labels invisible
    		artistLabel.setVisible(false);
    		albumLabel.setVisible(false);
    		yearLabel.setVisible(false);
    		
			nameField.setVisible(true);		//make fields visible
    		artistField.setVisible(true);
    		albumField.setVisible(true);
    		yearField.setVisible(true);
    		
    		nameField.setEditable(true);	//make fields editable
    		artistField.setEditable(true);
    		albumField.setEditable(true);
    		yearField.setEditable(true);
    		
    		nameField.setDisable(false);	//enable fields
    		artistField.setDisable(false);
    		albumField.setDisable(false);
    		yearField.setDisable(false);
    		
    		addBtn.setDisable(true);		//disable add/del/edit buttons
    		delBtn.setDisable(true);
    		editBtn.setDisable(true);
    		
    		doneBtn.setDisable(false);		//enable done/cancel buttons
    		cancelBtn.setDisable(false);
    		
    		songList.setMouseTransparent( true );	//disable song list
    		songList.setFocusTraversable( false );
    		songList.setDisable(true);
    		
    	} else {
    		nameLabel.setVisible(true);		//make labels visible
    		artistLabel.setVisible(true);
    		albumLabel.setVisible(true);
    		yearLabel.setVisible(true);
			
			nameField.setVisible(false);	//make fields invisible
    		artistField.setVisible(false);
    		albumField.setVisible(false);
    		yearField.setVisible(false);
			
			nameField.setEditable(false);	//make fields uneditable
    		artistField.setEditable(false);
    		albumField.setEditable(false);
    		yearField.setEditable(false);
    		
    		nameField.setDisable(true);		//disable fields
    		artistField.setDisable(true);
    		albumField.setDisable(true);
    		yearField.setDisable(true);
    		
    		addBtn.setDisable(false);		//enable add/del/edit buttons
    		delBtn.setDisable(false);
    		editBtn.setDisable(false);
    		
    		doneBtn.setDisable(true);		//disable done/cancel buttons
    		cancelBtn.setDisable(true);
    		
    		songList.setMouseTransparent( false );	//enable song list
    		songList.setFocusTraversable( true );
    		songList.setDisable(false);
    	}
    	return;
    }

    public void setSelected(String title) {
    	//setStatus("selected index: " + songList.getSelectionModel().getSelectedIndex());
    	return;
	}
    
    @FXML
    void initialize() {
    	editing = false;
    	adding = false;
    	
    	songList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){	//generate listener for list
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {	//if list selection changes
				//setStatus("selected: " + newValue);
				//setSelected(newValue);
			}
    	});
    	
    	String csvFile = "songs.csv";
    	BufferedReader br = null;
    	String line = "";
    	String csvSplitBy = ",";

    	try {
    		
    		br = new BufferedReader(new FileReader(csvFile));
    		while ((line = br.readLine()) != null) {	//for each song in the song list csv,
    			String[] songDetails = {"","","",""};
    			songDetails = line.split(csvSplitBy);	//create array of song details, separated by ","
    			
    			songObject newSongObject = new songObject(songDetails[0], songDetails[1]);	//make new songObject object
    			if (songDetails[2]!=""){
    				newSongObject.album = songDetails[2];
    			}else{
    				newSongObject.album = "n/a";
    			}
    			if (songDetails[3]!=""){
    				newSongObject.year = songDetails[3];
    			}else{
    				newSongObject.year = "n/a";
    			}
    			
    			songsArray.add(newSongObject);	//add this song (and its details) to song array
    			songTitles.add(newSongObject.title);
    		}
    		
    		songsArray.sort(songComp);
    		FXCollections.sort(songTitles);
    		songList.setItems(songTitles);
    		
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
