package ai.rnt.crm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @@since 22/08/2023.
 * @version 1.0
 */
@Entity
@Table(name = "crm_lead_master")
@Getter
@Setter
@NoArgsConstructor
public class CrmLeads extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "lead_id")
	private Integer leadId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phoneNumber")
	private Integer phoneNumber;

	@Column(name = "topic")
	private String topic;

	@Column(name = "company_website")
	private Integer companyWebsite;

	@Column(name = "budget_amount")
	private Float budgetAmount;

	@Column(name = "business_card")
	private String businessCard;

	@Column(name = "customer_need")
	private String customerNeed;

	@Column(name = "proposed_solution")
	private String proposedSolution;

	@Column(name = "lead_source_id")
	private Integer leadSourceId;

	@Column(name = "service_falls_id")
	private Integer serviceFallsId;

	@Column(name = "company_id")
	private Integer companyId;

}
