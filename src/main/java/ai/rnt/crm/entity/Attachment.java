package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
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
 * @since 12/09/2023.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_email_attachment")
@Where(clause = "deleted_by is null")
public class Attachment extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "email_attch_id")
	private Integer emailAttchId;

	@Column(name = "attachment")
	private String attachmentData;

	@Column(name = "attach_type")
	private String attachType;

	@Column(name = "attach_name")
	private String attachName;

	@ManyToOne(cascade = ALL)
	@JoinColumn(name = "mail_id")
	private Email mail;
}
