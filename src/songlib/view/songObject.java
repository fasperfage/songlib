package songlib.view;

public class songObject 
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
	
	public String toString(){
		return title;
	}
}
