package tester;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.EmployeeDaoImpl;
import pojos.Employee;
import pojos.EmploymentType;

public class AddNewEmployeeWithGetCurrentSession {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			// dao instance
			EmployeeDaoImpl dao = new EmployeeDaoImpl();
			System.out.println(
					"Enter emp details  : firstName,  lastName,  email,  password,  joinDate  empType,  salary");
			Employee newEmp = new Employee(sc.next(), sc.next(), sc.next(), sc.next(), LocalDate.parse(sc.next()),
					EmploymentType.valueOf(sc.next().toUpperCase()), sc.nextDouble());
			System.out.println("emp id "+newEmp.getEmpId());//null
		//	newEmp.setEmpId(50);
			System.out.println(dao.addNewEmployeeCurrentSession(newEmp));
		} // JVM :sc.close, sf.close() --> DB CP --cleaned up , cns closed
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
