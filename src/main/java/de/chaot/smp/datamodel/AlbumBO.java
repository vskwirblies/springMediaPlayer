package de.chaot.smp.datamodel;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.chaot.smp.exceptions.SmpException;
import lombok.Data;

@Data
@Entity
public class AlbumBO extends AbstractBO {
	
	private String title = "defaultValue";
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "artist")
	private ArtistBO artist;
	
	@OneToMany
	private List<SongBO> songs;
	
	public AlbumBO() {
		if (this.songs == null) this.songs = new ArrayList<>();
	}
	
	public void addSong(SongBO songBO) throws SmpException {
		requireNonNull(songBO);
		if (songs.contains(songBO)) {
			throw new SmpException("Album" + this.title + "contains the song already.");
		}
		this.songs.add(songBO);
	}
}
