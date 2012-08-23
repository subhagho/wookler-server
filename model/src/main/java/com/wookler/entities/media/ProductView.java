/**
 * 
 */
package com.wookler.entities.media;

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
@Entity(recordset = "PRODUCTVIEW", isview = true, query = "select VIDEO.NAME \"VIDEO.NAME\", VIDEO.TYPE \"VIDEO.TYPE\", PRODUCT.TX_TIMESTAMP \"PRODUCT.TX_TIMESTAMP\", PRODUCT.TITLE \"PRODUCT.TITLE\", PRODUCT.DESCRIPTION \"PRODUCT.DESCRIPTION\", PRODUCT.RATING \"PRODUCT.RATING\", PRODUCT.HTML \"PRODUCT.HTML\", PRODUCT.ID \"PRODUCT.ID\", VIDEO.REFID \"VIDEO.REFID\", VIDEO.TAGS \"VIDEO.TAGS\", VIDEO.TX_TIMESTAMP \"VIDEO.TX_TIMESTAMP\", VIDEO.URI \"VIDEO.URI\", PRODUCT.URI \"PRODUCT.URI\", VIDEO.PUBLISHED \"VIDEO.PUBLISHED\", VIDEO.ID \"VIDEO.ID\", PRODUCT.RATECOUNT \"PRODUCT.RATECOUNT\", VIDEO.DESCRIPTION \"VIDEO.DESCRIPTION\", VIDEO.LENGTH \"VIDEO.LENGTH\", VIDEO.VIEWS \"VIDEO.VIEWS\", VIDEO.SOURCE \"VIDEO.SOURCE\" from SEQUENCE SEQUENCE, VIDEO VIDEO, CREATIVE PRODUCT where ((SEQUENCE.MEDIAID = VIDEO.ID) AND (SEQUENCE.CREATIVE = PRODUCT.ID))")
@XmlRootElement(name = "productview")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductView extends AbstractEntity {
	@Attribute(name = "VIDEO")
	@XmlElement(name = "video")
	@Reference(target = "com.wookler.entities.media.VideoMedia", attribute = "ID")
	private VideoMedia video;

	@Attribute(name = "PRODUCT")
	@XmlElement(name = "product")
	@Reference(target = "com.wookler.entities.media.Creative", attribute = "ID")
	private Creative product;

	/**
	 * @return the video
	 */
	public VideoMedia getVideo() {
		return video;
	}

	/**
	 * @param video
	 *            the video to set
	 */
	public void setVideo(VideoMedia video) {
		this.video = video;
	}

	/**
	 * @return the product
	 */
	public Creative getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(Creative product) {
		this.product = product;
	}

}
