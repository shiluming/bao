package com.sise.bao.hibernate;

import static org.junit.Assert.*;

import com.sise.bao.entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.sise.bao.utils.HibernateUtils;

public class HibernateTestCase {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testInit() {
		Session session = HibernateUtils.getCurrentSession();
		session.getTransaction();
		session.beginTransaction();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
	}
	
	public static void main(String[] args) {
		Session session = HibernateUtils.getCurrentSession();
		session.getTransaction();
		session.beginTransaction();
		Transaction tx = session.beginTransaction();
		System.out.println("session = " + session);
		System.out.println("test");
		Book book = new Book();
		book.setAuthor("dsfdsf11112");
		book.setBookName("dfdsf1111");
		session.save(book);
		tx.begin();
		tx.commit();
		session.close();
	}

}
