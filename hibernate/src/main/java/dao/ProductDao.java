package dao;

import java.util.List;

import pojos.Product;
import pojos.ProductCategory;

public interface ProductDao {
//add a method to insert new product details
	String addNewProduct(Product product);
//add method to get all products
	List<Product> GetAllProduct();
	List<String> GetProductByCategory(ProductCategory type);
}
