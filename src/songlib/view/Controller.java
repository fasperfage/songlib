/*
 * Written by Calvin Lee and Bartosz Kidacki
 */

package songlib.view;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import songlib.app.Song;

public class Controller {
	
	private boolean editing;
	private boolean adding;
	private Song selectedSong;
	ObservableList<Song> songs = FXCollections.observableArrayList();
	
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
    private ListView<Song> songList;
    
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
    		int deletedIndex = songList.getSelectionModel().getSelectedIndex();
    		
			nameLabel.setText(null);
			artistLabel.setText(null);
			yearLabel.setText(null);
			albumLabel.setText(null);
			songs.remove(selectedSong);
			if(songs.size() == 0) {
				editBtn.setDisable(true);
				delBtn.setDisable(true);
			}
			save();
			if ( ! (deletedIndex == songs.size()) ){
				songList.getSelectionModel().selectNext();
			}
			
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

			if (editing) {
				String name = nameField.getText();
				String artist= artistField.getText();

				if (name!=null&& !name.isEmpty()&& artist!=null && !artist.isEmpty()){
					Song song = new Song(nameField.getText(), artistField.getText(),
							albumField.getText(), yearField.getText());

					if((!song.getName().equals(selectedSong.getName()) || !song.getArtist().equals(selectedSong.getArtist()))
							&& isDuplicate(song))
					{
						setStatus("song with this name and artist already exists!");
					}
					else {
						songs.remove(selectedSong);
						songs.add(song);
						FXCollections.sort(songs);
						songList.getSelectionModel().select(song);
						editMode( false );
						save();
					}

				}
				else {
					// name or artist is empty
					setStatus( "name and artist cannot be empty" );
				}
			}
			else if (adding) {
				String name = nameField.getText();
				String artist = artistField.getText();

				if(name != null && !name.isEmpty() && artist != null && !artist.isEmpty()) {
					Song song = new Song(nameField.getText(), artistField.getText(),
							albumField.getText(), yearField.getText());
					if(isDuplicate(song)) {
						setStatus("song with this name and artist already exists!");
					}
					else {
						songs.add(song);
						FXCollections.sort(songs);
						songList.getSelectionModel().select(song);
						addMode(false);
						save();
					}
				}
				else {
					// name or artist is empty
					setStatus("name and artist cannot be empty");
				}
			}
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
				nameField.setText(selectedSong.getName());
				artistField.setText(selectedSong.getArtist());
				albumField.setText(selectedSong.getAlbum());
				yearField.setText(selectedSong.getYear());
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
				nameField.setText(null);
				artistField.setText(null);
				albumField.setText(null);
				yearField.setText(null);
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
    		doneBtn.setDefaultButton(true);
    		cancelBtn.setCancelButton(true);
    		
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
    		doneBtn.setDefaultButton(false);
    		cancelBtn.setCancelButton(false);
    		
    		songList.setMouseTransparent( false );	//enable song list
    		songList.setFocusTraversable( true );
    		songList.setDisable(false);
    	}
    	return;
    }

	public void setSelected(Song song) {
		if(song != null) {
			selectedSong = song;
			nameLabel.setText(selectedSong.getName());
			artistLabel.setText(selectedSong.getArtist());
			albumLabel.setText(selectedSong.getAlbum());
			yearLabel.setText(selectedSong.getYear());
			nameField.setText(selectedSong.getName());
			artistField.setText(selectedSong.getArtist());
			albumField.setText(selectedSong.getAlbum());
			yearField.setText(selectedSong.getYear());
			editBtn.setDisable(false);
			delBtn.setDisable(false);
		}
	}

	public boolean isDuplicate(Song newSong) {
		for(Song song : songs) {
			if(song.equals(newSong)) {
				return true;
			}
		}

		return false;
	}

    @FXML
    void initialize() {
    	editing = false;
    	
    	nameLabel.setText(null);
		artistLabel.setText(null);
		yearLabel.setText(null);
		albumLabel.setText(null);
    	
    	songList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>(){	//generate listener for list
			@Override
			public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {	//if list selection changes
				setSelected(newValue);
				setStatus("selected: " + newValue);
			}
    	});
    	File f = new File("songs.csv");
		if (!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    	String csvFile = "songs.csv";
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";

    	try {
    		
    		br = new BufferedReader(new FileReader(csvFile));
    		while ((line = br.readLine()) != null) {	//for each song in the song list csv,
				String[] songDetails = new String[4];
				String[] temp = line.split(cvsSplitBy);	//create array of song details, separated by ","

				for(int i = 0; i < temp.length; i++) {
					songDetails[i] = temp[i];
				}

				Song song = new Song(songDetails[0], songDetails[1], songDetails[2], songDetails[3]);
    			songs.add(song);	//add song array entry 0 (song title) to songs list
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

	public void save() {
		String csvFile = "songs.csv";
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter(csvFile));


			for(int i = 0; i < songs.size(); i++) {
				Song song = songs.get(i);
				String album = song.getAlbum();
				String year = song.getYear();

				if(album == null) {
					album = "";
				}

				if(year == null) {
					year = "";
				}

				writer.write(song.getName() + "," + song.getArtist() + "," + album + "," + year + "\n" );
			}
		}
		catch ( IOException e)
		{
		}
		finally
		{
			try
			{
				if ( writer != null)
					writer.close( );
			}
			catch ( IOException e)
			{
			}
		}
	}

}
