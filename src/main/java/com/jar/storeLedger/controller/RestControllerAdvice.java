package com.jar.storeLedger.controller;

import com.jar.storeLedger.exception.ApiException;
import com.jar.storeLedger.exception.ErrorCode;
import com.jar.storeLedger.exception.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class RestControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ErrorData handleAccessDeniedException(AccessDeniedException e) {
        e.printStackTrace();
        return ErrorData.builder()
                .code(ErrorCode.UNAUTHORISED)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorData handleUnknownException(HttpServletRequest req, Throwable t) {
        t.printStackTrace();
        return ErrorData.builder()
                .code(ErrorCode.INTERNAL_SERVER_ERROR)
                .message(t.getMessage())
                .build();
    }

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ErrorData handleApiException(HttpServletRequest req, ApiException e, HttpServletResponse response) {
        e.printStackTrace();
        setResponseStatus(response, e.getErrorCode());
        return ErrorData.builder()
                .code(e.getErrorCode())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorData handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletResponse response) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().stream().findAny().get();
        setResponseStatus(response, ErrorCode.BAD_REQUEST);
        return ErrorData.builder()
                .code(ErrorCode.BAD_REQUEST)
                .message(fieldError.getDefaultMessage())
                .build();
    }

    private void setResponseStatus(HttpServletResponse response, ErrorCode errorCode) {
        switch (errorCode) {
            case UNAUTHORISED:
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                break;
            case NOT_FOUND:
                response.setStatus(HttpStatus.NOT_FOUND.value());
                break;
            case BAD_REQUEST:
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                break;
            case FORBIDDEN:
                response.setStatus(HttpStatus.FORBIDDEN.value());
                break;
            case INTERNAL_SERVER_ERROR:
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            default:
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
