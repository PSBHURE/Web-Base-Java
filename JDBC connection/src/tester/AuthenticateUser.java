package tester;

import java.util.Scanner;

import dao.UserDaoImpl;
import pojos.User;

public class AuthenticateUser {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			// create user dao instance
			UserDaoImpl userDao = new UserDaoImpl();
			System.out.println("Enter email n password");
			// invoke CRUD method/s of DAO
			User user = userDao.authenticateUser(sc.next(), sc.next());
			if (user == null)
				System.out.println("Invalid Login!!!!!!!!!!!");
			else {
				System.out.println("Login successful");
				System.out.println("User details " + user);
			}

			// Clean up
			userDao.cleanUp();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}