/**
 * 
 */
package com.wookler.entities.interactions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.sqewd.open.dal.core.persistence.Entity;
import com.sqewd.open.dal.core.persistence.Attribute;
import com.sqewd.open.dal.core.persistence.AbstractEntity;
import com.wookler.entities.media.EnumEntityType;

/**
 * @author subhagho
 * 
 */
@Entity(recordset = "ACTIVITY")
@XmlRootElement(name = "activity")
@XmlAccessorType(XmlAccessType.NONE)
public class Activity extends AbstractEntity {
	@Attribute(name = "PROFILEID")
	@XmlElement(name = "profileid")
	private long profileid;

	@Attribute(name = "ACTIVITYTYPE", size = 128)
	@XmlElement(name = "activitytype")
	private EnumUserActivity type;

	@Attribute(name = "ENTITYTYPE", size = 128)
	@XmlElement(name = "entity")
	private EnumEntityType entity;

	@Attribute(name = "REFERENCEID", size = 512)
	@XmlElement(name = "referenceid")
	private String referenceid;

	@Attribute(name = "ACTIVITY", size = 4096)
	@XmlElement(name = "activity")
	private String activity;

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
	public EnumUserActivity getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EnumUserActivity type) {
		this.type = type;
	}

	/**
	 * @return the entity
	 */
	public EnumEntityType getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(EnumEntityType entity) {
		this.entity = entity;
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
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}
}
