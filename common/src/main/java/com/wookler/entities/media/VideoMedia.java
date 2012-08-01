/**
 * 
 */
package com.wookler.entities.media;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.wookler.core.persistence.Attribute;
import com.wookler.core.persistence.Entity;

/**
 * Class represents a media type Video.
 * 
 * @author subhagho
 * 
 */
@Entity(recordset = "VIDEO")
@XmlRootElement(name = "video")
@XmlAccessorType(XmlAccessType.NONE)
public class VideoMedia extends Media {
	@Attribute(name = "NAME", size = 512)
	@XmlElement(name = "name")
	private String name;

	@Attribute(name = "DESC", size = 1024)
	@XmlElement(name = "description")
	private String description;

	@Attribute(name = "LENGTH")
	@XmlElement(name = "length")
	private long length;

	@Attribute(name = "URI", size = 1024)
	@XmlElement(name = "uri")
	private String location;

	@Attribute(name = "SOURCE")
	@XmlElement(name = "source")
	private EnumVideoSource source;

	public VideoMedia() {
		type = EnumMediaType.Video;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(long length) {
		this.length = length;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the source
	 */
	public EnumVideoSource getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(EnumVideoSource source) {
		this.source = source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("[ID:").append(getId()).append("]");
		buff.append(", [Timestamp:")
				.append((getTimestamp() != null ? getTimestamp().toString()
						: "NULL")).append("]");
		buff.append(", [Name:").append(getName()).append("]");
		buff.append(", [Type:").append(getType()).append("]");
		buff.append(", [Description:").append(getDescription()).append("]");
		buff.append(", [Length:").append(getLength()).append("]");
		buff.append(", [Location:").append(getLocation()).append("]");
		buff.append(", [Source:").append(getSource()).append("]");
		if (getTags() != null && getTags().length > 0) {
			buff.append("[TAGS {");
			boolean first = true;
			for (String tag : getTags()) {
				if (first)
					first = false;
				else
					buff.append(", ");
				buff.append(tag);
			}
			buff.append("}]");
		}
		return buff.toString();
	}

}
