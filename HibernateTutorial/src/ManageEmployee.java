import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import util.TrippleDes;

public class ManageEmployee {
	private static SessionFactory factory;
	private TrippleDes td;

	public ManageEmployee() {
		this.td = new TrippleDes();
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

		SessionFactory factory = meta.getSessionFactoryBuilder().build();

		Session session1 = factory.openSession();
/*		Employee emp1 = (Employee) session1.load(Employee.class, 5);
		System.out.println(emp1.getFname() + " " + emp1.getLname()+ " " + emp1.getSalary());*/
		SQLQuery q1 = session1.createSQLQuery("SELECT * FROM EMPLOYEE WHERE id = 5");
		q1.addEntity(Employee.class);
		q1.setCacheable(true);
		List<Employee> resultSet1 = q1.list();
		System.out.println(resultSet1.get(0));
		session1.close();

		Session session2 = factory.openSession();
		/*Employee emp2 = (Employee) session2.load(Employee.class, 5);
		System.out.println(emp2.getFname() + " " + emp2.getLname() + " " + emp2.getSalary());*/
		SQLQuery q2 = session2.createSQLQuery("SELECT * FROM EMPLOYEE WHERE id = 5");
		q2.addEntity(Employee.class);
		q2.setCacheable(true);

		List<Employee> resultSet2 = q2.list();
		System.out.println(resultSet2.get(0));
		session2.close();
	}

	public static void main2(String[] args) {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

		ManageEmployee me = new ManageEmployee();

		Integer empID1 = me.addEmployee("Manish", "Bharti", 35800, "@nbhbb");
		Integer empID2 = me.addEmployee("Don", "Das", 27500, "hnhsvv12");
		Integer empID3 = me.addEmployee("Jiji", "Paul", 30000, "qwerty");

		me.listEmployees();
		// me.updateEmployee(empID1, 42000);
		// me.deleteEmployee(empID2);
		// me.listEmployees();

		factory.close();
	}

	public Integer addEmployee(String fname, String lname, int salary, String secretKey) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;

		try {
			tx = session.beginTransaction();
			Employee employee = new Employee(fname, lname, salary, td.encrypt(secretKey));
			employeeID = (Integer) session.save(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employeeID;
	}

	@SuppressWarnings("unchecked")
	public void listEmployees() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List<Employee> employees = session.createQuery("FROM Employee").list();
			for (Employee employee : employees) {
				System.out.println("First Name : " + employee.getFname());
				System.out.println("Last Name  : " + employee.getLname());
				System.out.println("Salary     : " + employee.getSalary());
				System.out.println("Secret     : " + employee.getDecryptedSecretKey() + "\n");
			}

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void updateEmployee(Integer employeeID, int salary) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Employee employee = session.get(Employee.class, employeeID);
			employee.setSalary(salary);
			session.update(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	public void getEmployee(String secretKey) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List result = session.createSQLQuery("Select first_name, last_name from Employee where secret=" + secretKey)
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			System.out.println(result.get(0));
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteEmployee(Integer employeeID) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Employee employee = session.get(Employee.class, employeeID);
			session.delete(employee);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

}
