package com.sise.bao.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sise.bao.utils.HibernateUtils;
import com.sise.bao.utils.Page;
import com.sise.bao.utils.ReflectionUtils;

import java.util.Map;

public class BaseCrudDao<T, PK extends Serializable> {
	
	protected Class<T> entityClass;
	
	public BaseCrudDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	public Session getSession() {
		return HibernateUtils.getCurrentSession();
	}
	
	
	
	/**
	 * ��ҳ��ȡ����
	 */
	public Page<T> findPage(final Page<T> page,String hql,final Object... objects) {
		Query q = createQuery(hql,objects);
		if(page.isAutoCount()) {
			long totalCounts = countHqlResult(hql, objects);
			page.setTotalCount(totalCounts);
		}
		setPageParameter(q,page);
		List<T> result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * ��ҳ��ȡ����
	 */
	public Page<T> findPage(final Page<T> page,String hql,final Map<String, ?> values) {
		Query q = createQuery(hql,values);
		if(page.isAutoCount()) {
			long totalCounts = countHqlResult(hql, values);
			page.setTotalCount(totalCounts);
		}
		setPageParameter(q,page);
		List<T> result = q.list();
		page.setResult(result);
		return page;
	}
	
	public List<T> findByAttr(final String proptertyName, final Object value) {
		return null;
	}
	
	public T getById(final PK id) {
		return getSession().get(entityClass, id);
	}
	
	@SuppressWarnings("unchecked")
	public PK save(final T entity) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		PK pk = (PK)session.save(entity);
		tx.commit();
		session.close();
		return pk;
	}
	
	public void update(final T entity) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		session.update(entity);
		tx.commit();
		session.close();
	}
	
	public void delete(final T entity) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		session.delete(entity);
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public PK merge(final T entity) {
		return (PK)getSession().merge(entity);
	}
	
	public T get(final PK id) {
		return getSession().load(entityClass, id);
	}
	
	
	/**
	 * ������ѯquery
	 * @param hql
	 * @param objects
	 * @return
	 */
	public Query createQuery(final String hql,final Object...objects) {
		Session session = getSession();
		Query result = getSession().createQuery(hql);
		if(objects != null) {
			for(int i=0;i<objects.length;i++) {
				result.setParameter(i, objects[i]);
			}
		}
		return result;
	}
	
	/**
	 * ִ��count��ѯ��ñ���Hql��ѯ���ܻ�õĶ�������.
	 * 
	 * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String fromHql = hql;
		// select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");
		String countHql = "";
		if (fromHql.indexOf("fetch") > 0) {
			fromHql = fromHql.replace("fetch", "");
			countHql = "select count(t) " + fromHql;
		} else {
			countHql = "select count(*) " + fromHql;
		}
		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}
	
	/**
	 * 
	 * @param hql
	 * @param objects
	 * 				�����ɱ�Ĳ�������˳���
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Object...objects) {
		return (X) createQuery(hql, objects).uniqueResult();
	}

	/**
	 * ���÷�ҳ������Query����,��������.
	 */
	protected Query setPageParameter(final Query q, final Page<T> page) {
		// hibernate��firstResult����Ŵ�0��ʼ
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * ���ݲ�ѯHQL������б���Query����.
	 *
	 * @param values
	 *            ��������,�����ư�.
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * ִ��count��ѯ��ñ���Hql��ѯ���ܻ�õĶ�������.
	 *
	 * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String fromHql = hql;
		// select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

}
