package ai.rnt.crm.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelFileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

	@Override
	public void initialize(ValidFile constraintAnnotation) {
		// TODO document why this method is empty
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		log.info("entered inside the excel file custom validation annotation...");
		String contentType = multipartFile.getContentType();
		return Objects.nonNull(contentType) && isSupportedContentType(contentType);
	}

	private boolean isSupportedContentType(String contentType) {
		return ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").equals(contentType)
				|| ("application/vnd.ms-excel").equals(contentType);
	}
}
