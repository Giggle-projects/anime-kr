package anime.count;

import anime.config.AccessCountConfig;
import jakarta.persistence.LockModeType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

@ConditionalOnBean(AccessCountConfig.class)
public interface DailyCountRepository extends JpaRepository<DailyCount, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<DailyCount> findById(Long id);
}
