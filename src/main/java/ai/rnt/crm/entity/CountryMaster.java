package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "crm_country_master")
@Where(clause = "deleted_by is null")
public class CountryMaster extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "country_id")
	private Integer countryId;

	@Column(name = "country")
	private String country;
	
	@OneToMany(cascade =ALL,mappedBy = "country")
	private List<CompanyMaster> contacts=new ArrayList<>();

}
