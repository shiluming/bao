package com.sise.bao.actions;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ≤‚ ‘¿‡
 * @author rola
 *
 */
@Namespace("/")
@AllowedMethods(value={"testOne","testTwo"})
public class TestAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public String testOne() {
		System.out.println("testOne..........");
		return "test";
	}
	
	public String testTwo() {
		System.out.println("testOne..........");
		return "test";
	}
	
	public String browse() {
		System.out.println("browse ....  ");
		return "test";
	}
}
