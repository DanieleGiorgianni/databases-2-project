package telco.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table (name="user", schema="telcodb")

@NamedQuery (name = "User.checkRegistrationUsername", query = "SELECT u FROM User u WHERE u.username = ?1")
@NamedQuery (name = "User.checkRegistrationEmail", query = "SELECT u FROM User u WHERE u.email = ?1")
@NamedQuery (name = "User.checkLoginCredentials", query = "SELECT u FROM User u WHERE u.username = ?1 and u.password = ?2")
@NamedQuery (name = "User.findAllInsolvents", query = "SELECT u FROM User u WHERE u.insolvent = TRUE")
@NamedQuery (name = "User.findUserByName", query = "SELECT u FROM User u WHERE u.username = ?1")

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;

	//Attributes
	private String username;
	private String email;
	private String password;
	private boolean insolvent;
	
	public User() {}
	
	//Relationships
	@OneToOne (mappedBy = "user")
	private Alert alert;
	
	@OneToMany (mappedBy = "user")
	private List<Order> orders = new ArrayList<Order>();
	
	@OneToMany (mappedBy = "user")
	private List<Sas> sasses = new ArrayList<Sas>();
	
	//Getters and Setters
	public int getUserid() {
		return id;
	}
	public void setUserid(int userid) {
		this.id = userid;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isInsolvent() {
		return insolvent;
	}
	public void setInsolvent(boolean insolvent) {
		this.insolvent = insolvent;
	}
	public Alert getAlert() {
		return alert;
	}
	public void setAlert(Alert alert) {
		this.alert = alert;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public List<Sas> getSasses() {
		return sasses;
	}
	public void setSasses(List<Sas> sasses) {
		this.sasses = sasses;
	}
}
