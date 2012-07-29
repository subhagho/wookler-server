/**
 * 
 */
package com.wookler.entities.users;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.Attribute;
import com.wookler.core.persistence.Entity;
import com.wookler.core.persistence.Reference;

/**
 * @author subhagho
 * 
 */
@Entity(recordset = "NOTIFICATION")
@XmlRootElement(name = "notification")
@XmlAccessorType(XmlAccessType.NONE)
public class Notification extends AbstractEntity {
	@Attribute(name = "ID", keyattribute = true, autoincr = true)
	@XmlElement(name = "id")
	private long id;

	@Attribute(name = "PROFILE", size = 512)
	@XmlElement(name = "profile")
	@Reference(target = "com.wookler.entities.users.Profile", attribute = "ID")
	private Profile profile;

	@Attribute(name = "NOTIFYSTATE", size = 128)
	@XmlElement(name = "notifystate")
	private EnumPublicationState notifystate;

	@Attribute(name = "CONTENT", size = 4096)
	@XmlElement(name = "content")
	private String content;

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
	 * @return the notifystate
	 */
	public EnumPublicationState getNotifystate() {
		return notifystate;
	}

	/**
	 * @param notifystate
	 *            the notifystate to set
	 */
	public void setNotifystate(EnumPublicationState notifystate) {
		this.notifystate = notifystate;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
