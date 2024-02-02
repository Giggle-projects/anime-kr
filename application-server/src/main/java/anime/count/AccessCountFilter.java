package anime.count;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AccessCountFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessCountFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        countAccessLog(request);
        filterChain.doFilter(request, response);
    }

    private static void countAccessLog(HttpServletRequest request) {
        var ip = request.getHeader("X-FORWARDED-FOR");
        if(ip != null) {
            LOGGER.info("request ip : " + ip);
            AccessCount.increase();
        }
    }
}
