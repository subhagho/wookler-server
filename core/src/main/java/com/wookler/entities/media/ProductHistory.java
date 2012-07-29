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
import com.wookler.core.persistence.Reference;

/**
 * @author subhagho
 * 
 */
@Entity(recordset = "PRODUCTHISTORY")
@XmlRootElement(name = "producthistory")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductHistory extends AbstractEntity {
	@Attribute(name = "CREATIVE", keyattribute = true, size = 256)
	@Reference(target = "com.wookler.entities.media.Creative", attribute = "ID")
	@XmlElement(name = "creativeid")
	private Creative creative;

	@Attribute(name = "DAILYCOUNT")
	private long countDaily;

	@Attribute(name = "WEEKLYCOUNT")
	private long countWeekly;

	@Attribute(name = "MONTHLYCOUNT")
	private long countMonthly;

	@Attribute(name = "TOTALVIEWS")
	private long totalViews;

	/**
	 * @return the creative
	 */
	public Creative getCreative() {
		return creative;
	}

	/**
	 * @param creative
	 *            the creative to set
	 */
	public void setCreative(Creative creative) {
		this.creative = creative;
	}

	/**
	 * @return the countDaily
	 */
	public long getCountDaily() {
		return countDaily;
	}

	/**
	 * @param countDaily
	 *            the countDaily to set
	 */
	public void setCountDaily(long countDaily) {
		this.countDaily = countDaily;
	}

	/**
	 * @return the countWeekly
	 */
	public long getCountWeekly() {
		return countWeekly;
	}

	/**
	 * @param countWeekly
	 *            the countWeekly to set
	 */
	public void setCountWeekly(long countWeekly) {
		this.countWeekly = countWeekly;
	}

	/**
	 * @return the countMonthly
	 */
	public long getCountMonthly() {
		return countMonthly;
	}

	/**
	 * @param countMonthly
	 *            the countMonthly to set
	 */
	public void setCountMonthly(long countMonthly) {
		this.countMonthly = countMonthly;
	}

	/**
	 * @return the totalViews
	 */
	public long getTotalViews() {
		return totalViews;
	}

	/**
	 * @param totalViews
	 *            the totalViews to set
	 */
	public void setTotalViews(long totalViews) {
		this.totalViews = totalViews;
	}

}
