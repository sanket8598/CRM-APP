package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "crm_company_master")
@Where(clause = "deleted_by is null")
public class CompanyMaster extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "company_id")
	private Integer companyId;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "website")
	private String companyWebsite;

	@Column(name = "address_line1")
	private String addressLineOne;

	@ManyToOne(cascade =ALL)
	@JoinColumn(name = "country_id")
	private CountryMaster country;

	@JoinColumn(name = "state_id")
	@ManyToOne(cascade =ALL)
	private StateMaster state;

	@ManyToOne(cascade =ALL)
	@JoinColumn(name = "city_id")
	private CityMaster city;

	@Column(name = "zipcode")
	private String zipCode;
	
	@OneToMany(mappedBy="companyMaster",cascade ={ ALL })
	private List<Leads> leads=new ArrayList<>();
	
	@OneToMany(mappedBy="companyMaster",cascade =ALL)
	private List<Contacts> contacts=new ArrayList<>();
}
