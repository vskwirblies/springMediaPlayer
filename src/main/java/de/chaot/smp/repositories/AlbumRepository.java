package de.chaot.smp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import de.chaot.smp.datamodel.AlbumBO;
import de.chaot.smp.datamodel.ArtistBO;

@Repository
public interface AlbumRepository extends PagingAndSortingRepository<AlbumBO, Long>{

	Page<AlbumBO> findAll(Pageable pageable);
	
	Optional<AlbumBO> findById(Long id);
	
	List<AlbumBO> findByArtistAndTitle(ArtistBO artistBO, String title);
	
}
