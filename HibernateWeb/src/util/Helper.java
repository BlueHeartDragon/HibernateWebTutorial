package util;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entity.Employee;

public class Helper {
    public static SessionFactory factory;
	
	
	public static void addEmployee(String fname, String lname, int salary, String secretKey) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;

		try {
			tx = session.beginTransaction();
			Employee employee = new Employee(fname, lname, salary, TrippleDes.encrypt(secretKey));
			
			employeeID = (Integer) session.save(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List listEmployees(String fname) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Employee> employees = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM Employee where fname like :f_name");
			query.setString("f_name", fname+"%");
			query.setCacheable(true); // Second level query caching
			query.setCacheRegion("Employee"); // Second level query caching
			employees=query.list();			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employees;
	}
	
	@SuppressWarnings("unchecked")
	public static List getAllEmployees() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Employee> employees = null;
		 try {
			 tx = session.beginTransaction();
			 Query query = session.createQuery("FROM Employee");
			 query.setCacheable(true);
			 query.setCacheRegion("Employee");
			 employees = query.list();
			 tx.commit();
		 } catch(HibernateException e) {
			 if(tx!=null)
				 tx.rollback();
			 e.printStackTrace();
		 } finally {
			 session.close();
		 }
		 return employees;
	}

}
