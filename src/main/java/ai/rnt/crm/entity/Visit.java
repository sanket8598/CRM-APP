package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.FetchMode.JOIN;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 14-09-2023.
 * @version 1.0
 */

@Entity
@Table(name = "crm_visit")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class Visit extends CommonField {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "visit_id")
	private Integer visitId;
	
	@Column(name = "content")
	private String content;

	@Column(name = "location")
	private String location;

	@Column(name = "participants")
	private String participates;
	
	@Column(name="is_opportunity",columnDefinition = "default false")
	private Boolean isOpportunity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_by", updatable = true, nullable = false)
	private EmployeeMaster visitBy;

	@ManyToOne
	@JoinColumn(name = "lead_id")
	private Leads lead;
	
	@OneToMany(mappedBy = "visit", cascade = ALL, orphanRemoval = true)
	@Fetch(JOIN)
	private List<VisitTask> visitTasks = new ArrayList<>();
}
