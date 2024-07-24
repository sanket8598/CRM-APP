package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

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
 * @author Sanket Wakanakar
 * @version 1.0
 * @since 24/07/2024
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_currency_master")
@Where(clause = "deleted_by is null")
public class CurrencyMaster extends Auditable {

	private static final long serialVersionUID = -2393130004737859949L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "currency_id")
	private Integer currencyId;

	@Column(name = "currency_code")
	private String currencyCode; 

	@Column(name = "currency_name")
	private String currencyName; 

	@Column(name = "currency_symbol")
	private String currencySymbol; 

	@OneToMany(mappedBy = "currency", cascade = ALL, orphanRemoval = true)
	private List<CountryMaster> countryMaster;
}
