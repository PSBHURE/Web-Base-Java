package tester;

import java.util.Scanner;

import dao.UserDaoImpl;
import pojos.User;

public class DeleteVoterDetails {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			// create user dao instance
			UserDaoImpl userDao = new UserDaoImpl();
			System.out.println("Enter email ");
			// invoke CRUD method/s of DAO
			System.out.println(userDao.deleteVoterDetails(sc.next()));
			// Clean up
			userDao.cleanUp();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}