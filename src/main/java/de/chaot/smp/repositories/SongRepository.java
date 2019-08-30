package de.chaot.smp.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import de.chaot.smp.datamodel.SongBO;

@Repository
public interface SongRepository extends PagingAndSortingRepository<SongBO, Long>{
	
	Page<SongBO> findAll(Pageable pageable);
	
	Optional<SongBO> findById(Long id);
}
