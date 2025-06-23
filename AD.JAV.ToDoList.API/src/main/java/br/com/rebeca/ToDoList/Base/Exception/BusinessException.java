package br.com.rebeca.ToDoList.Base.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 4124475171582627964L;

    private HttpStatus httpStatus = null;
    private String message = null;
    private Throwable throwable = null;

    public BusinessException(final String message, final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.throwable = null;
    }
}