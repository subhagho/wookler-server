/**
 * 
 */
package com.wookler.demo.struts;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author subhagho
 * 
 */
public class WelcomeAction extends ActionSupport {
	private static final Logger log = LoggerFactory
			.getLogger(WelcomeAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7603681490357501469L;
	private String userName;
	private String message;

	@Action(value = "output", results = { @Result(name = "success", location = "/results/successPage.jsp") })
	public String execute() {
		message = "Welcome " + userName + " !";
		log.debug("Message : " + message);
		return "success";
	}

	public void setUserName(String userName) {
		log.debug("Username : " + userName);
		this.userName = userName;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public String getMessage() {
		return message;
	}
}
