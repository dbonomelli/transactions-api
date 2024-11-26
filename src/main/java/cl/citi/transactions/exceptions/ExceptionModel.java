package cl.citi.transactions.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class ExceptionModel {
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<Object> subErrors;

    public ExceptionModel(String code, String message, List<Object> subErrors) {
        this.code = code;
        this.message = message;
        this.subErrors = subErrors;
    }

    @Getter
    public static class FieldSubErrorModel {

        private final String field;
        private final Object value;
        private final String type;

        public FieldSubErrorModel(FieldError error) {
            field = String.join(".", error.getObjectName(), error.getField());
            value = error.getRejectedValue();
            type = error.getDefaultMessage();
        }

    }
}
