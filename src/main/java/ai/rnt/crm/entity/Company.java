package ai.rnt.crm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "companycrm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company extends Auditable{
	
	private static final long serialVersionUID = 2431108888580483486L;
	
	@Id
	@GeneratedValue(strategy =IDENTITY)
	private Integer companyId;
	
	@Column(name="companyName")
	private String companyName;

}