package telco.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table (name = "service", schema = "telcodb")

@NamedQuery (name = "Service.findAllServices", query = "SELECT s FROM Service s")

@NamedQuery (name = "Service.findServiceById", query = "SELECT s FROM Service s WHERE s.id = ?1")
@NamedQuery (name = "Service.findOrderByType", query = "SELECT s FROM Service s WHERE s.type = ?1")

public class Service implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	//Attributes
	private String type;
	private int minsnum;
	private int minsfee;
	private int smsnum;
	private int smsfee;
	private int giganum;
	private int gigafee;
	
	public Service() {}
	
	//Relationship
	@ManyToMany (mappedBy = "services")
	private List<Package> packages;
	
	//Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getMinsnum() {
		return minsnum;
	}
	public void setMinsnum(int minsnum) {
		this.minsnum = minsnum;
	}
	public int getMinsfee() {
		return minsfee;
	}
	public void setMinsfee(int minsfee) {
		this.minsfee = minsfee;
	}
	public int getSmsnum() {
		return smsnum;
	}
	public void setSmsnum(int smsnum) {
		this.smsnum = smsnum;
	}
	public int getSmsfee() {
		return smsfee;
	}
	public void setSmsfee(int smsfee) {
		this.smsfee = smsfee;
	}
	public int getGiganum() {
		return giganum;
	}
	public void setGiganum(int giganum) {
		this.giganum = giganum;
	}
	public int getGigafee() {
		return gigafee;
	}
	public void setGigafee(int gigafee) {
		this.gigafee = gigafee;
	}
	public List<Package> getPackages() {
		return packages;
	}
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
}
