package ai.rnt.crm.exception;

import ai.rnt.crm.payloads.ApiError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CRMException extends RuntimeException {

	private static final long serialVersionUID = 3552888431302818887L;
	
	private final ApiError error;
	private final Exception exception;
	
	public CRMException(String exception) {
		super(exception);
		this.error = null;
		this.exception = null;
	}
	
	public CRMException(String exception, ApiError apiError) {
		super(exception);
		this.error = apiError;
		this.exception = null;
	}
	
	public CRMException(Exception exception) {
		super(exception);
		this.error = null;
		this.exception = exception;
	}
	
	public CRMException(ApiError error, Exception exception) {
		super(exception);
		this.error = error;
		this.exception = exception;
	}

}
