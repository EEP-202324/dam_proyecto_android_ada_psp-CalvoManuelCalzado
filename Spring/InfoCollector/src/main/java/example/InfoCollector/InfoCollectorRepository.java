package example.InfoCollector;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface InfoCollectorRepository extends CrudRepository<InfoCollector, Long>, PagingAndSortingRepository<InfoCollector, Long> {
}
