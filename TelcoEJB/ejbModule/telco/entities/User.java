package telco.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="user", schema="telcodb")

@NamedQuery(name = "User.checkRegistrationUsername", query = "SELECT u FROM User u  WHERE u.username = ?1")
@NamedQuery(name = "User.checkRegistrationEmail", query = "SELECT u FROM User u  WHERE u.email = ?1")

@NamedQuery(name = "User.checkLoginCredentials", query = "SELECT u FROM User u  WHERE u.username = ?1 and u.password = ?2")

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userid;
	//Attributes
	private String username;
	private String email;
	private String password;
	
	public User() {
	}
	
	//Getters and Setters
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
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
}
