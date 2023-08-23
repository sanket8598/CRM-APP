package ai.rnt.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
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
@Table(name = "Crm_company_master")
public class CrmCompanyMaster extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "company_id")
	private Integer companyId;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "website")
	private String website;

	@Column(name = "address_line1")
	private String addressLineOne;

	@Column(name = "country_id")
	private Integer countryId;

	@Column(name = "state_id")
	private Integer stateId;

	@Column(name = "city_id")
	private Integer cityId;

	@Column(name = "zipcode")
	private String zipCode;
}
