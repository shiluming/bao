package com.sise.bao.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * ��¼Ȩ�޿���
 * @author rola
 *
 */
public class LoginInterceptior implements Interceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("��ʼִ��Struts2 interceptor.....");
		
		return invocation.invoke();
	}

	
}
