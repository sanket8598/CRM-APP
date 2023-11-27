package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.PERSIST;
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
 * @since 25/11/2023
 * @version 1.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_meeting_attachment")
@Where(clause = "deleted_by is null")
public class MeetingAttachments extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mtg_attch_id")
	private Integer meetingAttchId;

	@Column(name = "mtg_attachment")
	private String meetingAttachmentData;

	@Column(name = "mtg_attach_type")
	private String meetingAttachType;

	@Column(name = "mtg_attach_name")
	private String meetingAttachName;

	@ManyToOne(cascade = PERSIST)
	@JoinColumn(name = "crm_mtg_id")
	private Meetings meetings;
}
