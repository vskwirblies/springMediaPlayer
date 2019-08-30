package de.chaot.smp.datamodel;

//import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

//import com.mpatric.mp3agic.ID3v1;
//import com.mpatric.mp3agic.ID3v2;
//import com.mpatric.mp3agic.InvalidDataException;
//import com.mpatric.mp3agic.Mp3File;
//import com.mpatric.mp3agic.UnsupportedTagException;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SongBO extends AbstractBO implements Serializable {

	/*
	 * Fields
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String filepath = "defaultValue";
	
	@OneToOne
	private ArtistBO artist;
	
	@ManyToOne
	private AlbumBO album;
	
	private String title = "defaultValue";
	
	private int trackNumber = 0;
	
	/*
	 * Getters and setters
	 */

	public int getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
	}

	public AlbumBO getAlbum() {
		return album;
	}

	public void setAlbum(AlbumBO album) {
		this.album = album;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArtistBO getArtist() {
		return artist;
	}

	public void setArtist(ArtistBO artist) {
		this.artist = artist;
	}
	
	public SongBO() {
		this.artist = new ArtistBO();
		this.artist.setName("defaultValue");
	}
	
	/**
	 *	Constructor 
	 */
//	@Deprecated
//	public SongBO(String filepath) {
//		this.setFilepath(filepath);
//		this.artist = new ArtistBO();
//		this.artist.setName("defaultValue");
//		try {
//			Mp3File mp3file = new Mp3File(filepath); //Player.folderpath
//			if (mp3file.hasId3v1Tag()) {
//				ID3v1 id3v1Tag = mp3file.getId3v1Tag();
//				this.title = id3v1Tag.getTitle();
//				this.artist.setName(id3v1Tag.getArtist());
//			}
//			if (mp3file.hasId3v2Tag()) {
//				ID3v2 id3v2Tag = mp3file.getId3v2Tag();
//				this.title = id3v2Tag.getTitle();
//				this.artist.setName(id3v2Tag.getArtist());
//			}
//			
//		} catch (UnsupportedTagException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidDataException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
