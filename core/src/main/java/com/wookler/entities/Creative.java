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
 * Creative is the product/product display creative that is rendered.
 * 
 * @author subhagho
 * 
 */
@Entity(recordset = "CREATIVE")
@XmlRootElement(name = "creative")
@XmlAccessorType(XmlAccessType.NONE)
public class Creative extends AbstractEntity {
	@Attribute(name = "ID", keyattribute = true, size = 256)
	@XmlElement(name = "creativeid")
	private String id;

	// Max HTML size 10K
	@Attribute(name = "HTML", size = 10 * 1024)
	@XmlElement(name = "html")
	private String html;

	@Attribute(name = "URI", size = 1024)
	@XmlElement(name = "uri")
	private String url;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[ID:").append(getId()).append("]");
		buff.append(", [Timestamp:")
				.append((getTimestamp() != null ? getTimestamp().toString()
						: "NULL")).append("]");
		buff.append(", [HTML:").append(getHtml()).append("]");
		buff.append(", [URL:").append(getUrl()).append("]");
		return buff.toString();
	}

}
