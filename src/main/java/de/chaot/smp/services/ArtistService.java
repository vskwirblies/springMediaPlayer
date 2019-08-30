package de.chaot.smp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import de.chaot.smp.datamodel.ArtistBO;
import de.chaot.smp.repositories.ArtistRepository;

@Service
public class ArtistService {

	@Autowired
	private ArtistRepository artistRepository;
	
	public List<ArtistBO> getPageContent(int pageIndex) {
		return artistRepository.findAll(PageRequest.of(pageIndex, 10)).getContent();
	}

	public ArtistBO findById(Long id) {
		return artistRepository.findById(id).get();
	}
	
	public List<ArtistBO> findByName(String name) {
		return artistRepository.findByName(name);
	}
	
	public void save(ArtistBO artistBO) {
		artistRepository.save(artistBO);
	}

	public int getTotalPages() {
		return artistRepository.findAll(PageRequest.of(0, 10)).getTotalPages();
	}
	
}
