package ai.rnt.crm.validation.impl;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import ai.rnt.crm.validation.ValidFile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelFileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

	@Override
	public void initialize(ValidFile constraintAnnotation) {
 }

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		log.info("entered inside the excel file custom validation annotation...");
		if(isNull(multipartFile) || multipartFile.isEmpty())
			return false;
		String contentType = multipartFile.getContentType();
		return nonNull(contentType) && isSupportedContentType(contentType);
	}

	private boolean isSupportedContentType(String contentType) {
		return ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").equals(contentType)
				|| ("application/vnd.ms-excel").equals(contentType);
	}
}
