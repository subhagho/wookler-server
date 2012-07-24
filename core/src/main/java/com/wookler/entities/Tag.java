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
@Entity(recordset = "TAG")
@XmlRootElement(name = "tag")
@XmlAccessorType(XmlAccessType.NONE)
public class Tag extends AbstractEntity {
	@Attribute(name = "MEDIAID", keyattribute = true, size = 256)
	@XmlElement(name = "mediaid")
	private String mediaid;

	@Attribute(name = "SEQID", keyattribute = true)
	@XmlElement(name = "sequenceid")
	private long seqid;

	@Attribute(name = "NAME", keyattribute = true, size = 512)
	@XmlElement(name = "name")
	private String name;

	@Attribute(name = "VALUE", size = 512)
	@XmlElement(name = "value")
	private String value;

	/**
	 * @return the mediaid
	 */
	public String getMediaid() {
		return mediaid;
	}

	/**
	 * @param mediaid
	 *            the mediaid to set
	 */
	public void setMediaid(String mediaid) {
		this.mediaid = mediaid;
	}

	/**
	 * @return the seqid
	 */
	public long getSeqid() {
		return seqid;
	}

	/**
	 * @param seqid
	 *            the seqid to set
	 */
	public void setSeqid(long seqid) {
		this.seqid = seqid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[MEDIAID:").append(getMediaid()).append("]");
		buff.append(", [SEQID:").append(getSeqid()).append("]");
		buff.append(", [Timestamp:")
				.append((getTimestamp() != null ? getTimestamp().toString()
						: "NULL")).append("]");
		buff.append(", [NAME:").append(getName()).append("]");
		buff.append(", [NAME:").append(getValue()).append("]");
		return buff.toString();
	}

}
