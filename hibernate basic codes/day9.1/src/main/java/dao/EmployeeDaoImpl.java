package dao;

import static utils.HibernateUtils.getFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pojos.Employee;
import pojos.EmploymentType;

public class EmployeeDaoImpl implements EmployeeDao {

	@Override
	public String addNewEmployee(Employee emp) {
		// emp : TRANSIENT
		String mesg = "Adding emp details failed!!!!!";
		// 1. open session from SF
		Session session = getFactory().openSession();
		Session session2 = getFactory().openSession();
		System.out.println(session == session2);// f

		// 2. begin Tx
		Transaction tx = session.beginTransaction();
		// for checking
		System.out.println("session is open " + session.isOpen() + " is connected " + session.isConnected());// t t
		try {
			Serializable empId = session.save(emp);
			// emp : PERSISTENT
			System.out.println("generated id " + empId);
			tx.commit();
			/*
			 * Hibernate performs : session.flush() --> auto dirty checking --> checks the
			 * state of L1 cache --with DB -- new entity in L1 -- DML : insert
			 */
			mesg = "Added emp details with id=" + empId;
			// for checking
			System.out.println(
					"after commit : session is open " + session.isOpen() + " is connected " + session.isConnected());// t
																														// t
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
		// for checking
		System.out.println("session is open " + session.isOpen() + " is connected " + session.isConnected());// f f

		return mesg;
	}

	@Override
	public String addNewEmployeeCurrentSession(Employee emp) {
		// emp : TRANSIENT
		String mesg = "Adding emp details failed!!!!!";
		// 1. open session from SF
		Session session = getFactory().getCurrentSession();
		Session session2 = getFactory().getCurrentSession();
		System.out.println(session == session2);// t

		// 2. begin Tx
		Transaction tx = session.beginTransaction();
		// for checking
		System.out.println("session is open " + session.isOpen() + " is connected " + session.isConnected());// t t
		try {
			// Serializable empId = session.save(emp);
			session.persist(emp);
			// emp : PERSISTENT (=> part of L1 cache , BUT rec is not yet inserted in DB)
			System.out.println("generated id " + emp.getEmpId());
			tx.commit();
			// emp : DETACHED (=> not a part of L1 cache BUT a part DB)
			/*
			 * Hibernate performs : session.flush() --> auto dirty checking --> checks the
			 * state of L1 cache --with DB -- new entity in L1 -- DML : insert ,
			 * session.close() -- L1 cache is destroyed , pooled out db cn rets to the pool
			 */
			mesg = "Added emp details with id=" + emp.getEmpId();
			// for checking
			System.out.println(
					"after commit : session is open " + session.isOpen() + " is connected " + session.isConnected());// f
																														// f
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			System.out.println(
					"after rollback : session is open " + session.isOpen() + " is connected " + session.isConnected());// f
																														// f
			/*
			 * session.close() -- L1 cache is destroyed , pooled out db cn rets to the pool
			 */
			throw e;
		}
		return mesg;
	}

	@Override
	public Employee getEmployeeById(Integer id) {
		Employee emp = null;// emp : DOES not exist !
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a tx
		Transaction tx = session.beginTransaction();
		try {
			System.out.println("session contains entity " + session.contains(emp));
			emp = session.get(Employee.class, id);// Integer --> Serializable : up casting, select
			// emp : in case of existing id : PERSISTENT (part of L1 cache , part of DB)
			// emp : null (in case of non existing id)
			System.out.println("session contains entity " + session.contains(emp));

//			emp = session.get(Employee.class, id);
//			emp = session.get(Employee.class, id);
//			emp = session.get(Employee.class, id);
//			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return emp;
	}

	@Override
	public List<Employee> getAllEmps() {
		List<Employee> empList = null;
		String jpql = "select e from Employee e";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			empList = session.createQuery(jpql, Employee.class).getResultList();
//			empList=session.createQuery(jpql, Employee.class)
//					.getResultList();
//			empList=session.createQuery(jpql, Employee.class)
//					.getResultList();
			// empList : list of persistent entities
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return empList;// empList : list of detached entities
	}

	@Override
	public List<Employee> getEmpsByJoinDateAndSalary(LocalDate beginDate, LocalDate endDate, double minSalary) {
		List<Employee> emps = null;
		String jpql = "select e from Employee e where e.joinDate between :start and :end and e.salary>:min";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			emps = session.createQuery(jpql, Employee.class).setParameter("start", beginDate)
					.setParameter("end", endDate).setParameter("min", minSalary).getResultList();
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return emps;
	}

	@Override
	public List<String> getLastNamesByEmploymentType(EmploymentType type) {
		List<String> lastNames = null;
		String jpql = "select e.lastName from Employee e where e.empType=:ty";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			lastNames = session.createQuery(jpql, String.class).setParameter("ty", type).getResultList();
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return lastNames;
	}

	@Override
	public List<Employee> getEmpDetailsByEmpType(EmploymentType type1) {
		List<Employee> emps = null;
		String jpql = "select new pojos.Employee(firstName,lastName,salary) from Employee e where e.empType=:type";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			emps = session.createQuery(jpql, Employee.class).setParameter("type", type1).getResultList();
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return emps;
	}

	@Override
	public String updateSalary(String email1, String password1, double salIncr) {
		String mesg = "Salary Updation  failed !!!!!!!!!!";
		String jpql = "select e from Employee e where e.email=:em and e.password=:pass";
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin a tx
		Transaction tx = session.beginTransaction();
		Employee emp = null;
		try {
			emp = session.createQuery(jpql, Employee.class).setParameter("em", email1).setParameter("pass", password1)
					.getSingleResult();
			// any null chking required : NO !
			// emp : PERSISTENT (doesn not exist --> persistent)
			emp.setSalary(emp.getSalary() + salIncr);// modifying the state of persistent entity
			// session.evict(emp);//emp : detached
			tx.commit(); // session.flush() --> dirty chking--> DML :update , session.close() --L1 cache
							// is destroyed n db cn rets to the pool
			mesg = "Upadated sal of " + emp.getFirstName();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		// emp.setSalary(emp.getSalary() + salIncr);//modifying the state of detached
		// entity
		return mesg;
	}

	@Override
	public String bulkUpdateSalary(LocalDate date, double salIncrement) {
		String mesg = "Bulk updations failed ....";
		String jpql = "update Employee e set e.salary=e.salary+:incr where e.joinDate <  :dt";
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin a tx
		Transaction tx = session.beginTransaction();
		try {
			int rows = session.createQuery(jpql).setParameter("incr", salIncrement).setParameter("dt", date)
					.executeUpdate();
			tx.commit();
			mesg = "Updated salaries of " + rows + " no of emps...";
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}

		return mesg;
	}

	@Override
	public String deleteEmpDetails(Integer empId) {
		String mesg = "deleting emp details failed !!!!!";
		Employee emp = null;// does not exist!
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin a tx
		Transaction tx = session.beginTransaction();
		try {
			emp = session.get(Employee.class, empId);
			if (emp != null) {
				// emp : persistent entity
				// Session API : public void delete(Object persistentRef)
				session.delete(emp);// emp : REMOVED (simply marked for removal) , BUT exists in L1 cache n in DB
				// session.evict(emp);
				mesg = "deleted emp details ....";
			}
			tx.commit();// session.flush --> auto dirty chking --> DML : delete (gone from db) -->
						// session.close --> L1 cache is destroyed (entity no longer in L1 cache) , db
						// cn rets to the pool
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		// emp : TRANSIENT
		return mesg;
	} // emp : marked for GC

	@Override
	public String storeImage(Integer empId, String imageFilePath) throws IOException {
		String mesg = "Storing image failed!!!!!";
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin a tx
		Transaction tx = session.beginTransaction();
		try {
			// 3. get emp details from it's id
			Employee emp = session.get(Employee.class, empId);
			if (emp != null) {
				// emp : persistent
				// create java.io.File class instance --> represents a file path
				File file = new File(imageFilePath);
				// validate if file exists n readable
				if (file.isFile() && file.canRead()) {
					// file : validated
					emp.setImage(FileUtils.readFileToByteArray(file));
					mesg = "Storing image successful!";
				}
			}
			tx.commit();// DML : update
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}

		return mesg;
	}

	@Override
	public String restoreImage(Integer empId, String newImageFilePath) throws IOException {
		String mesg = "Restoring image failed !!!!!!!!";
		// 1. get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. begin a tx
		Transaction tx = session.beginTransaction();
		try {
			// get emp from it's id
			Employee emp = session.get(Employee.class, empId);
			if (emp != null && emp.getImage() != null) {
				// emp : persistent
				FileUtils.writeByteArrayToFile
				(new File(newImageFilePath), emp.getImage());
				mesg="Restored image !";
			}
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}

		return mesg;
	}

}
