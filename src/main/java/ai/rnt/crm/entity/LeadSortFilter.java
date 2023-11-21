package ai.rnt.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="crm_lead_card_sort")
@Setter
@Getter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class LeadSortFilter extends Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3720164365002721975L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="lead_sort_filt_id")
    private Integer leadSortFilterId;
	
	@Column(name="primary_field")
	private String primaryFilter;
	
	@Column(name="secondary_field")
	private String secondaryFilter;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    EmployeeMaster employee;
    
    


}
