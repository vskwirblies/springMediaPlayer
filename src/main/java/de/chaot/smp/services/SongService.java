package de.chaot.smp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import de.chaot.smp.datamodel.SongBO;
import de.chaot.smp.repositories.SongRepository;

@Service
public class SongService {

	@Autowired
	private SongRepository songRepository;
	
	public List<SongBO> getPageContent(int index) {
		return songRepository.findAll(PageRequest.of(index, 10)).getContent();
	}

	public int getTotalPages() {
		return songRepository.findAll(PageRequest.of(0, 10)).getTotalPages();
	}
	
	public SongBO findById(Long id) {
		if (songRepository.findById(id).isPresent()) {
			return songRepository.findById(id).get();
		}
		return null;
	}
	
	public void save(SongBO songBO) {
		songRepository.save(songBO);
	}
	
}
