package com.example.productcatalogapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<ErrorResponse.FieldErrorDetail> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> new ErrorResponse.FieldErrorDetail(err.getField(), err.getDefaultMessage()))
				.collect(Collectors.toList());

		ErrorResponse errorResponse = new ErrorResponse(status.value(), status.toString(), "Validation failed",
				fieldErrors, request.getDescription(false).replace("uri=", ""));

		return new ResponseEntity<>(errorResponse, headers, status);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex,
			HttpServletRequest request) {

		ErrorResponse err = new ErrorResponse(ex.getStatusCode().value(), ex.getStatusCode().toString(), ex.getReason(),
				null, request.getRequestURI());
		return new ResponseEntity<>(err, ex.getStatusCode());
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {

		ErrorResponse err = new ErrorResponse(HttpStatusCode.valueOf(404).value(),
				HttpStatusCode.valueOf(404).toString(), ex.getMessage(), null, request.getRequestURI());
		return new ResponseEntity<>(err, HttpStatusCode.valueOf(404));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex,
			HttpServletRequest request) {

		ErrorResponse err = new ErrorResponse(HttpStatusCode.valueOf(400).value(),
				HttpStatusCode.valueOf(400).toString(), ex.getMessage(), null, request.getRequestURI());
		return new ResponseEntity<>(err, HttpStatusCode.valueOf(400));
	}
}
