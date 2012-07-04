/**
 * 
 */
package com.wookler.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.Attribute;
import com.wookler.core.persistence.Entity;

/**
 * @author subhagho
 * 
 */
@Entity(recordset = "SEQUENCE")
@XmlRootElement(name = "sequence")
@XmlAccessorType(XmlAccessType.NONE)
public class Creative extends AbstractEntity {
	@Attribute(name = "ID")
	@XmlElement(name = "creativeid")
	private long id;

	@Attribute(name = "HTML")
	@XmlElement(name = "html")
	private String html;

	@Attribute(name = "URL")
	@XmlElement(name = "url")
	private String url;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @param html
	 *            the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
