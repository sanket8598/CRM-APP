package ai.rnt.crm.entity;

import static ai.rnt.crm.constants.DateFormatterConstant.TIME_12_HRS;
import static ai.rnt.crm.constants.DateFormatterConstant.TIME_24_HRS;
import static java.util.Objects.nonNull;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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

@Entity
@Table(name = "crm_meeting_task")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class MeetingTask extends Auditable {

	private static final long serialVersionUID = -3854915649423434269L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mtg_task_id")
	private Integer meetingTaskId;

	@Column(name = "subject")
	private String subject;

	@Column(name = "task_status")
	private String status;

	@Column(name = "task_priority")
	private String priority;

	@Column(name = "task_due_date")
	private Date dueDate;

	@Column(name = "task_due_time")
	private String dueTime;

	@Column(name = "task_description")
	private String description;

	@Column(name = "remainder", columnDefinition = "boolean default false")
	private boolean remainderOn;

	@Column(name = "remainder_via")
	private String remainderVia;

	@Column(name = "remainder_due_at")
	// @Temporal(TIME)
	private String remainderDueAt;

	@Column(name = "remainder_due_on", columnDefinition = "date")
	private Date remainderDueOn;

	@JoinColumn(name = "crm_mtg_id", updatable = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne
	private Meetings meetings;

	@JoinColumn(name = "task_assign_to", updatable = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne
	private EmployeeMaster assignTo;

	@OneToMany(mappedBy = "meetingTask", cascade = ALL)
	private List<TaskNotifications> taskNotifications;
	
	@Transient
	public String getDueTime12Hours() {
		if (nonNull(getDueTime())) {
			try {
				return TIME_12_HRS.format(TIME_24_HRS.parse(getDueTime()));
			} catch (ParseException e) {
				return getDueTime();
			}
		}
		return null;
	}

	@Transient
	public String getRemainderDueAt12Hours() {
		if (nonNull(getRemainderDueAt())) {
			try {
				return TIME_12_HRS.format(TIME_24_HRS.parse(getRemainderDueAt()));
			} catch (ParseException e) {
				return getRemainderDueAt();
			}
		}
		return null;
	}

}
