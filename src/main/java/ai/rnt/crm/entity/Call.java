package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 23/08/2023
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_call")
@Where(clause = "deleted_by is null")
public class Call extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "call_id")
	private Integer callId;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.TRUE)
	@JsonIgnore
	@JoinColumn(name = "call_from", updatable = true, nullable = false)
	private EmployeeMaster callFrom;

	@Column(name = "call_to")
	private String callTo;

	@Column(name = "subject")
	private String subject;

	@Column(name = "direction")
	private String direction;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "comment")
	private String comment;

	@Column(name = "duration")
	private String duration;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "start_time")
	private String startTime;

	@Column(name = "end_time")
	private String endTime;

	@Column(name = "all_day")
	private boolean allDay;

	@Column(name = "status")
	private String status;

	@ManyToOne(cascade = MERGE, fetch = LAZY)
	@JoinColumn(name = "lead_id")
	private Leads lead;

	@OneToMany(mappedBy = "call", cascade = ALL, orphanRemoval = true)
	private List<PhoneCallTask> callTasks = new ArrayList<>();

}
