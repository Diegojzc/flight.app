package com.tokioschool.flight.app.core.advice;

import com.fasterxml.classmate.Annotations;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations= RestController.class)
public class ApiExceptionHandler{

}
