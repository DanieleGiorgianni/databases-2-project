package telco.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "employee", schema = "telcodb")

@NamedQuery (name = "Employee.checkLoginCredentials", query = "SELECT e FROM Employee e  WHERE e.employeename = ?1 and e.password = ?2")
@NamedQuery (name = "Employee.findEmployeeById", query = "SELECT e FROM Employee e WHERE e.id = ?1")
@NamedQuery (name = "Employee.findEmployeeByName", query = "SELECT e FROM Employee e WHERE e.employeename = ?1")

public class Employee implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	//Attributes
	private String employeename;
	private String password;
	private String email;
	
	public Employee() {}
	
	//Relationship
	@OneToMany (mappedBy = "employee")
	private List<Package> packages;
	
	@OneToMany (mappedBy = "employee")
	private List<Product> products;
	
	@ManyToMany
	@JoinTable (name = "employee_controls_alert", joinColumns = @JoinColumn (name = "employeeid"),
	inverseJoinColumns = @JoinColumn (name = "alertid"))
	private List<Alert> alerts;
	
	//Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
