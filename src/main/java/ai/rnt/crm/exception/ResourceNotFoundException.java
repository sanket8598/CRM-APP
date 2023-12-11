package ai.rnt.crm.exception;

import lombok.experimental.StandardException;

@StandardException
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 9019011246094743847L;
	
    protected String resourceName;
    protected String fieldName;
    protected Object fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	public ResourceNotFoundException(String resourceName) {
		super(String.format("%s not found.",resourceName));
		this.resourceName = resourceName;
	}

}
