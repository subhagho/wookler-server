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
@Entity(recordset = "SEQUENCE")
@XmlRootElement(name = "sequence")
@XmlAccessorType(XmlAccessType.NONE)
public class Sequence extends AbstractEntity {
	@Attribute(name = "MEDIAID", keyattribute = true, size = 256)
	@XmlElement(name = "mediaid")
	private String mediaid;

	@Attribute(name = "SEQID", keyattribute = true, autoincr = true)
	@XmlElement(name = "sequenceid")
	private long seqid;

	@Attribute(name = "STARTTIME")
	@XmlElement(name = "start")
	private long starttime;

	@Attribute(name = "ENDTIME")
	@XmlElement(name = "end")
	private long endtime;

	@Attribute(name = "CREATIVE")
	@XmlElement(name = "creative")
	@Reference(target = "com.wookler.entities.media.Creative", attribute = "ID")
	private Creative creative;

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

	/**
	 * @return the seqid
	 */
	public long getSeqid() {
		return seqid;
	}

	/**
	 * @param seqid
	 *            the seqid to set
	 */
	public void setSeqid(long seqid) {
		this.seqid = seqid;
	}

	/**
	 * @return the starttime
	 */
	public long getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime
	 *            the starttime to set
	 */
	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return the endtime
	 */
	public long getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime
	 *            the endtime to set
	 */
	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[MEDIAID:").append(getMediaid()).append("]");
		buff.append(", [Timestamp:")
				.append((getTimestamp() != null ? getTimestamp().toString()
						: "NULL")).append("]");
		buff.append(", [SequenceId:").append(getSeqid()).append("]");
		buff.append(", [StartTime:").append(getStarttime()).append("]");
		buff.append(", [EndTime:").append(getEndtime()).append("]");
		buff.append(", [Creative:").append(getCreative().toString())
				.append("]");
		return buff.toString();
	}

}
