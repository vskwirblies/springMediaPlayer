package de.chaot.smp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import de.chaot.smp.datamodel.ArtistBO;

@Repository
public interface ArtistRepository extends PagingAndSortingRepository<ArtistBO, Long>{

	Page<ArtistBO> findAll(Pageable pageable);
	
	Optional<ArtistBO> findById(Long id);
	
	List<ArtistBO> findByName(String name);
}
