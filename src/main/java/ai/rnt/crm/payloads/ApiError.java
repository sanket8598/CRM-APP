package ai.rnt.crm.payloads;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.time.LocalDateTime.now;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"status", "message","timestamp", "errors", "messages" })
@JsonInclude(NON_NULL)
public class ApiError implements Serializable{
	private static final long serialVersionUID = -582783404133488565L;
	@JsonProperty("SUCCESS")
	private boolean status;
	@JsonProperty("MESSAGE")
	private String message;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	@JsonInclude(NON_EMPTY)
	private List<String> messages = new ArrayList<>();
	@JsonInclude(NON_EMPTY)
	private List<String> errors = new ArrayList<>();
	HttpStatus httpStatus;
	private ApiError() {
		timestamp = now();
	}

	public ApiError(HttpStatus httpStatus) {
		this();
		this.httpStatus = httpStatus;
	}
	public ApiError(String message, List<String> messages) {
		this.message = message;
		this.messages = messages;
	}

	public ApiError(HttpStatus httpStatus, Throwable ex) {
		this();
		this.httpStatus = httpStatus;
		this.message = "Unexpected error";
		errors = Arrays.asList(ex.getLocalizedMessage(), ex.getMessage(), ExceptionUtils.getMessage(ex));
	}
	
	public ApiError(String message, Throwable ex) {
		this();
		this.message = message;
		errors = Arrays.asList(message, ex.getLocalizedMessage(), ex.getMessage(), ExceptionUtils.getMessage(ex));
	}

	public ApiError(HttpStatus httpStatus, String message, Throwable ex) {
		this();
		this.httpStatus = httpStatus;
		this.message = message;
		errors = Arrays.asList(ex.getLocalizedMessage(), ExceptionUtils.getMessage(ex));
	}

	public ApiError(HttpStatus httpStatus, String message, List<String> errors) {
		this();
		this.httpStatus = httpStatus;
		this.message = message;
		this.errors = errors;
	}
	public ApiError(boolean status, String message) {
		this();
		this.status = status;
		this.message = message;
	}
	public ApiError(boolean status, String message,List<String> errors,HttpStatus httpStatus) {
		this();
		this.status = status;
		this.message = message;
		this.httpStatus = httpStatus;
		this.errors = errors;
	}
}
