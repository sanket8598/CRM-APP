package ai.rnt.crm.exception.handler;

import static ai.rnt.crm.constants.MessageConstants.ACCESS_DENIED;
import static ai.rnt.crm.constants.MessageConstants.BAD_CREDENTIALS;
import static ai.rnt.crm.constants.MessageConstants.DUPLICATE_DATA_ERROR_MSG;
import static ai.rnt.crm.constants.MessageConstants.ERROR_MSG;
import static ai.rnt.crm.constants.MessageConstants.TOKEN_EXPIRED;
import static ai.rnt.crm.util.HttpUtils.getURL;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.DTOConvertionException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.payloads.ApiError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <i> This file contain exception configurations of REST API.<br>
 * </i><br>
 * <b>List Of Handled Exceptions</b> <blockquote> 1. MethodArgumentNotValid<br>
 * 2. MissingServletRequestParameterException<br>
 * 3. HttpRequestMethodNotSupportedException<br>
 * 4. MissingPathVariableException<br>
 * 5. CRMException<br>
 * </blockquote>
 *
 * @author Sanket Wakankar
 * @since 19-08-2023
 * @version 1.0
 * 
 */
@ControllerAdvice
@ResponseBody
@RequiredArgsConstructor
@Component
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @since version 1.0
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		ApiError apiError = new ApiError(BAD_REQUEST, ex.getLocalizedMessage(), Arrays.asList(error));
		log.error("handleMissingServletRequestParameter api error: {}", apiError);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since version 1.0
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getRequestPartName() + " parameter is missing";
		ApiError apiError = new ApiError(BAD_REQUEST, ex.getLocalizedMessage(), Arrays.asList(error));
		log.error("handleMissingServletRequestParameter api error: {}", apiError);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	/**
	 * {@inheritDoc} This method will handle exception which is occurred because
	 * wrong request method type
	 * 
	 * @since version 1.0
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(BAD_REQUEST, ex.getLocalizedMessage(),
				Arrays.asList("Request method not supported"));
		log.error("handleHttpRequestMethodNotSupported api error: {}", apiError);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	/**
	 * {@inheritDoc} This method will handle exception which is occurred because
	 * required path/URL variable not found in requested URL
	 * 
	 * @since version 1.0
	 */
	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(BAD_REQUEST, ex.getLocalizedMessage(),
				Arrays.asList("Missing required path variable"));
		log.error("handleMissingPathVariable api error: {}", apiError);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("error occured while invalid request body...{}", ex);
		return new ResponseEntity<>(new ApiError(false, ex.getMessage()), NOT_ACCEPTABLE);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		exc.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			errors.add(fieldName + ": " + error.getDefaultMessage());
		});
		log.error("handleMethodArgumentNotValid api error: {}", exc.getLocalizedMessage());
		return new ResponseEntity<>(new ApiError(BAD_REQUEST, !errors.isEmpty() ? errors.get(0) : null, errors),
				BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exc) {
		log.info("handling ResourceNotFoundException....{}", exc.getLocalizedMessage());
		return new ResponseEntity<>(new ApiError(false, exc.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidKeyException.class)
	protected ResponseEntity<ApiError> handleInvalidKeyException(InvalidKeyException exc) {
		log.error("handle InvalidKeyException handler: {}", exc.getMessage());
		return new ResponseEntity<>(new ApiError(false, TOKEN_EXPIRED), UNAUTHORIZED);
	}

	@ExceptionHandler(CRMException.class)
	protected ResponseEntity<ApiError> handleCRMException(CRMException exc) throws Throwable {
		log.info("handling CRM Exception....{}", exc.getLocalizedMessage());
		if (exc.getException() instanceof BadCredentialsException)
			return new ResponseEntity<>(new ApiError(false, BAD_CREDENTIALS), BAD_REQUEST);
		else if (exc.getException() instanceof InvalidKeyException)
			return new ResponseEntity<>(new ApiError(false, TOKEN_EXPIRED), UNAUTHORIZED);
		else if (exc.getException() instanceof NullPointerException)
			return new ResponseEntity<>(new ApiError(false, ERROR_MSG), INTERNAL_SERVER_ERROR);
		else if (exc.getException() instanceof org.hibernate.exception.ConstraintViolationException)
			return new ResponseEntity<>(new ApiError(false, DUPLICATE_DATA_ERROR_MSG), BAD_REQUEST);
		else if (exc.getException() instanceof org.springframework.dao.DataIntegrityViolationException)
			return new ResponseEntity<>(new ApiError(false, getRootCause(exc).getMessage()), BAD_REQUEST);
		return new ResponseEntity<>(new ApiError(false, getRootCause(exc).getMessage()), INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exc, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.info("handling handler Not Found Exception....{}", exc.getLocalizedMessage());
		ApiError apiError = new ApiError(false,
				"No URL Found In The CRM with " + getURL(((ServletRequestAttributes) request).getRequest()) + ".");
		return handleExceptionInternal(exc, apiError, headers, NOT_FOUND, request);
	}

	/**
	 * This method will handle exception which is occurred because request body
	 * contain invalid data
	 * 
	 * @param ex
	 * @param request
	 * @return Create a {@code ResponseEntity} with status code 400
	 *         <i>(BAD_REQUEST)</i> and exception details
	 * @since version 1.0
	 */
	@ExceptionHandler({ UnsupportedOperationException.class, DTOConvertionException.class })
	protected ResponseEntity<Object> handleInvalidRequestBodyException(UnsupportedOperationException ex,
			WebRequest request) {
		ApiError apiError = new ApiError(BAD_REQUEST, ex.getLocalizedMessage(),
				Arrays.asList(ex.getMessage(), "Invalid Request Body"));
		log.error("handleInvalidRequestBodyException api error: {}", apiError);
		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
	}

	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException exc) {
		log.error("handle AccessDenied api error handler: {}", exc.getMessage());
		return new ResponseEntity<>(new ApiError(false, ACCESS_DENIED + "" + exc.getMessage()),
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException exc) {
		List<String> errors = new ArrayList<>();
		exc.getConstraintViolations().forEach(e -> {
			String fieldName = e.getPropertyPath().toString();
			errors.add(fieldName + ": " + e.getMessage());
		});
		log.error("handle Contraint not valid api error: {}", exc.getMessage());
		return new ResponseEntity<>(
				new ApiError(BAD_REQUEST, !errors.isEmpty() ? errors.get(0).split(":")[1] : null, errors), BAD_REQUEST);
	}

	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<ApiError> handleSignatureException(SignatureException ex) {
		return new ResponseEntity<>(new ApiError(false, "JWT Signature Is Not Valid!!"), UNAUTHORIZED);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ApiError> handleSignatureException(ExpiredJwtException ex) {
		return new ResponseEntity<>(new ApiError(false, TOKEN_EXPIRED), UNAUTHORIZED);
	}

	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<ApiError> handleMalformedJwtException(MalformedJwtException ex) {
		return new ResponseEntity<>(new ApiError(false, "JWT Token is Not Valid!!"), FORBIDDEN);
	}

	@ExceptionHandler({ SQLException.class })
	protected ResponseEntity<ApiError> handleDBException(Exception exc) {
		log.error("handle DB connection exception and api error handler: {}", exc.getMessage());
		return new ResponseEntity<>(new ApiError(false, getRootCause(exc).getMessage()), TOO_MANY_REQUESTS);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiError> handleAllException(Exception exc) {
		log.error("inside All exception and api error handler: {}{}", exc.getMessage(), exc);
		return new ResponseEntity<>(new ApiError(false, getRootCause(exc).getMessage()), INTERNAL_SERVER_ERROR);
	}

}
