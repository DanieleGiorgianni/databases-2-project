package telco.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "alert", schema = "telcodb")

@NamedQuery (name = "Alert.findAllAlerts", query = "SELECT a FROM Alert a")
@NamedQuery (name = "Alert.findAlertByUser", query = "SELECT a FROM Alert a WHERE a.user = ?1")

@NamedQuery (name = "Alert.findAlertById", query = "SELECT a FROM ALert a WHERE a.id = ?1")

public class Alert implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	//Attributes
	private String username;
	private String email;
	private int amount;
	private Timestamp lastdatetime;
	
	public Alert() {}
	
	//Relationship
	@OneToOne
	@JoinColumn (name = "userid")
	private User user;
	
	@ManyToMany (mappedBy = "alerts")
	private List<Employee> employees;
	
	//Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Timestamp getLastdatetime() {
		return lastdatetime;
	}
	public void setLastdatetime(Timestamp lastdatetime) {
		this.lastdatetime = lastdatetime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
