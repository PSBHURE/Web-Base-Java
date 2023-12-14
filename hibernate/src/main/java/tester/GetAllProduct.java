package tester;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.ProductDaoImpl;
import pojos.Product;
import pojos.ProductCategory;

public class GetAllProduct {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory()) {
			//create dao instance
			ProductDaoImpl dao=new ProductDaoImpl();
			System.out.println("All products list");
			dao.GetAllProduct().forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
