package ai.rnt.crm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "crm_log")
@Where(clause = "deleted_by is null")
public class CrmLog extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "crm_log_id")
	private Integer crmLogId;

	@Column(name = "crm_contact_id")
	private Integer crmContactId;

	@Column(name = "comm_type")
	private String commType;

	@Column(name = "to_msg")
	private String toMessage;

	@Column(name = "your_msg")
	private String yourMessage;

	@Column(name = "call_type")
	private String callType;

	@Column(name = "call_with")
	private String callWith;

	@Column(name = "note")
	private String note;

	@Column(name = "from_mail")
	private String fromMail;

	@Column(name = "to_mail")
	private String toMail;

	@Column(name = "cc_mail")
	private String ccMail;

	@Column(name = "subject")
	private String subject;

	@Column(name = "your_mail")
	private String yourMail;

	@Column(name = "note_title")
	private String noteTitle;

	@Column(name = "your_note")
	private String yourNote;

}
