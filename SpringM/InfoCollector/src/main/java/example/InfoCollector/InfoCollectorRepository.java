package example.InfoCollector;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface InfoCollectorRepository extends CrudRepository<InfoCollector, Long>, PagingAndSortingRepository<InfoCollector, Long> {
	InfoCollector findByIdAndOwner(Long id, String owner);
	   Page<InfoCollector> findByOwner(String owner, PageRequest pageRequest);
}
