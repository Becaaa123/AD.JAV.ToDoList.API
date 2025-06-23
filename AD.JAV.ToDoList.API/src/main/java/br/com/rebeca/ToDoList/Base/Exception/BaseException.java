package br.com.rebeca.ToDoList.Base.Exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final HttpStatus httpStatus;
    private final String message;
    private final Throwable throwable;


    public BaseException(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.throwable = null;
    }

    public BaseException(final String message) {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
        this.throwable = null;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getMessage() {
        return this.message;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }
}
