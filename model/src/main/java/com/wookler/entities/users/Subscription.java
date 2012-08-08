/**
 * 
 */
package com.wookler.entities.users;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sqewd.open.dal.api.persistence.AbstractEntity;
import com.sqewd.open.dal.api.persistence.Attribute;
import com.sqewd.open.dal.api.persistence.Entity;
import com.sqewd.open.dal.api.persistence.Reference;


/**
 * @author subhagho
 * 
 */
@Entity(recordset = "SUBSCRIPTION")
@XmlRootElement(name = "subscription")
@XmlAccessorType(XmlAccessType.NONE)
public class Subscription extends AbstractEntity {
	public static final String _SUBSCRIBE_ALL_ = "ALL";

	@Attribute(name = "ID", size = 256, autoincr = true)
	@XmlElement(name = "id")
	private String id;

	@Attribute(name = "PROFILE", size = 512)
	@XmlElement(name = "profile")
	@Reference(target = "com.wookler.entities.users.Profile", attribute = "ID")
	private Profile profile;

	@Attribute(name = "TAXONOMY", size = 1024)
	@XmlElement(name = "taxonomy")
	private String taxonomy;

	@Attribute(name = "VALUE", size = 1024)
	@XmlElement(name = "value")
	private String value;

	/**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * @param profile
	 *            the profile to set
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	/**
	 * @return the taxonomy
	 */
	public String getTaxonomy() {
		return taxonomy;
	}

	/**
	 * @param taxonomy
	 *            the taxonomy to set
	 */
	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
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

}
