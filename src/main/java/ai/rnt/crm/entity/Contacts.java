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
 * @since 22/08/2023
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_contacts")
@Where(clause = "deleted_by is null")
public class Contacts extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "crm_contact_id")
	private Integer contactId;

	@Column(name = "f_name")
	private String firstName;

	@Column(name = "l_name")
	private String lastName;

	@Column(name = "designation")
	private String designation;

	@Column(name = "work_email")
	private String workEmail;

	@Column(name = "personal_email")
	private String personalEmail;

	@Column(name = "contact_no_primary")
	private String contactNumberPrimary;

	@Column(name = "contact_no_secondary")
	private String contactNumberSecondary;
	
	@Column(name = "linkedin_id")
	private String linkedinId;
	
	@Column(name = "business_card", columnDefinition = "LONGTEXT")
	private String businessCard;

	@Column(name = "business_card_name")
	private String businessCardName;

	@Column(name = "business_card_type")
	private String businessCardType;
	
	@ManyToOne(cascade=ALL)
	@JoinColumn(name = "lead_id",nullable = true)
	private Leads lead;
	
	@ManyToOne
	@JoinColumn(name = "company_id",nullable = true)
	private CompanyMaster companyMaster;

	@Column(name = "is_primary",columnDefinition = "boolean default false")
	private Boolean primary;
	
	@Column(name = "is_client",columnDefinition = "boolean default false")
	private Boolean client;
}
