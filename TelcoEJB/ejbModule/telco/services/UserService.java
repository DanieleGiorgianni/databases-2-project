package telco.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import telco.entities.Order;
import telco.entities.User;
import telco.exceptions.CredentialsException;

/* 
 * UserService is an Enterprise Java Bean.
 * Enterprise Java Beans are the software components that implement server-side
 * the business logic of a web application within the Java EE architecture 
 * by performing services in favor of the front-end part.
 */
@Stateless // Does not maintain a conversational state with the client.
public class UserService {
	
	/* 
	 * Persistence Context is a set of objects that need to be managed 
	 * and tracked in their changes by the Entity Manager. 
	 */
	@PersistenceContext (unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.
	
	@EJB (name = "telco.services/OrderService")
	private OrderService orderService;

	public UserService() {}
	
	public User findUserById(int userId) {
		return em.find(User.class, userId);
	}
	
	public List<User> findAllInsolvents() {
		try {
			return em.createNamedQuery("User.findAllInsolvents", User.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public User findUserByName(String username) {
		return em.createNamedQuery("User.findUserByName", User.class).setParameter(1, username).getSingleResult();
	}

	public String registration(String username, String email, String password) throws CredentialsException{
		List<User> userList = null;
		
		try {
			userList = em
					.createNamedQuery("User.checkRegistrationUsername", User.class)
					.setParameter(1, username)
					.getResultList();
			
			if (!userList.isEmpty())
				return "Username already taken";
			
			userList = em
					.createNamedQuery("User.checkRegistrationEmail", User.class)
					.setParameter(1, email)
					.getResultList();
			
			if (!userList.isEmpty())
				return "Email already taken";
			
			User newUser = new User();
			newUser.setUsername(username);
			newUser.setEmail(email);
			newUser.setPassword(password);
			em.persist(newUser); // Persist an entity instance in the database.
			em.flush(); // Writes the state of entities to the database immediately.
			
			System.out.println("Registration in UserService OK");
			
			return "OK";
		} catch (PersistenceException e) {
			throw new CredentialsException("Impossible verify your credentials");
		}
	}
	
	public User login(String username, String password) throws CredentialsException, NonUniqueResultException{
		List<User> userList = null;
		
		try {
			userList = em
					.createNamedQuery("User.checkLoginCredentials", User.class)
					.setParameter(1, username)
					.setParameter(2, password)
					.getResultList();
			
			if (userList.isEmpty())
				return null;
			
			System.out.println("Login in UserService OK");
			
			return userList.get(0);
		} catch (PersistenceException e) {
			throw new CredentialsException("Impossible verify your credentials");
		}
	}
	
	public void insolventManager(User user) {
		User usr = findUserById(user.getUserid());
		List<Order>	rejectedOrders = new ArrayList<Order>();
		rejectedOrders = orderService.findRejectedOrdersByUser(usr);
		
		//System.out.println("> insolventManager: List<Order> size = " + rejectedOrders.size());
		for (Order rejectedOrder : rejectedOrders) {
			if (rejectedOrder.getFails() != 0) {
				usr.setInsolvent(true);
				em.persist(usr);
				em.flush();
				System.out.println("insolventManager in UserService DONE (insolvent=true)");
				return;
			}
		}
		usr.setInsolvent(false);
		
		em.persist(usr);
		em.flush();
		System.out.println("insolventManager in UserService DONE (insolvent=false)");
	}
}
