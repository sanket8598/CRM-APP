package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import ai.rnt.crm.dto.ContactDto;
import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @since 09/02/2024
 * @version 2.0
 *
 */
@Data
public class QualifyOpportunityDto {

	private Integer opportunityId;

	private String topic;

	private String budgetAmount;

	private Integer assignTo;

	private String proposedSolution;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate closedOn;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate updatedClosedOn;

	private Integer leadSourceId;

	private ContactDto primaryContact;

	private List<ContactDto> contacts = new ArrayList<>();
}
