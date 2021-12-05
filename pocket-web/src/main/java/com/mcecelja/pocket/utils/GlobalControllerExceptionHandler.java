package com.mcecelja.pocket.utils;

import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler({PocketException.class})
	public ResponseEntity<Object> handleForumException(
			PocketException ex) {

		String response = null;
		try {
			response = ResponseMessage.packageAndJsoniseError(ex.getError());
		} catch (PocketException e) {

			e.printStackTrace();
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@ExceptionHandler({MethodArgumentNotValidException.class, NumberFormatException.class, MissingServletRequestParameterException.class})
	public ResponseEntity<Object> handleBadRequestExceptions(Exception ex) {
		String response = null;
		ex.printStackTrace();
		try {
			response = ResponseMessage.packageAndJsoniseError(PocketError.BAD_REQUEST);
		} catch (PocketException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleException(
			Exception ex) {

		String response = null;
		ex.printStackTrace();
		try {
			response = ResponseMessage.packageAndJsoniseError(PocketError.UNRECOGNIZED_EXCEPTION);
		} catch (PocketException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
