package tester;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.ProductDaoImpl;
import pojos.Product;
import pojos.ProductCategory;

public class AddProduct {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			System.out.println(
					"Enter product details  : name,  category,  " + "price,  stock,  productDesc, manufactureDate ");
			Product p = new Product(sc.next(), ProductCategory.valueOf(sc.next().toUpperCase()), sc.nextDouble(),
					sc.nextInt(), sc.next(), LocalDate.parse(sc.next()));
			//create dao instance
			ProductDaoImpl dao=new ProductDaoImpl();
			//invoke dao's method
			System.out.println(dao.addNewProduct(p));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
