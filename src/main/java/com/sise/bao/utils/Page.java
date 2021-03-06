package com.sise.bao.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Page<T> {

	//公共变量
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	//分页参数
	protected int pageNo = 1;
	protected int pageSize = 10;
	protected String orderBy = null;
	protected String order  = null;
	protected boolean autoCount = true;
	
	//返回结果
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
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}
	
	/**
	 * 获得排序字段,无默认值.多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}
	
	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}
	
	public Page<T> orderBy(final String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}
	
	/**
	 * 获得排序方向.
	 */
	public String getOrder() {
		return order;
	}
	
	/**
	 * 设置排序方式向.
	 * 
	 * @param order
	 *            可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		// 检查order字符串的合法值
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr))
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
		}

		this.order = StringUtils.lowerCase(order);
	}
	
	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}
	
	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}
	
	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数, 默认为true.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}
	
	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}
	
	public Page<T> autoCount(final boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}
	
	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}
	
	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}
	
	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}
	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
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
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}
	
	/**
	 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}
	
	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}
	
	/**
	 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}
	
	/**
	 * 生成简单分页下标数组，-1表示"..." <br/>
	 * 【可以通过修改下标左右间隔的值dist来修改当前页左右两端保留的页码】
	 * 
	 * <pre>
	 * 一共有n页，他有一下几种逻辑(dist=1的情况)：
	　　pageNo=1 1 2 ... n
	　　pageNo=2 1 2 3 ... n
	　　pageNo=3 1 2 3 4 ... n
	　　pageNo=4 1 ... 3 4 5 ... n
	　　pageNo=5 1 ... 4 5 6 ... n
	　　...
	　　pageNo=n-3 1 ... n-4 n-3 n-2 ... n
	　　pageNo=n-2 1 ... n-3 n-2 n-1 n
	　　pageNo=n-1 1 ... n-2 n-1 n
	　　pageNo=n 1 ... n-1 n
	 * </pre>
	 * 
	 * @return
	 */
	public List<Integer> getPageArray() {
		List<Integer> array = new LinkedList<Integer>();
		int totalPages = (int) getTotalPages();
		if (pageNo <= totalPages && pageNo > 0) {
			int dist = 2;// 下标左右间隔
			int prev = pageNo - dist;
			int next = pageNo + dist;
			array.add(1);
			if (prev > 2) {
				array.add(-1);
			} else if (totalPages >= 2) {
				array.add(2);
			}
			// next之前
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
