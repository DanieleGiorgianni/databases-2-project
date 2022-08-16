package telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import telco.entities.Employee;


@Stateless
public class EmployeeService {
	
	@PersistenceContext(unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Pers
	
	public EmployeeService() {}
	
	public Employee findEmployeeById(int employeeId) {
		return em.find(Employee.class, employeeId);
	}

	public List<Employee> findEmployeeByName () {
		return em.createNamedQuery("Employee.findEmployeeByName", Employee.class).getResultList();
	}
}
