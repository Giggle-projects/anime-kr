package anime.aop;

import anime.dao.UserAccessCount;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAccessCountAop {

    @Pointcut("@within(anime.aop.UserAccess) || @annotation(anime.aop.UserAccess)")
    public void userAccessPointcut() {
    }

    @AfterReturning("userAccessPointcut()")
    public void afterUserAccess() {
        UserAccessCount.increase();
    }
}
