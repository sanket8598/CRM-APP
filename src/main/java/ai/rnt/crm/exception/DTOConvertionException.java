package ai.rnt.crm.exception;

import ai.rnt.crm.payloads.ApiError;

/**
 * Exception class to handle DTO to Entity convention failed. 
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 06-09-2021
 */
public class DTOConvertionException extends CRMException {

	private static final long serialVersionUID = 5224740785947546863L;

	public DTOConvertionException(ApiError error, Exception exception) {
		super(error, exception);
	}
	
	/**
	 * @param error
	 */
	public DTOConvertionException(String error) {
		super(error);
	}
}
