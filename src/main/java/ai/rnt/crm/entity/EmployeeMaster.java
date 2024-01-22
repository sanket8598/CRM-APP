package ai.rnt.crm.entity;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sanket Wakankar
 * @since 19-08-2023
 * @version 1.0
 */
// @formatter:off
 
@Entity
@Table(name = "employee_master")
@Setter
@Getter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class EmployeeMaster extends Auditable {

	private static final long serialVersionUID = 931377982617396405L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "staff_id")
	private Integer staffId;

	@Column(name = "password")
	private String password;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "f_name")
	private String firstName; // first_name

	@Column(name = "m_name")
	private String middleName; // middle_name

	@Column(name = "l_name")
	private String lastName;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "manager_id")
	private Integer managerId;

	@Column(name = "emp_job_title")
	private String employeeJobTitle;


	@Column(name = "date_of_departure")
	private LocalDate departureDate;

	@ManyToMany(fetch = EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), 
	               inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Where(clause = "deleted_by is null")
	private List<RoleMaster> employeeRole = new ArrayList<>();
	
	@OneToMany(mappedBy = "employee")
	private List<Leads> leads = new ArrayList<>();
	
	@OneToMany(mappedBy = "employee")
	 Set<LeadImportant> impLead;

	public EmployeeMaster(Integer staffId, String firstName, String lastName, LocalDate departureDate,String emailId) {
		super();
		this.staffId = staffId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.departureDate = departureDate;
		this.emailId = emailId;
	}
}
//@formatter:on