package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sanket Wakankar
 * @since 22-08-2023.
 * @version 1.0
 */
@Entity
@Table(name = "crm_lead")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class Leads extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "lead_id")
	private Integer leadId;

	@Column(name = "f_name")
	private String firstName;

	@Column(name = "l_name")
	private String lastName;

	@Column(name = "phone_no")
	private String phoneNumber;

	@Column(name = "topic")
	private String topic;

	@Column(name = "email")
	private String email;

	@Column(name = "status")
	private String status;

	@JoinColumn(name = "assign_to", updatable = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne
	private EmployeeMaster employee;

	@Column(name = "company_website")
	private String companyWebsite;

	@Column(name = "budget_amount")
	private String budgetAmount;

	@Column(name = "business_card", columnDefinition = "LONGTEXT")
	private String businessCard;
	
	@Column(name = "business_card_name")
	private String businessCardName;
	
	@Column(name = "business_card_type")
	private String businessCardType;

	@Column(name = "customer_need")
	private String customerNeed;

	@Column(name = "proposed_solution")
	private String proposedSolution;

	@Column(name = "disqualify_as")
	private String disqualifyAs;

	@Column(name = "disqualify_reason")
	private String disqualifyReason;

	@Column(name = "designation")
	private String designation;

	@Column(name = "pseudo_name")
	private String pseudoName;

	@ManyToOne(cascade = ALL)
	@JoinColumn(name = "lead_source_id")
	private LeadSourceMaster leadSourceMaster;

	@ManyToOne(cascade = ALL)
	@JoinColumn(name = "service_falls_id")
	private ServiceFallsMaster serviceFallsMaster;

	@ManyToOne(cascade = { MERGE, DETACH, REFRESH })
	@JoinColumn(name = "company_id")
	private CompanyMaster companyMaster;

	@OneToMany(mappedBy = "lead", cascade = ALL)
	private List<AddEmail> emails = new ArrayList<>();

	@OneToMany(mappedBy = "lead", cascade = ALL)
	private List<AddCall> calls = new ArrayList<>();

	@OneToMany(mappedBy = "lead", cascade = ALL)
	private List<Visit> visit = new ArrayList<>();

	@OneToMany(mappedBy = "lead")
	private Set<LeadImportant> impLead;

	@Transient
	private Boolean important = false;

}
