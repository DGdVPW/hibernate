package com.hibernate.test;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;

import com.hibernate.model.fruit.Fruit;


public class FruitTest {

	private static void save(Session session) {
		Fruit data = new Fruit();
		data.setId("k003");
		data.setName("â��");
		data.setSource("��ɽ");
		data.setPrice(10.0);
		data.setNumbers(200);
		data.setImage("��");
		try {
			session.beginTransaction();
			session.save(data);
			session.getTransaction().commit();
		} catch (Exception e) {
			Logger.getLogger(Fruit.class).info(e.getMessage());
		}
	}

	private static void delete(Session session) {
		Fruit data = (Fruit) session.load(Fruit.class, "k002");// get��load�����Բ�
		if (data != null) {
			session.beginTransaction();
			session.delete(data);
			session.getTransaction().commit();
		}
	}

	private static void update(Session session) {
		Fruit data = (Fruit) session.load(Fruit.class, "k002");
		if (data != null) {
			data.setName("���ܹ�");
			data.setPrice(11.1);
			data.setNumbers(99);
			try {
				session.beginTransaction();
				session.update(data);
				session.getTransaction().commit();
			} catch (Exception e) {
				Logger.getLogger(Fruit.class).info(e.getMessage());
			}
		}
	}

	private static void queryFruit(Session session) {
		// �鵥������
		Fruit data = (Fruit) session.get(Fruit.class, "k002");
		Logger.getLogger(Fruit.class).info(data.getName() + data.getPrice() + data.getNumbers());
	}

	public static void queryFruits(SessionFactory factory,String name){ 
		try{
			Session session = factory.openSession();
			session.beginTransaction();
			Query s=session.createQuery("");
			Query q = session.getNamedQuery("findFruitById");  
			q.setString("id", "k003");  
			List<Fruit> list = q.list(); 
			for(Fruit f:list){
				Logger.getLogger(Fruit.class).info(f.getName() + f.getPrice() + f.getNumbers());
	        }
		}catch(Exception e){
			Logger.getLogger(Fruit.class).info(e.getMessage());
		}
	}  
	
	public static void main(String[] args) {
		// ��ȡhibernate.cfg.xml�����ã�����Hiberna�����
		Configuration config = new Configuration().configure();
		// �������ã�����session����
		SessionFactory factory = config.buildSessionFactory();
		// �ù�������session
		Session session = factory.openSession();
		try {
			queryFruits(factory,"â��");
			queryFruit(session);
			save(session);
			update(session);
			queryFruit(session);
			delete(session);
			queryFruit(session);
		} catch (Exception e) {
			Logger.getLogger(Fruit.class).info(e.getMessage());
		}
		session.close();
	}
}
