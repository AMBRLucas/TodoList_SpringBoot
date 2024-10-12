package com.lucas.todoSimple.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lucas.todoSimple.services.exceptions.DataBindingViolationException;
import com.lucas.todoSimple.services.exceptions.ObjectNotFoundException;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @Value("${server.error.include-exception}")
    private boolean printStackTrace;

    @SuppressWarnings("null")
    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException methodArgumentNotValidException,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
            ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error, Check 'errors' field for details."
            );
            for(FieldError fieldError: methodArgumentNotValidException.getBindingResult().getFieldErrors()){
                errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return ResponseEntity.unprocessableEntity().body(errorResponse);
        }
    
    //? Tratamento de erros inesperados
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(
        Exception exception,
        WebRequest request) {
            final String errorMessage = "Unknown error occured";
            log.error(errorMessage, exception);

            return buildErrorResponse(exception, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    
    private ResponseEntity<Object> buildErrorResponse(
        Exception exception,
        String message,
        HttpStatus httpStatus,
        WebRequest request) {
            ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
            if(this.printStackTrace){
                errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
            }

            return ResponseEntity.status(httpStatus).body(errorResponse);
        }
    
    private ResponseEntity<Object> buildErrorResponse(
        Exception exception,
        HttpStatus httpStatus,
        WebRequest request) {
            return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
        }
    
    //? Exemplo: Tentar cadastrar dois usuarios com a mesma chave
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
        DataIntegrityViolationException dataIntegrityViolationException,
        WebRequest request) {
            String errorMessage = dataIntegrityViolationException.getMostSpecificCause().getMessage();
            log.error("Failed to save entity with integrity problems: " + errorMessage, dataIntegrityViolationException);

            return  buildErrorResponse(dataIntegrityViolationException, errorMessage, HttpStatus.CONFLICT, request);
        }
    
    //? Tentar cadastrar um dado que quebre as regras da entidade por exemplo uma senha com menos ou mais caracteres que o pedido
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintViolationException(
        ConstraintViolationException constraintViolationException,
        WebRequest request) {
            log.error("Failed to validate element", constraintViolationException);

            return buildErrorResponse(constraintViolationException, HttpStatus.UNPROCESSABLE_ENTITY, request);
        }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlObjectNotFoundException(
        ObjectNotFoundException objectNotFoundException,
        WebRequest request) {
            log.error("Failed to find requested element", objectNotFoundException);

            return buildErrorResponse(objectNotFoundException, HttpStatus.NOT_FOUND, request);
        }

    @ExceptionHandler(DataBindingViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataBindingViolationException(
        DataBindingViolationException dataBindingViolationException,
        WebRequest request) {
            log.error("Failed to save entity with associated data", dataBindingViolationException);

            return buildErrorResponse(dataBindingViolationException, HttpStatus.CONFLICT, request);
        }
}
