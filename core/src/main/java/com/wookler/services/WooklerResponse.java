/**
 * 
 */
package com.wookler.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author subhagho
 * 
 */
@XmlRootElement(name = "video")
@XmlAccessorType(XmlAccessType.NONE)
public class WooklerResponse {
	@XmlElement(name = "state")
	private EnumResponseState state = EnumResponseState.Success;
	@XmlElement(name = "message")
	private String message = null;
	@XmlElement(name = "request")
	private String request = null;
	@XmlElement(name = "data")
	private Object data = null;

	/**
	 * @return the state
	 */
	public EnumResponseState getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(EnumResponseState state) {
		this.state = state;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
