package com.edu.web.spacecatsmarket.web;


import com.edu.web.spacecatsmarket.catalog.application.exceptions.CategoryAlreadyExistException;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.CategoryNotFoundException;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.ProductAlreadyExistException;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.ProductNotFoundException;
import com.edu.web.spacecatsmarket.web.exception.ParamsViolationDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static com.edu.web.spacecatsmarket.utils.ProblemDetailsUtils.getValidationErrorsProblemDetail;
import static java.net.URI.create;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    ProblemDetail  productNotFoundException(ProductNotFoundException e) {
        log.warn("Product not found exception");
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(NOT_FOUND, e.getMessage());
        problemDetail.setType(create("product-not-found"));
        problemDetail.setTitle("Product not found");
        return problemDetail;
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    ProblemDetail productAlreadyExistException(ProductAlreadyExistException e) {
        log.warn("Product already exist exception");
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(CONFLICT, e.getMessage());
        problemDetail.setType(create("product-already-exist"));
        problemDetail.setTitle("Product already exist");
        return problemDetail;
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    ProblemDetail categoryNotFoundException(CategoryNotFoundException e) {
        log.warn("Category not found exception");
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(NOT_FOUND, e.getMessage());
        problemDetail.setType(create("category-not-found"));
        problemDetail.setTitle("Category not found");
        return problemDetail;
    }

    @ExceptionHandler(CategoryAlreadyExistException.class)
    ProblemDetail categoryAlreadyExistException(CategoryAlreadyExistException e) {
        log.warn("Category already exist exception");
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(CONFLICT, e.getMessage());
        problemDetail.setType(create("category-already-exist"));
        problemDetail.setTitle("Category already exist");
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<ParamsViolationDetails> validationResponse = errors.stream()
                .map(err -> ParamsViolationDetails.builder()
                        .fieldName(err.getField())
                        .reason(err.getDefaultMessage()).build())
                        .toList();

        log.warn("Input params validation failed");
        return ResponseEntity.badRequest().body(getValidationErrorsProblemDetail(validationResponse));
    }
}
