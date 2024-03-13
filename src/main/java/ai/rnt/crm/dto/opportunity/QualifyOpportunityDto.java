package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import ai.rnt.crm.dto.ContactDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 09/02/2024
 * @version 1.2
 *
 */
@Getter
@Setter
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

	private String progressStatus;

	private String currentPhase;

	private ContactDto primaryContact;

	private List<ContactDto> contacts = new ArrayList<>();

	private List<OpprtAttachmentDto> attachments = new ArrayList<>();

	private List<ContactDto> clients = new ArrayList<>();
}
