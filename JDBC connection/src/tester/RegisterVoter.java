package tester;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

import dao.UserDaoImpl;
import pojos.User;

public class RegisterVoter {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			// create user dao instance
			UserDaoImpl userDao = new UserDaoImpl();
			System.out.println("Enter first_name , last_name , email , password , dob");
			User voter = new User(sc.next(), sc.next(), sc.next(), sc.next(), Date.valueOf(sc.next()));
			// validate voter's age
			int age = Period.between(voter.getDob().toLocalDate(), LocalDate.now()).getYears();
			if (age >= 21) {
				// invoke CRUD method of DAO
				System.out.println(userDao.registerNewVoter(voter));
			} else
				System.out.println("Invalid age !!!!!!!!!!!");
			// Clean up
			userDao.cleanUp();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}