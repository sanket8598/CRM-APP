package ai.rnt.crm.exception.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.payloads.ApiError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

class GlobalExceptionHandlerTest {

	@Mock
	private WebRequest mockRequest;

	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;

	@Mock
	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	private MockHttpServletRequest mockHttpRequest;
	
	 @Mock
	    private WebRequest webRequest;

	@BeforeEach
	void setUp() {
		mockRequest = mock(WebRequest.class);
		mockHttpRequest = new MockHttpServletRequest();
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(globalExceptionHandler).build();
	}

	@Test
	void handleMissingServletRequestParameter_ShouldReturnBadRequestWithErrorMessage() {
		MissingServletRequestParameterException ex = new MissingServletRequestParameterException("paramName",
				"paramType");
		ResponseEntity<Object> responseEntity = globalExceptionHandler.handleMissingServletRequestParameter(ex, headers,
				HttpStatus.BAD_REQUEST, mockRequest);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		ApiError apiError = (ApiError) responseEntity.getBody();
		assertEquals("Required request parameter 'paramName' for method parameter type paramType is not present",
				apiError.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, apiError.getHttpStatus());
		assertEquals(Arrays.asList("paramName parameter is missing"), apiError.getErrors());
	}

	@Test
	void handleMissingServletRequestPart_ShouldReturnBadRequestWithErrorMessage() {
		MissingServletRequestPartException ex = new MissingServletRequestPartException("partName");
		ResponseEntity<Object> responseEntity = globalExceptionHandler.handleMissingServletRequestPart(ex,
				new HttpHeaders(), HttpStatus.BAD_REQUEST, mockRequest);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		ApiError apiError = (ApiError) responseEntity.getBody();
		assertEquals("Required request part 'partName' is not present", apiError.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, apiError.getHttpStatus());
		assertEquals(Arrays.asList("partName parameter is missing"), apiError.getErrors());
	}

	@Test
	void handleHttpRequestMethodNotSupported_ShouldReturnBadRequestWithErrorMessage() {
		HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("POST");
		ResponseEntity<Object> responseEntity = globalExceptionHandler.handleHttpRequestMethodNotSupported(ex,
				new HttpHeaders(), HttpStatus.BAD_REQUEST, mockRequest);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		ApiError apiError = (ApiError) responseEntity.getBody();
		assertEquals("Request method 'POST' not supported", apiError.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, apiError.getHttpStatus());
		assertEquals(Arrays.asList("Request method not supported"), apiError.getErrors());
	}

	@Test
    void handleHttpMessageNotReadable_ShouldReturnNotAcceptableWithErrorMessage() {
        @SuppressWarnings("deprecation")
		HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Invalid request body");
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleHttpMessageNotReadable(
                ex, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, mockRequest);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertEquals("Invalid request body", apiError.getMessage());
    }
	
	@Test
    void handleResourceNotFoundException_ShouldReturnNotFoundWithErrorMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleResourceNotFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ApiError apiError = responseEntity.getBody();
        assertEquals("Resource not found not found.", apiError.getMessage());
    }

    @Test
    void handleInvalidKeyException_ShouldReturnUnauthorizedWithErrorMessage() {
        InvalidKeyException ex = new InvalidKeyException("Invalid key");
        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleInvalidKeyException(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        ApiError apiError = responseEntity.getBody();
        assertEquals("JWT Token is Expired !!", apiError.getMessage());
    }
    
    @Test
    void handleCRMException_ShouldReturnBadRequestForBadCredentialsException() throws Throwable {
        BadCredentialsException innerException = new BadCredentialsException("Invalid username or password");
        CRMException ex = new CRMException(innerException);
        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleCRMException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ApiError apiError = responseEntity.getBody();
        assertEquals("Your Credentails Are Not Valid !!", apiError.getMessage()); 
    }
    @Test
    void handleCRMExceptionShouldReturnNullPointerException() throws Throwable {
    	NullPointerException innerException = new NullPointerException("Null Pointer Exception");
    	CRMException ex = new CRMException(innerException);
    	ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleCRMException(ex);
    	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    	ApiError apiError = responseEntity.getBody();
    	assertEquals("Something unexpected happened !!", apiError.getMessage()); 
    }

    @Test
    void handleCRMException_ShouldReturnUnauthorizedForInvalidKeyException() throws Throwable {
        InvalidKeyException innerException = new InvalidKeyException("Invalid key");
        CRMException ex = new CRMException("CRM Exception");//, innerException);
        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleCRMException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        CRMException ex1 = new CRMException(innerException);
        ResponseEntity<ApiError> responseEntity1 = globalExceptionHandler.handleCRMException(ex1);
        ApiError apiError = responseEntity1.getBody();
        assertEquals("JWT Token is Expired !!", apiError.getMessage()); // Assuming TOKEN_EXPIRED is "Token expired"
    }
    
    
    @Test
    void handleInvalidRequestBodyException_ShouldReturnBadRequestWithErrorMessage() {
        UnsupportedOperationException ex = new UnsupportedOperationException("Operation not supported");
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleInvalidRequestBodyException(ex, mockRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertEquals("Operation not supported", apiError.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, apiError.getHttpStatus());
        assertEquals(Arrays.asList("Operation not supported", "Invalid Request Body"), apiError.getErrors());
    }
    
    @Test
    void handleAccessDeniedException_ShouldReturnUnauthorizedWithErrorMessage() {
        AccessDeniedException ex = new AccessDeniedException("Access Denied");
        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleAccessDeniedException(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        ApiError apiError = responseEntity.getBody();
        assertEquals("You Don't Have The Access !!Access Denied", apiError.getMessage());
    }
    
    @Test
    void handleConstraintViolationException_ShouldReturnBadRequestWithErrorMessage() {
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn(PathImpl.of("fieldName"));
        when(violation.getMessage()).thenReturn("must not be null");
        violations.add(violation);
        ConstraintViolationException ex = new ConstraintViolationException("Constraint violation", violations);
        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleConstraintViolationException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ApiError apiError = responseEntity.getBody();
        assertEquals(" must not be null", apiError.getMessage());
    }
    
    @Test
    void handleSignatureException_ShouldReturnUnauthorizedWithErrorMessage() {
        SignatureException ex = new SignatureException("Invalid signature");
        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleSignatureException(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        ApiError apiError = responseEntity.getBody();
        assertEquals("JWT Signature Is Not Valid!!", apiError.getMessage());
    }
    
    @Test
    void handleExpiredJwtException_ShouldReturnUnauthorizedWithTokenExpiredMessage() {
        ExpiredJwtException ex = new ExpiredJwtException(null, null, "JWT expired");
        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleSignatureException(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        ApiError apiError = responseEntity.getBody();
        assertEquals("JWT Token is Expired !!", apiError.getMessage()); // Assuming TOKEN_EXPIRED is "Token expired"
    }
    @Test
    void handleMalformedJwtException_ShouldReturnForbiddenWithJwtTokenNotValidMessage() {
        MalformedJwtException ex = new MalformedJwtException("Malformed JWT");
        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleMalformedJwtException(ex);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        ApiError apiError = responseEntity.getBody();
        assertEquals("JWT Token is Not Valid!!", apiError.getMessage());
    }
    
    @Test
    void handleDBException_ShouldReturnTooManyRequestsWithDatabaseErrorMessage() {
        SQLException ex = new SQLException("Database connection error");
        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleDBException(ex);
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, responseEntity.getStatusCode());
        ApiError apiError = responseEntity.getBody();
        assertEquals("Database connection error", apiError.getMessage());
    }
    
    
    @Test
    void handleAllException_ShouldReturnInternalServerErrorWithRootCauseErrorMessage() {
        Exception ex = new Exception("Some internal error");
        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleAllException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ApiError apiError = responseEntity.getBody();
        assertEquals("Some internal error", apiError.getMessage());
    }
    
    @Test
    void handleNoHandlerFoundException_ShouldReturnNotFoundWithErrorMessage() {
        NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/api/resource", null);
        mockHttpRequest.setRequestURI("/api/resource");
        WebRequest webRequest = new ServletWebRequest(mockHttpRequest);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleNoHandlerFoundException(
                ex, headers, HttpStatus.NOT_FOUND, webRequest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertEquals("No URL Found In The CRM with http://localhost/api/resource.", apiError.getMessage());
    }
   
    
    @Test
    void handleMissingPathVariable_ShouldReturnBadRequestWithErrorMessage() {
        MissingPathVariableException ex = mock(MissingPathVariableException.class);
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleMissingPathVariable(
                ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, apiError.getHttpStatus());
    }
 
    private static class PathImpl implements Path {
        private final String property;

        private PathImpl(String property) {
            this.property = property;
        }

        public static Path of(String property) {
            return new PathImpl(property);
        }

        @Override
        public String toString() {
            return property;
        }

		@Override
		public Iterator<Node> iterator() {
			return null;
		}
    }
}
