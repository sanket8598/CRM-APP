package ai.rnt.crm.enums;

import ai.rnt.crm.constants.ApiResponseKeyConstant;

public enum ApiResponse {
	
	DATA(ApiResponseKeyConstant.DATA),MESSAGE(ApiResponseKeyConstant.MESSAGE),
	TOKEN(ApiResponseKeyConstant.TOKEN),SUCCESS(ApiResponseKeyConstant.SUCCESS);

	String data;
	ApiResponse(String data2) {
		this.data=data2;
	}


}
