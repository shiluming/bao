package com.sise.bao.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Page<T> {

	//��������
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	//��ҳ����
	protected int pageNo = 1;
	protected int pageSize = 10;
	protected String orderBy = null;
	protected String order  = null;
	protected boolean autoCount = true;
	
	//���ؽ��
	protected List<T> result = Collections.emptyList();
	protected long totalCount = -1;
	
	public Page(){}
	
	public Page(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;
		if(pageNo < 1) {
			this.pageNo = 1;
		}
	}
	
	public Page<T> pageNo(final int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}
	
	public Page<T> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}
	
	/**
	 * ����pageNo��pageSize���㵱ǰҳ��һ����¼���ܽ�����е�λ��,��Ŵ�1��ʼ.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}
	
	/**
	 * ��������ֶ�,��Ĭ��ֵ.��������ֶ�ʱ��','�ָ�.
	 */
	public String getOrderBy() {
		return orderBy;
	}
	
	/**
	 * ���������ֶ�,��������ֶ�ʱ��','�ָ�.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}
	
	public Page<T> orderBy(final String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}
	
	/**
	 * ���������.
	 */
	public String getOrder() {
		return order;
	}
	
	/**
	 * ��������ʽ��.
	 * 
	 * @param order
	 *            ��ѡֵΪdesc��asc,��������ֶ�ʱ��','�ָ�.
	 */
	public void setOrder(final String order) {
		// ���order�ַ����ĺϷ�ֵ
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr))
				throw new IllegalArgumentException("������" + orderStr + "���ǺϷ�ֵ");
		}

		this.order = StringUtils.lowerCase(order);
	}
	
	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}
	
	/**
	 * �Ƿ������������ֶ�,��Ĭ��ֵ.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}
	
	/**
	 * ��ѯ����ʱ�Ƿ��Զ�����ִ��count��ѯ��ȡ�ܼ�¼��, Ĭ��Ϊtrue.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}
	
	/**
	 * ��ѯ����ʱ�Ƿ��Զ�����ִ��count��ѯ��ȡ�ܼ�¼��.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}
	
	public Page<T> autoCount(final boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}
	
	/**
	 * ȡ��ҳ�ڵļ�¼�б�.
	 */
	public List<T> getResult() {
		return result;
	}
	
	/**
	 * ����ҳ�ڵļ�¼�б�.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}
	
	/**
	 * ȡ���ܼ�¼��, Ĭ��ֵΪ-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}
	/**
	 * �����ܼ�¼��.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	 * ����pageSize��totalCount������ҳ��, Ĭ��ֵΪ-1.
	 */
	public long getTotalPages() {
		if (totalCount < 0)
			return -1;

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}
	/**
	 * �Ƿ�����һҳ.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}
	
	/**
	 * ȡ����ҳ��ҳ��, ��Ŵ�1��ʼ. ��ǰҳΪβҳʱ�Է���βҳ���.
	 */
	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}
	
	/**
	 * �Ƿ�����һҳ.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}
	
	/**
	 * ȡ����ҳ��ҳ��, ��Ŵ�1��ʼ. ��ǰҳΪ��ҳʱ������ҳ���.
	 */
	public int getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}
	
	/**
	 * ���ɼ򵥷�ҳ�±����飬-1��ʾ"..." <br/>
	 * ������ͨ���޸��±����Ҽ����ֵdist���޸ĵ�ǰҳ�������˱�����ҳ�롿
	 * 
	 * <pre>
	 * һ����nҳ������һ�¼����߼�(dist=1�����)��
	����pageNo=1 1 2 ... n
	����pageNo=2 1 2 3 ... n
	����pageNo=3 1 2 3 4 ... n
	����pageNo=4 1 ... 3 4 5 ... n
	����pageNo=5 1 ... 4 5 6 ... n
	����...
	����pageNo=n-3 1 ... n-4 n-3 n-2 ... n
	����pageNo=n-2 1 ... n-3 n-2 n-1 n
	����pageNo=n-1 1 ... n-2 n-1 n
	����pageNo=n 1 ... n-1 n
	 * </pre>
	 * 
	 * @return
	 */
	public List<Integer> getPageArray() {
		List<Integer> array = new LinkedList<Integer>();
		int totalPages = (int) getTotalPages();
		if (pageNo <= totalPages && pageNo > 0) {
			int dist = 2;// �±����Ҽ��
			int prev = pageNo - dist;
			int next = pageNo + dist;
			array.add(1);
			if (prev > 2) {
				array.add(-1);
			} else if (totalPages >= 2) {
				array.add(2);
			}
			// next֮ǰ
			for (int i = prev; i <= Math.min(next, totalPages); i++) {
				if (i > 2) {
					array.add(i);
				}
			}
			// next~totalPages
			if (totalPages > next + 1) {
				array.add(-1);
				array.add(totalPages);
			} else if (totalPages == next + 1) {
				array.add(totalPages);
			}
		}
		return array;
	}
}
