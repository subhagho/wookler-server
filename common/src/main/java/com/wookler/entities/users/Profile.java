/**
 * 
 */
package com.wookler.entities.users;

import java.util.Date;

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
@Entity(recordset = "PROFILE")
@XmlRootElement(name = "profile")
@XmlAccessorType(XmlAccessType.NONE)
public class Profile extends AbstractEntity {
	@Attribute(name = "ID", keyattribute = true, size = 512, autoincr = true)
	@XmlElement(name = "id")
	private String id;

	@Attribute(name = "DISPLAYNAME", size = 512)
	@XmlElement(name = "displayName")
	private String displayname;

	@Attribute(name = "EMAIL", size = 512)
	@XmlElement(name = "email")
	private String email;

	@Attribute(name = "PROFILEURL", size = 512)
	@XmlElement(name = "url")
	private String profileurl;

	@Attribute(name = "IMAGEURL", size = 512)
	@XmlElement(name = "imageurl")
	private String imageurl;

	@Attribute(name = "AUTHSOURCE", size = 512)
	@XmlElement(name = "authsource")
	private EnumUserAuthSource authsource;

	@Attribute(name = "LASTLOGIN", size = 512)
	@XmlElement(name = "lastlogin")
	private Date lastLogin;

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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the authsource
	 */
	public EnumUserAuthSource getAuthsource() {
		return authsource;
	}

	/**
	 * @param authsource
	 *            the authsource to set
	 */
	public void setAuthsource(EnumUserAuthSource authsource) {
		this.authsource = authsource;
	}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin
	 *            the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the displayname
	 */
	public String getDisplayname() {
		return displayname;
	}

	/**
	 * @param displayname
	 *            the displayname to set
	 */
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	/**
	 * @return the imageurl
	 */
	public String getImageurl() {
		return imageurl;
	}

	/**
	 * @param imageurl
	 *            the imageurl to set
	 */
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	/**
	 * @return the profileurl
	 */
	public String getProfileurl() {
		return profileurl;
	}

	/**
	 * @param profileurl
	 *            the profileurl to set
	 */
	public void setProfileurl(String profileurl) {
		this.profileurl = profileurl;
	}

}
