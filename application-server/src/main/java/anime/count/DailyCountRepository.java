package anime.count;

import anime.config.AccessCountConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.repository.CrudRepository;

@ConditionalOnBean(AccessCountConfig.class)
public interface DailyCountRepository extends CrudRepository<DailyCount, String> {
}
