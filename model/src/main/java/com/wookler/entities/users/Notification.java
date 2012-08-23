/**
 * 
 */
package com.wookler.entities.users;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sqewd.open.dal.api.persistence.AbstractPersistedEntity;
import com.sqewd.open.dal.api.persistence.Attribute;
import com.sqewd.open.dal.api.persistence.Entity;
import com.sqewd.open.dal.api.persistence.Reference;


/**
 * @author subhagho
 * 
 */
@Entity(recordset = "NOTIFICATION")
@XmlRootElement(name = "notification")
@XmlAccessorType(XmlAccessType.NONE)
public class Notification extends AbstractPersistedEntity {
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

	@Attribute(name = "SUBSCRIPTION", size = 512)
	@XmlElement(name = "subscription")
	@Reference(target = "com.wookler.entities.users.Subscription", attribute = "ID")
	private Subscription subscription;

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

	/**
	 * @return the subscription
	 */
	public Subscription getSubscription() {
		return subscription;
	}

	/**
	 * @param subscription
	 *            the subscription to set
	 */
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

}
