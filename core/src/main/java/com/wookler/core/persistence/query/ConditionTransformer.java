/**
 * 
 */
package com.wookler.core.persistence.query;

/**
 * @author subhagho
 * 
 */
public interface ConditionTransformer {
	/**
	 * Transform a Condition to a target query format.
	 * 
	 * @param condition
	 *            - Filter Condition to transform.
	 * @param type
	 *            - AbstractEntity Class type
	 * 
	 * @return
	 * @throws Exception
	 */
	public String transform(AbstractCondition condition, Class<?> type)
			throws Exception;
}
