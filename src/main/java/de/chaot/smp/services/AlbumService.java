package de.chaot.smp.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import de.chaot.smp.datamodel.AlbumBO;
import de.chaot.smp.datamodel.ArtistBO;
import de.chaot.smp.datamodel.SongBO;
import de.chaot.smp.repositories.AlbumRepository;

@Service
public class AlbumService {

	private Logger logger = LoggerFactory.getLogger(AlbumService.class);
	
	@Autowired
	private AlbumRepository albumRepository;
	
	public List<AlbumBO> getPageContent(int pageIndex) {
		return albumRepository.findAll(PageRequest.of(pageIndex, 10)).getContent();
	}

	public int getLastPageIndex() {
		return albumRepository.findAll(PageRequest.of(0, 10)).getTotalPages();
	}
	
	public AlbumBO findById(Long id) {
		return albumRepository.findById(id).get();
	}
	
	public List<AlbumBO> findByArtistAndTitle(ArtistBO artistBO, String title) {
		return albumRepository.findByArtistAndTitle(artistBO, title);
	}
	
	public void save(AlbumBO albumBO) {
		albumRepository.save(albumBO);
	}

	public void sortSongsByTrackNumber(AlbumBO albumBO) {
		Collections.sort(albumBO.getSongs(), new Comparator<SongBO>() {
		    @Override
		    public int compare(SongBO song1, SongBO song2) {
		    	if (song1.getTrackNumber() < song2.getTrackNumber()) {
		    		logger.trace("song 1 with trackno "+ song1.getTrackNumber() + " is smaller then song 2 with trackno " + song2.getTrackNumber());
		    		return -1;
		    	}
		    	else if (song1.getTrackNumber() == song2.getTrackNumber()) {
		    		logger.trace("song 1 with trackno "+ song1.getTrackNumber() + " is equal to song 2 with trackno " + song2.getTrackNumber());
		    		return 0;
		    	}
		    	else {
		    		logger.trace("song 1 with trackno "+ song1.getTrackNumber() + " is greater then song 2 with trackno " + song2.getTrackNumber());
		    		return 1;
		    	}
		    }
		});
	}

	public int getTotalPages() {
		return albumRepository.findAll(PageRequest.of(0, 10)).getTotalPages();
	}
	
}
