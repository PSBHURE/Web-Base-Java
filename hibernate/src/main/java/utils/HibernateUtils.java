package utils;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	// to create singleton , immutable , inherently thrd safe , heavy weight
	// SessionFactory object
	private static SessionFactory factory;
	static {
		System.out.println("in static init block");
		factory=new Configuration() //empty config
				.configure() //config obj populated from hibernate.cfg.xml
				.buildSessionFactory();
	}
	//getter
	public static SessionFactory getFactory() {
		return factory;
	}
	
	
	

}
