package de.chaot.smp.datamodel;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import de.chaot.smp.exceptions.SmpException;
import lombok.Data;

@Data
@Entity
public class PlaylistBO extends AbstractBO {
	
	@OneToMany
	private List<SongBO> songs;
	
	public PlaylistBO() {
		if (this.songs == null) this.songs = new ArrayList<>();
	}
	
	public void addSong(SongBO songBO) throws SmpException {
//		requireNonNull(songBO);
		this.songs.add(songBO);
	}
}
