package telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import telco.entities.Employee;
import telco.exceptions.CredentialsException;


@Stateless
public class EmployeeService {
	
	@PersistenceContext (unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Pers
	
	public EmployeeService() {}
	
	public Employee findEmployeeById(int employeeId) {
		return em.find(Employee.class, employeeId);
	}

	public List<Employee> findEmployeeByName () {
		return em.createNamedQuery("Employee.findEmployeeByName", Employee.class).getResultList();
	}
	
	
	public Employee login(String username, String password) throws CredentialsException, NonUniqueResultException{
		List<Employee> employeeList = null;
		
		try {
			employeeList = em
					.createNamedQuery("Employee.checkLoginCredentials", Employee.class)
					.setParameter(1, username)
					.setParameter(2, password)
					.getResultList();
			
			if (employeeList.isEmpty())
				return null;
			
			System.out.println("Login in EmployeeService OK");
			
			return employeeList.get(0);
		} catch (PersistenceException e) {
			throw new CredentialsException("Impossible verify your credentials");
		}
	}
}
