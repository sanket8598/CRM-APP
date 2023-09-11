package ai.rnt.crm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "crm_add_email")
@Where(clause = "deleted_by is null")
public class AddEmail extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "add_mail_id")
	private Integer addMailId;

	@Column(name = "mail_from")
	private String mailFrom;

	@Column(name = "mail_to")
	private String mailTo;

	@Column(name = "cc")
	private String cc;

	@Column(name = "bcc")
	private String bcc;

	@Column(name = "subject")
	private String subject;

	@Column(name = "content")
	private String content;

	@ManyToOne
	@JoinColumn(name="lead_id")
	private Leads lead;

}
