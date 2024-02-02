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
        var ip = request.getHeader("X-FORWARDED-FOR");
        LOGGER.info("request ip : " + ip);

        AccessCount.increase();
        filterChain.doFilter(request, response);
    }
}
