package de.chaot.smp.datamodel;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import de.chaot.smp.exceptions.SmpException;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ArtistBO extends AbstractBO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "name")
	private String name = "defaultValue";

	@OneToMany
	@JoinColumn(name = "albums")
	private List<AlbumBO> albums;
	
	public List<AlbumBO> getAlbums() {
		return albums;
	}

	public void setAlbums(List<AlbumBO> albums) {
		this.albums = albums;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Constructor
	 * @throws SmpException 
	 */
	public ArtistBO() {
		if (this.albums == null) this.albums = new ArrayList<>();
	}
	
//	public ArtistBO(String name) {
//		this.name = name;
//	}
	
	public void addAlbum(AlbumBO album) throws SmpException {
		requireNonNull(album);
		if (albums.contains(album)) {
			throw new SmpException("Artist" + this.name + " contains the album already.");
		}
		this.albums.add(album);
	}
	
	public boolean hasAlbum(AlbumBO albumBO) {
		return this.albums.contains(albumBO);
	}
}
