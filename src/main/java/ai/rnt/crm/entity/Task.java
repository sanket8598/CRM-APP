package ai.rnt.crm.entity;

import static ai.rnt.crm.util.ConvertDateFormatUtil.convertTimeTo12Hours;
import static java.util.Objects.nonNull;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Task extends Auditable {

	private static final long serialVersionUID = -6116639913717201924L;

	@Column(name = "subject")
	private String subject;

	@Column(name = "task_status")
	private String status;

	@Column(name = "task_priority")
	private String priority;

	@Column(name = "task_due_date")
	private LocalDate dueDate;

	@Column(name = "task_due_time")
	private String dueTime;

	@Column(name = "task_description")
	private String description;

	@Column(name = "remainder", columnDefinition = "boolean default false")
	private boolean remainderOn;

	@Column(name = "remainder_via")
	private String remainderVia;

	@Column(name = "remainder_due_at")
	private String remainderDueAt;

	@Column(name = "remainder_due_on", columnDefinition = "date")
	private LocalDate remainderDueOn;

	@Transient
	public String getDueTime12Hours() {
		if (nonNull(getDueTime()))
			return convertTimeTo12Hours(getDueTime());
		return null;
	}

	@Transient
	public String getRemainderDueAt12Hours() {
		if (nonNull(getRemainderDueAt()))
			return convertTimeTo12Hours(getRemainderDueAt());
		return null;
	}
}
