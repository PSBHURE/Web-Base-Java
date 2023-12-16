package tester;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.EmployeeDaoImpl;
import pojos.Employee;
import pojos.EmploymentType;

public class GeDetailsByEmpType {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			// dao instance
			EmployeeDaoImpl dao = new EmployeeDaoImpl();
			System.out.println("Enter employement type");

			dao.getEmpDetailsByEmpType(EmploymentType.
					valueOf(sc.next().toUpperCase()))
					.forEach(e -> System.out.println(e.getFirstName()+" "+e.getLastName()+" "+e.getSalary()));
		} // JVM :sc.close, sf.close() --> DB CP --cleaned up , cns closed
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
