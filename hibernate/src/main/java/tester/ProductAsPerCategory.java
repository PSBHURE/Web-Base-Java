package tester;

import static utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.ProductDaoImpl;
import pojos.Product;
import pojos.ProductCategory;
import pojos.Product;
public class ProductAsPerCategory {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			ProductDaoImpl PDI=new ProductDaoImpl();
			System.out.println("enter product type");
			System.out.println(PDI.GetProductByCategory(ProductCategory.valueOf(sc.next().toUpperCase())));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
