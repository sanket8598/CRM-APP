package ai.rnt.crm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "crm_important_lead",uniqueConstraints = 
{ @UniqueConstraint(name = "UniqueLeadAndEmployee", columnNames = { "lead_id", "staff_id" })})
@Setter
@Getter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class LeadImportant extends Auditable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3395385139148013907L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
	
	@ManyToOne
    @JoinColumn(name = "lead_id")
    Leads lead;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    EmployeeMaster employee;

}
