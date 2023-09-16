package com.demo.skyros.exception;


import com.demo.skyros.vo.AppResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Setter
@Getter
@Log4j2
@ControllerAdvice
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        AppResponse appResponse = new AppResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(appResponse, HttpStatus.OK);
    }

    @ExceptionHandler({InvalidLimitException.class})
    public ResponseEntity<Object> handleEmptyResultException(Exception ex, WebRequest request) {
        AppResponse appResponse = new AppResponse(new Date(), HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(appResponse, HttpStatus.OK);
    }

}
