package com.sise.bao.service;

import com.sise.bao.dao.BookCategoryDao;
import com.sise.bao.entity.BookCategory;

public class BookCategoryManager {

	private BookCategoryDao bookCategoryDao = new BookCategoryDao();
	
	public void update(BookCategory entity) {
		bookCategoryDao.update(entity);
	}

	public Long save(BookCategory entity) {
		return bookCategoryDao.save(entity);
	}
}
