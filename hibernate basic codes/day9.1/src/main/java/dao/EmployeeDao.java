package dao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import pojos.Employee;
import pojos.EmploymentType;

public interface EmployeeDao {
//add a method to insert new emp details
	String addNewEmployee(Employee trsnsientEmp);

	// add a method to insert new emp details : getCurrentSession
	String addNewEmployeeCurrentSession(Employee trsnsientEmp);

	// add a method to retrieve emp details by it's id
	Employee getEmployeeById(Integer id);

	// add a method to get list of all emps
	List<Employee> getAllEmps();

	// add a method to get emps by join date and salary
	List<Employee> getEmpsByJoinDateAndSalary(LocalDate beginDate, LocalDate endDate, double minSalary);

	// add a method to return last names of emps of specific emp type
	List<String> getLastNamesByEmploymentType(EmploymentType type);

	// add a method to get firstName , lastname n salary from all emps of a specific
	// emp type
	List<Employee> getEmpDetailsByEmpType(EmploymentType type);

	// add a method to update emp's salary
	String updateSalary(String email, String password, double salIncr);

	// add a method to increment salaries of all emps joined before a specific date.
	// : bulk updation
	String bulkUpdateSalary(LocalDate date, double salIncrement);

	// add a method to delete emp details
	String deleteEmpDetails(Integer empId);

	// add a method to store image in db , for existing emp
	String storeImage(Integer empId, String imageFilePath) throws IOException;
	// add a method to restore image from db , for existing emp
		String restoreImage(Integer empId, String newImageFilePath) throws IOException;

}
