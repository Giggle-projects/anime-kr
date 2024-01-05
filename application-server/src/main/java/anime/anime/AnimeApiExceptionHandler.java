package anime.anime;

import anime.alert.AlertManagerChain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@ControllerAdvice
public class AnimeApiExceptionHandler {

    private final AlertManagerChain alertManagerChain;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noSuchElementHandler(NoSuchElementException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<String> wrongRequestMethodHandler() {
        return ResponseEntity.badRequest().body("Invalid request parameters");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> unhandledServerError(IllegalArgumentException e) {
        e.printStackTrace();
        alertManagerChain.alert(e.getMessage());
        return ResponseEntity.internalServerError().body("interval server error");
    }
}
