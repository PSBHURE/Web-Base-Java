package dao;

import pojos.Product;
import pojos.ProductCategory;

import org.hibernate.*;
import static utils.HibernateUtils.getFactory;

import java.util.List;

public class ProductDaoImpl implements ProductDao {

	@Override
	public String addNewProduct(Product product) {
		String mesg="Adding product failed!!!!";
		Session session= getFactory().openSession();
		Transaction tx=session.beginTransaction();
		System.out.println("session is open="+session.isOpen()+"is connected="+session.isConnected());
		try {
		session.save(product);
		tx.commit();
		mesg="product added with ID"+product.getProductId();
		}
		catch(RuntimeException e)
		{
			if(tx!=null)
				tx.rollback();
			throw e;
		}
		return mesg;
	}

	@Override
	public List<Product> GetAllProduct() {
		List<Product> products=null;
		String jpql="select p from Product p";
		//1.get session from SF
		Session session =getFactory().getCurrentSession();
		//2. begin a tx
		Transaction tx=session.beginTransaction();
		try {
			products=session.createQuery(jpql,Product.class).getResultList();
			
			tx.commit();
		} catch (RuntimeException e) {
			if(tx != null)
				tx.rollback();
			throw e;
		}
	
		return products;
	}

	@Override
	public List<String> GetProductByCategory(ProductCategory type) {
		List<String>product=null;
		String jpql="select e.name from Product e where e.category=:ty";
		Session session =getFactory().getCurrentSession();
		Transaction tx=session.beginTransaction();
		try {
			product = session.createQuery(jpql, String.class).setParameter("ty", type).getResultList();
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return product;
	}

	

}
