package anime;

import anime.alert.AlertChain;
import anime.exception.AnimeException;
import anime.exception.DataFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class AnimeExceptionHandler {

    private final AlertChain alertChain;

    public AnimeExceptionHandler(AlertChain alertChain) {
        this.alertChain = alertChain;
    }

    @ExceptionHandler(AnimeException.class)
    public ResponseEntity<String> animeExceptionHandler(AnimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DataFileException.class)
    public ResponseEntity<String> datafileExceptionHandler(DataFileException e) {
        alertChain.alert(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> noResourceFoundHandler(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> wrongRequestMethodHandler(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> unhandledServerError(IllegalArgumentException e) {
        // TODO :: SLACK UTILS SHOULD BE BEAN, ABLE TO BE TURNED OFF BY PROFILE
        alertChain.alert(e.getMessage());
        return ResponseEntity.internalServerError().body("interval server error");
    }
}
