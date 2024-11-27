package cl.citi.transactions.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlers {

    /**
     * Creates a custom error for a business error
     *
     * @param e
     * @return handleError
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionModel> handleBusinessExceptions(BusinessException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(e.getCode().equals("404")){
            status = HttpStatus.NOT_FOUND;
        }
        return handleError(e.getCode(), e.getMessage(), status);
    }

    /**
     * If a static resource is not found, returns a controlled error
     *
     * @param e
     * @return handleError
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionModel> handleNoResourceFoundException(NoResourceFoundException e) {
        return handleError("404", e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * If an error comes with the body, a bad request is sent
     *
     * @param e
     * @return handleError
     */
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ExceptionModel> handleNoResourceFoundException(HttpClientErrorException.BadRequest e) {
        return handleError("400", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * If an error comes with the body, a bad request is sent
     *
     * @param e
     * @return handleError
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionModel> handleNoResourceFoundException(HttpMessageNotReadableException e) {
        return handleError("400", e.getMessage(), HttpStatus.BAD_REQUEST);
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
