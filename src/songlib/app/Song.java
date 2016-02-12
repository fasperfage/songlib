/*
 * Written by Calvin Lee and Bartosz Kidacki
 */

package songlib.app;

public class Song implements Comparable<Song> {
   private String name;
   private String artist;
   private String album;
   private String year;

   public Song(String name, String artist, String album, String year) {
      this.name = name;
      this.artist = artist;
      this.album = album;
      this.year = year;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getArtist() {
      return artist;
   }

   public void setArtist(String artist) {
      this.artist = artist;
   }

   public String getAlbum() {
      return album;
   }

   public void setAlbum(String album) {
      this.album = album;
   }

   public String getYear() {
      return year;
   }

   public void setYear(String year) {
      this.year = year;
   }

   @Override
   public int compareTo(Song o) {
      return name.toLowerCase().compareTo(o.name.toLowerCase());
   }

   @Override
   public boolean equals(Object o) {
      if(this == o) {
         return true;
      }
      if(o == null || getClass() != o.getClass()) {
         return false;
      }

      Song song = (Song) o;
      return name.equals(song.name) && artist.equals(song.artist);
   }

   @Override
   public String toString() {
      return name;
   }
}

