package ai.rnt.crm.entity;

import static ai.rnt.crm.constants.DateFormatterConstant.TIME_12_HRS;
import static ai.rnt.crm.constants.DateFormatterConstant.TIME_24_HRS;
import static java.util.Objects.nonNull;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class CommonField extends Auditable{
	
	private static final long serialVersionUID = -5691709078982916604L;

	@Column(name = "subject")
	private String subject;

	@Column(name = "comment")
	private String comment;

	@Column(name = "duration")
	private String duration;

	@Column(name = "start_date", columnDefinition = "date")
	private Date startDate;

	@Column(name = "end_date", columnDefinition = "date")
	private Date endDate;

	@Column(name = "start_time")
	private String startTime;

	@Column(name = "end_time")
	private String endTime;

	@Column(name = "all_day")
	private boolean allDay;

	@Column(name = "status")
	private String status;
	
	@Transient
	public String getStartTime12Hours() {
		if (nonNull(getStartTime())) {
			try {
				return TIME_12_HRS.format(TIME_24_HRS.parse(getStartTime()));
			} catch (ParseException e) {
				return getStartTime();
			}
		}
		return null;
	}

	@Transient
	public String getEndTime12Hours() {
		if (nonNull(getEndTime()))
			try {
				return TIME_12_HRS.format(TIME_24_HRS.parse(getEndTime()));
			} catch (ParseException e) {
				return getEndTime();
			}
		return null;
	}
}
