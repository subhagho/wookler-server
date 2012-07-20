/**
 * 
 */
package com.wookler.core.persistence.query.test;

import com.wookler.core.persistence.Attribute;
import com.wookler.core.persistence.Entity;
import com.wookler.core.persistence.Reference;

/**
 * @author subhagho
 *
 */
@Entity(recordset = "REFROOT")
public class ReferenceRoot {
	@Attribute(name = "REF")
	@Reference(target = "com.wookler.core.persistence.query.test.ReferenceL2", attribute = "LV")
	private ReferenceL1 ref;

	@Attribute(name = "STR")
	private String str = null;

	/**
	 * @return the ref
	 */
	public ReferenceL1 getRef() {
		return ref;
	}

	/**
	 * @param ref
	 *            the ref to set
	 */
	public void setRef(ReferenceL1 ref) {
		this.ref = ref;
	}

	/**
	 * @return the str
	 */
	public String getStr() {
		return str;
	}

	/**
	 * @param str
	 *            the str to set
	 */
	public void setStr(String str) {
		this.str = str;
	}

}
