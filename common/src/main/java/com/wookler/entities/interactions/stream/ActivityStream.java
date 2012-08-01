/**
 * 
 */
package com.wookler.entities.interactions.stream;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.wookler.entities.users.Profile;

/**
 * @author subhagho
 *
 */
@XmlRootElement(name = "activityStream")
@XmlAccessorType(XmlAccessType.NONE)
public class ActivityStream {
	private Date published;
	
	private Profile actor;
	
	private EnumActivityVerbs verb;
	
	private ActivityObject object;
	
	private ActivityTarget target;
	
}
