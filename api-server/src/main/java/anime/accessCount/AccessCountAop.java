package anime.accessCount;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AccessCountAop {

    @Pointcut("@within(anime.accessCount.Access) || @annotation(anime.accessCount.Access)")
    public void accessPointcut() {
    }

    @AfterReturning("accessPointcut()")
    public void afterAccess() {
        AccessCount.increase();
    }
}
