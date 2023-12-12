package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.LazyCollectionOption.FALSE;

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

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
public class Leads extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "lead_id")
	@EqualsAndHashCode.Include
	private Integer leadId;

	@Column(name = "topic")
	@EqualsAndHashCode.Include
	private String topic;

	@Column(name = "status")
	@EqualsAndHashCode.Include
	private String status;

	@JoinColumn(name = "assign_to", updatable = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne
	private EmployeeMaster employee;

	@Column(name = "budget_amount")
	@EqualsAndHashCode.Include
	private String budgetAmount;


	@Column(name = "customer_need")
	@EqualsAndHashCode.Include
	private String customerNeed;

	@Column(name = "proposed_solution")
	@EqualsAndHashCode.Include
	private String proposedSolution;

	@Column(name = "disqualify_as")
	@EqualsAndHashCode.Include
	private String disqualifyAs;

	@Column(name = "disqualify_reason")
	@EqualsAndHashCode.Include
	private String disqualifyReason;

	@Column(name = "pseudo_name")
	@EqualsAndHashCode.Include
	private String pseudoName;

	@ManyToOne(cascade = { MERGE, DETACH, REFRESH })
	@JoinColumn(name = "lead_source_id")
	@EqualsAndHashCode.Include
	private LeadSourceMaster leadSourceMaster;

	@ManyToOne(cascade = { MERGE, DETACH, REFRESH })
	@JoinColumn(name = "service_falls_id")
	@EqualsAndHashCode.Include
	private ServiceFallsMaster serviceFallsMaster;
	
	@OneToMany(mappedBy = "lead",orphanRemoval = true)
	@LazyCollection(FALSE)
	@EqualsAndHashCode.Include
	private List<Contacts> contacts = new ArrayList<>();

	@OneToMany(mappedBy = "lead", cascade = ALL)
	private List<Email> emails = new ArrayList<>();

	@OneToMany(mappedBy = "lead", cascade = ALL)
	private List<Call> calls = new ArrayList<>();

	@OneToMany(mappedBy = "lead", cascade = ALL)
	private List<Visit> visit = new ArrayList<>();

	@OneToMany(mappedBy = "lead")
	private Set<LeadImportant> impLead;

	@Transient
	private Boolean important = false;

	@OneToMany(mappedBy = "lead", cascade = ALL, orphanRemoval = true)
	private List<LeadTask> leadTasks = new ArrayList<>();
	
}
