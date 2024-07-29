package ai.rnt.crm.entity;

import static ai.rnt.crm.util.ConvertDateFormatUtil.convertTimeTo12Hours;
import static java.util.Objects.nonNull;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.LazyCollectionOption.FALSE;

import java.time.LocalDate;
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
import javax.persistence.OneToOne;
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

	@Column(name = "topic")
	private String topic;

	@Column(name = "status")
	private String status;

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

	@Column(name = "budget_amount")
	private String budgetAmount;

	@Column(name = "customer_need")
	private String customerNeed;

	@Column(name = "proposed_solution")
	private String proposedSolution;

	@Column(name = "disqualify_as")
	private String disqualifyAs;

	@Column(name = "disqualify_reason")
	private String disqualifyReason;

	@Column(name = "pseudo_name")
	private String pseudoName;

	@Column(name = "is_follow_up_remainder")
	private Boolean isFollowUpRemainder;

	@Column(name = "remainder_via")
	private String remainderVia;

	@Column(name = "remainder_due_at")
	private String remainderDueAt;

	@Column(name = "remainder_due_on")
	private LocalDate remainderDueOn;

	@Column(name = "shared_req", columnDefinition = "default false")
	private Boolean requirementShared;

	@Column(name = "dec_maker", columnDefinition = "default false")
	private Boolean identifyDecisionMaker;

	@Column(name = "first_mtg_done", columnDefinition = "default false")
	private Boolean firstMeetingDone;

	@Column(name = "customer_readiness")
	private String customerReadiness;

	@Column(name = "qualify_rem")
	private String qualifyRemarks;

	@Column(name = "progress_status")
	private String progressStatus;

	@Column(name = "current_phase")
	private String currentPhase;

	@ManyToOne(cascade = { MERGE, DETACH, REFRESH })
	@JoinColumn(name = "lead_source_id")
	private LeadSourceMaster leadSourceMaster;

	@ManyToOne(cascade = { MERGE, DETACH, REFRESH })
	@JoinColumn(name = "service_falls_id")
	private ServiceFallsMaster serviceFallsMaster;

	@ManyToOne(cascade = { MERGE, DETACH, REFRESH })
	@JoinColumn(name = "domain_id")
	private DomainMaster domainMaster;

	@OneToMany(mappedBy = "lead", orphanRemoval = true)
	@LazyCollection(FALSE)
	private List<Contacts> contacts = new ArrayList<>();

	@OneToMany(mappedBy = "lead", cascade = ALL)
	private List<Email> emails = new ArrayList<>();

	@OneToMany(mappedBy = "lead", cascade = ALL)
	private List<Call> calls = new ArrayList<>();

	@OneToMany(mappedBy = "lead", cascade = ALL)
	private List<Visit> visit = new ArrayList<>();

	@OneToMany(mappedBy = "lead", cascade = ALL)
	private List<Description> descriptions = new ArrayList<>();

	@OneToMany(mappedBy = "lead")
	private Set<LeadImportant> impLead;

	@Transient
	private Boolean important = false;

	@OneToMany(mappedBy = "lead", cascade = ALL, orphanRemoval = true)
	private List<LeadTask> leadTasks = new ArrayList<>();

	@OneToOne(mappedBy = "leads", cascade = ALL, orphanRemoval = true)
	private Opportunity opportunity;
	
	@ManyToOne(cascade = {DETACH,MERGE})
	@JoinColumn(name = "currency_id")
	private CurrencyMaster currency;

	@Transient
	public String getRemainderDueAt12Hours() {
		if (nonNull(getRemainderDueAt()))
			return convertTimeTo12Hours(getRemainderDueAt());
		return null;
	}
}
