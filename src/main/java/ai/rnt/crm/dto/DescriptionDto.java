package ai.rnt.crm.dto;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DescriptionDto {

	private Integer descId;

	private String subject;

	private String desc;

	private String action;

	private String type;

	@FutureOrPresent(message = "Date must not be smaller than today's date!!")
	private LocalDate date;

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private LocalDate getDate;

	private String status;

	private Boolean isOpportunity;
}
