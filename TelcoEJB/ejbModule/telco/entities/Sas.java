package telco.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "sas", schema = "telcodb")

@NamedQuery (name = "Sas.findAllSas", query = "SELECT s FROM Sas s ")
@NamedQuery (name = "Sas.findSasByUser", query = "SELECT s FROM Sas s WHERE s.user = ?1")
@NamedQuery (name = "Sas.findSasByOrder", query = "SELECT s FROM Sas s WHERE s.order = ?1")

public class Sas implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	// Attributes.
	private Date deactivationdate;
	
	public Sas() {}
	
	// Relationships.
	@OneToOne 
	@JoinColumn (name = "orderid")
	private Order order;
	
	@ManyToOne
	@JoinColumn (name = "userid")
	private User user;
	
	// Getters and Setters.
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDeactivationdate() {
		return deactivationdate;
	}

	public void setDeactivationdate(Date deactivationdate) {
		this.deactivationdate = deactivationdate;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
