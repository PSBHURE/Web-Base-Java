package tester;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.EmployeeDaoImpl;
import pojos.Employee;
import pojos.EmploymentType;

public class GetEmpDetailsById {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			// dao instance
			EmployeeDaoImpl dao = new EmployeeDaoImpl();
			System.out.println("Enter emp id");
			System.out.println(dao.getEmployeeById(sc.nextInt()));
		} // JVM :sc.close, sf.close() --> DB CP --cleaned up , cns closed
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
