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
	@Attribute(name = "ID", keyattribute = true, size = 512)
	@XmlElement(name = "id")
	private String id;

	@Attribute(name = "EMAIL", size = 512)
	@XmlElement(name = "email")
	private String email;

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

}
