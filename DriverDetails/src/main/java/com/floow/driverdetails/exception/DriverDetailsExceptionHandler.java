package com.floow.driverdetails.exception;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DriverDetailsExceptionHandler {
	Logger logger = LoggerFactory.getLogger(DriverDetailsExceptionHandler.class);
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<DriverErrorDetails> invalidDriverDetails(InvalidRequestException exception) {
		DriverErrorDetails driverErrorDetails = new DriverErrorDetails(HttpStatus.BAD_REQUEST, "Invalid Date Details",
				exception.getMessage());
		logger.error(exception.getMessage());
		return new ResponseEntity<DriverErrorDetails>(driverErrorDetails, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<DriverErrorDetails> incorrectDriverDetails(MethodArgumentNotValidException exception) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : exception.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : exception.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		DriverErrorDetails driverErrorDetails = new DriverErrorDetails(HttpStatus.BAD_REQUEST, "Invalid Driver Details",
				errors);
		logger.error(exception.getMessage());
		return new ResponseEntity<DriverErrorDetails>(driverErrorDetails, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<DriverErrorDetails> serverError(Exception exception) {
		DriverErrorDetails driverErrorDetails = new DriverErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, "API Error",
				"Internal server error");
		logger.error(exception.getMessage());
		return new ResponseEntity<DriverErrorDetails>(driverErrorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
