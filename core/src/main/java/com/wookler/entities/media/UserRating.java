/**
 * 
 */
package com.wookler.entities.media;

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
@Entity(recordset = "USERRATING")
@XmlRootElement(name = "userrating")
@XmlAccessorType(XmlAccessType.NONE)
public class UserRating extends AbstractEntity {
	@Attribute(name = "PROFILEID", keyattribute = true)
	@XmlElement(name = "profileid")
	private long profileid;

	@Attribute(name = "TYPE", keyattribute = true, size = 128)
	@XmlElement(name = "type")
	private EnumEntityType type;

	@Attribute(name = "REFERENCEID", keyattribute = true, size = 512)
	@XmlElement(name = "referenceid")
	private String referenceid;

	@Attribute(name = "RATING")
	@XmlElement(name = "rating")
	private double rating;

	/**
	 * @return the profileid
	 */
	public long getProfileid() {
		return profileid;
	}

	/**
	 * @param profileid
	 *            the profileid to set
	 */
	public void setProfileid(long profileid) {
		this.profileid = profileid;
	}

	/**
	 * @return the type
	 */
	public EnumEntityType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EnumEntityType type) {
		this.type = type;
	}

	/**
	 * @return the referenceid
	 */
	public String getReferenceid() {
		return referenceid;
	}

	/**
	 * @param referenceid
	 *            the referenceid to set
	 */
	public void setReferenceid(String referenceid) {
		this.referenceid = referenceid;
	}

	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}
}
