package com.sise.bao.service;

import com.sise.bao.VO.BookSearchVO;
import com.sise.bao.dao.BookDao;
import com.sise.bao.entity.Book;
import com.sise.bao.utils.Page;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class BookServiceManager {

	private BookDao bookDao = new BookDao();

	public Long save(Book entity) {
		return bookDao.save(entity);
	}

	public void update(Book entity) {
		bookDao.update(entity);
	}

	public Page<Book> search(Page page, BookSearchVO vo) {

		StringBuilder sb = new StringBuilder("from Book where 1 = 1");
		List<String> paramters = new ArrayList<String>();
		if(StringUtils.isNotEmpty(vo.getBookName())) {
			sb.append(" and bookName like ? ");
			paramters.add("%"+vo.getBookName()+"%");
		}

		page = bookDao.findPage(page,sb.toString(),paramters.toArray());
		return page;
	}

	public void delete(Book entity) {
		bookDao.delete(entity);
	}
	
	public Book getById(Long id) {
		return bookDao.getById(id);
	}



}
