package anime.controller;

import anime.alert.AlertManagerChain;
import anime.exception.AnimeException;
import anime.exception.DataFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class AnimeExceptionHandler {

    private final AlertManagerChain alertManagerChain;

    public AnimeExceptionHandler(AlertManagerChain alertManagerChain) {
        this.alertManagerChain = alertManagerChain;
    }

    @ExceptionHandler(AnimeException.class)
    public ResponseEntity<String> animeExceptionHandler(AnimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> noResourceFoundHandler(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<String> wrongRequestMethodHandler(Exception e) {
        return ResponseEntity.badRequest().body("Invalid request parameters");
    }

    @ExceptionHandler(DataFileException.class)
    public ResponseEntity<String> datafileExceptionHandler(DataFileException e) {
        alertManagerChain.alert(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> unhandledServerError(IllegalArgumentException e) {
        e.printStackTrace();
        alertManagerChain.alert(e.getMessage());
        return ResponseEntity.internalServerError().body("interval server error");
    }
}
