package anime.shutdown;

import anime.config.AccessCountConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.lang.annotation.*;

@ConditionalOnBean(AccessCountConfig.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ShutDown {
}
