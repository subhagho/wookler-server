/**
 * 
 */
package com.wookler.entities.users;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sqewd.open.dal.api.persistence.Attribute;
import com.sqewd.open.dal.api.persistence.Entity;
import com.sqewd.open.dal.api.persistence.Reference;
import com.wookler.entities.media.EnumMediaType;
import com.wookler.entities.media.RatedEntity;

/**
 * Entity defines a user contribution for Tagging.
 * 
 * @author subhagho
 * 
 */
@Entity(recordset = "CONTRIBUTION")
@XmlRootElement(name = "contribution")
@XmlAccessorType(XmlAccessType.NONE)
public class Contribution extends RatedEntity {

	@Attribute(name = "ID", keyattribute = true, autoincr = true)
	@XmlElement(name = "id")
	private long id;

	@Attribute(name = "PROFILE", size = 512)
	@XmlElement(name = "profile")
	@Reference(target = "com.wookler.entities.users.Profile", attribute = "ID")
	private Profile profile;

	@Attribute(name = "TYPE", size = 1024)
	@XmlElement(name = "type")
	private EnumContributionType type;

	@Attribute(name = "TAXONOMY", size = 1024)
	@XmlElement(name = "taxonomy")
	private String taxonomy;

	@Attribute(name = "VALUE", size = 1024)
	@XmlElement(name = "value")
	private String value;

	@Attribute(name = "TEXT", size = 4096)
	@XmlElement(name = "text")
	private String text;

	@Attribute(name = "CONTRIBSTATE", size = 64)
	@XmlElement(name = "contribstate")
	private EnumContribState contribstate = EnumContribState.Pending;

	@Attribute(name = "MEDIATYPE", size = 64)
	@XmlElement(name = "mediatype")
	private EnumMediaType mediatype;

	@Attribute(name = "MEDIAID", size = 256)
	@XmlElement(name = "mediaid")
	private String mediaid;

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
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the contribstate
	 */
	public EnumContribState getContribstate() {
		return contribstate;
	}

	/**
	 * @param contribstate
	 *            the contribstate to set
	 */
	public void setContribstate(EnumContribState contribstate) {
		this.contribstate = contribstate;
	}

	/**
	 * @return the type
	 */
	public EnumContributionType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EnumContributionType type) {
		this.type = type;
	}

	/**
	 * @return the mediatype
	 */
	public EnumMediaType getMediatype() {
		return mediatype;
	}

	/**
	 * @param mediatype
	 *            the mediatype to set
	 */
	public void setMediatype(EnumMediaType mediatype) {
		this.mediatype = mediatype;
	}

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
}
