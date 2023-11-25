package ai.rnt.crm.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 */
@Data
public class MettingDto {

	private Integer mettingId;

	private String mettingTitle;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<String> participates;

	@JsonFormat(pattern="dd-MM-yyyy")
	private Date startDate;

	@JsonFormat(pattern="dd-MM-yyyy")
	private Date endDate;

	private String startTime;

	private String endTime;

	private boolean allDay;

	private String location;

	private String description;

	private String mettingMode;

	private List<MettingAttachmentsDto> mettingAttachments;

}
