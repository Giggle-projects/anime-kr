package anime.accessCount;

import org.springframework.data.repository.CrudRepository;

public interface AccessCountRepository extends CrudRepository<AccessCountRecord, String> {
}

