package taskApp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import taskApp.model.ErrorResponce;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponce> handleNotFound(NoSuchElementException ex){
       logger.warn(ex.getMessage());
       ErrorResponce errorResponce = new ErrorResponce(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(),"Not Found",ex.getMessage());
       return new ResponseEntity<>(errorResponce,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponce> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.warn(ex.getMessage());
        ErrorResponce errorResponce = new ErrorResponce(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Validation error",ex.getMessage());
        return new ResponseEntity<>(errorResponce, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponce> handleOtherExceptions(Exception ex){
        logger.warn(ex.getMessage());
        ErrorResponce errorResponce = new ErrorResponce(LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error",ex.getMessage());
        return new ResponseEntity<>(errorResponce, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
