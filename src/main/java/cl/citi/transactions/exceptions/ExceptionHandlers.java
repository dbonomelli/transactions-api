package cl.citi.transactions.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionModel> handleBusinessExceptions(BusinessException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(e.getCode().equals("404")){
            status = HttpStatus.NOT_FOUND;
        }
        return handleError(e.getCode(), e.getMessage(), status);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionModel> handleNoResourceFoundException(NoResourceFoundException e) {
        return handleError("404", e.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ExceptionModel> handleError(String code, String message, HttpStatus status) {
        return handleError(code, message, status, null);
    }
    private ResponseEntity<ExceptionModel> handleError(String code, String message, HttpStatus status,
                                                       List<Object> subErrors) {
        ExceptionModel model = new ExceptionModel(code, message, subErrors);
        return new ResponseEntity<>(model, status);
    }
}
