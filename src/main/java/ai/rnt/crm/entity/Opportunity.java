package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.LazyCollectionOption.FALSE;

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
@Table(name = "crm_opportunites")
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

	@Column(name = "customer_need")
	private String customerNeed;

	@Column(name = "proposed_solution")
	private String proposedSolution;

	@JoinColumn(name = "assign_to", updatable = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne
	private EmployeeMaster employee;

	@OneToOne(cascade = ALL)
	@JoinColumn(name = "lead_id", unique = true)
	private Leads leads;

	@OneToMany(mappedBy = "opportunity", orphanRemoval = true)
	@LazyCollection(FALSE)
	private List<Contacts> contacts = new ArrayList<>();

	@OneToMany(mappedBy = "opportunity", cascade = ALL)
	private List<Email> emails = new ArrayList<>();

	@OneToMany(mappedBy = "opportunity", cascade = ALL)
	private List<Call> calls = new ArrayList<>();

	@OneToMany(mappedBy = "opportunity", cascade = ALL)
	private List<Visit> visit = new ArrayList<>();

	@OneToMany(mappedBy = "opportunity", cascade = ALL)
	private List<Meetings> meetings = new ArrayList<>();
}
