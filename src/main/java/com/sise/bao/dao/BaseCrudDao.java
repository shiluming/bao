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
	 * 分页获取对象
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
	 * 分页获取对象
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
	 * 创建查询query
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
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
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
	 * 				数量可变的参数，按顺序绑定
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Object...objects) {
		return (X) createQuery(hql, objects).uniqueResult();
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameter(final Query q, final Page<T> page) {
		// hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 *
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 *
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
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
