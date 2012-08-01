/**
 * 
 */
package com.wookler.entities.media;

import javax.xml.bind.annotation.XmlElement;

import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.Attribute;

/**
 * @author subhagho
 * 
 */
public class RatedEntity extends AbstractEntity {
	@Attribute(name = "RATING")
	@XmlElement(name = "rating")
	private double rating = 0.0;

	@Attribute(name = "RATECOUNT")
	@XmlElement(name = "ratecount")
	private long ratercount = 0;

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

	/**
	 * @return the ratecount
	 */
	public long getRatercount() {
		return ratercount;
	}

	/**
	 * @param ratecount
	 *            the ratecount to set
	 */
	public void setRatercount(long ratecount) {
		this.ratercount = ratecount;
	}

	/**
	 * Add a new rating to this tag.
	 * 
	 * @param rating
	 */
	public void addRating(double rating) {
		double total = this.rating * ratercount;
		total += rating;
		ratercount++;

		this.rating = total / ratercount;
	}

}
