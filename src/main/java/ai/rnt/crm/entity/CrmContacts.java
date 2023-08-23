package ai.rnt.crm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 22/08/2023
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_contacts")
public class CrmContacts extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "crm_contact_id")
	private Integer crmContactId;

	@Column(name = "fname")
	private String firstName;

	@Column(name = "lname")
	private String lastName;

	@Column(name = "designtion")
	private String designtion;

	@Column(name = "department")
	private String department;

	@Column(name = "work_email")
	private String workEmail;

	@Column(name = "personal_email")
	private String personalEmail;

	@Column(name = "contact_number_primary")
	private String contactNumberPrimary;

	@Column(name = "contact_number_secondary")
	private String contactNumberSecondary;

	@Column(name = "company_id")
	private Integer companyId;

	@Column(name = "linkedin_id")
	private String linkedinId;

	@Column(name = "communication_log")
	private String communicationLog;

	@Column(name = "touchpoint")
	private String touchpoint;

	@Column(name = "remark")
	private String remark;

	@Column(name = "followup_date")
	private Date followUpDate;

}
