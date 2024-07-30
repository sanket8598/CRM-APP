package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sanket Wakankar
 * @since 03-02-2024.
 * @version 1.0
 */
@Entity
@Table(name = "crm_opportunity")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class Opportunity extends Auditable {

	private static final long serialVersionUID = -8839548448388664325L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "opty_id")
	private Integer opportunityId;

	@Column(name = "status")
	private String status;

	@Column(name = "topic")
	private String topic;

	@Column(name = "pseudo_name")
	private String pseudoName;

	@Column(name = "budget_amount")
	private String budgetAmount;

	@Column(name = "closed_on")
	private LocalDate closedOn;

	@Column(name = "technical_need")
	private String technicalNeed;

	@Column(name = "integration_point")
	private String integrationPoint;

	@Column(name = "security_complainces")
	private String secAndComp;

	@Column(name = "risk_minigation")
	private String riskMinigation;

	@Column(name = "initial_timeline", columnDefinition = "date")
	private LocalDate initialTimeline;

	@Column(name = "lic_pric_dets")
	private String licAndPricDetails;

	@Column(name = "dev_plan")
	private String devPlan;

	@Column(name = "prop_accept_criteria")
	private String propAcceptCriteria;

	@Column(name = "prop_exp_date", columnDefinition = "date")
	private LocalDate propExpDate;

	@Column(name = "terms_and_conditions")
	private String termsAndConditions;

	@Column(name = "presentation")
	private String presentation;

	@Column(name = "scope_of_work")
	private String scopeOfWork;

	@Column(name = "proposition")
	private String proposition;

	@Column(name = "opty_lost_reason")
	private String lostReason;

	@Column(name = "payment_terms")
	private String paymentTerms;

	@Column(name = "current_phase")
	private String currentPhase;

	@Column(name = "contract")
	private String contract;

	@Column(name = "support_plan")
	private String supportPlan;

	@Column(name = "final_budget")
	private String finalBudget;

	@Column(name = "progress_status")
	private String progressStatus;

	@OneToMany(mappedBy = "opportunity", cascade = { REMOVE, REFRESH }, orphanRemoval = true)
	private List<OpprtAttachment> oprtAttachment = new ArrayList<>();

	@JoinColumn(name = "assign_to", updatable = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne
	private EmployeeMaster employee;
	
	@JoinColumn(name = "assign_by", updatable = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne
	private EmployeeMaster assignBy;
	
	@Column(name = "assign_on")
	private LocalDate assignDate;

	@OneToOne
	@JoinColumn(name = "lead_id", unique = true,updatable=false)
	private Leads leads;

	@OneToMany(mappedBy = "opportunity", cascade = ALL, orphanRemoval = true)
	private List<OpportunityTask> opportunityTasks = new ArrayList<>();

	@Column(name = "analysis_rem")
	private String analysisRemarks;

	@Column(name = "propose_rem")
	private String proposeRemarks;

	@Column(name = "timeline")
	private String timeline;

	@Column(name = "identify_sme", columnDefinition = "default false")
	private Boolean identifySme;

	@Column(name = "dev_proposal", columnDefinition = "default false")
	private Boolean developProposal;

	@Column(name = "compl_intl_rev", columnDefinition = "default false")
	private Boolean complInternalReview;

	@Column(name = "present_proposal", columnDefinition = "default false")
	private Boolean presentProposal;

	@Column(name = "final_comm_timeline", columnDefinition = "default false")
	private Boolean finalCommAndTimeline;

	@Column(name = "proj_kick_off", columnDefinition = "default false")
	private Boolean projectKickoff;

	@Column(name = "final_team", columnDefinition = "default false")
	private Boolean finalisingTeam;

	@Column(name = "sla_signed", columnDefinition = "default false")
	private Boolean slaSigned;

	@Column(name = "sow_signed", columnDefinition = "default false")
	private Boolean sowSigned;

	@Column(name = "nda_signed", columnDefinition = "default false")
	private Boolean ndaSigned;

	@Column(name = "mail_status", columnDefinition = "default false")
	private Boolean thankMailSent;

	@Column(name = "descrp")
	private String description;
	
	@Column(name = "feedback")
	private String feedback;

	@OneToMany(mappedBy = "opportunity", cascade = ALL)
	private List<Proposal> proposals = new ArrayList<>();
	
	@ManyToOne(cascade = {MERGE,DETACH})
	@JoinColumn(name = "currency_id")
	private CurrencyMaster currency;
}
