package ai.rnt.crm.entity;

import static ai.rnt.crm.util.ConvertDateFormatUtil.convertTimeTo12Hours;
import static java.util.Objects.nonNull;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.FetchMode.JOIN;

import java.time.LocalDate;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 23/08/2023.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_email")
@Where(clause = "deleted_by is null")
public class Email extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "email_id")
	private Integer mailId;

	@Column(name = "mail_from")
	private String mailFrom;

	@Column(name = "mail_to")
	private String toMail;

	@Column(name = "cc_mail")
	private String ccMail;

	@Column(name = "bcc_mail")
	private String bccMail;

	@Column(name = "subject")
	private String subject;

	@Column(name = "content")
	private String content;

	private String status;

	@Column(name = "is_scheduled")
	private Boolean scheduled;

	@Column(name = "scheduled_on", columnDefinition = "date")
	private LocalDate scheduledOn;

	@Column(name = "scheduled_at")
	private String scheduledAt;

	@Column(name = "is_opportunity", columnDefinition = "default false")
	private Boolean isOpportunity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_id")
	private Leads lead;

	@OneToMany(mappedBy = "mail", cascade = { REMOVE, REFRESH }, orphanRemoval = true)
	@Fetch(JOIN)
	private List<Attachment> attachment = new ArrayList<>();

	@Transient
	public String getScheduledAtTime12Hours() {
		if (nonNull(getScheduledAt()))
			return convertTimeTo12Hours(getScheduledAt());
		return null;
	}
}
