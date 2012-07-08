/**
 * 
 */
package com.wookler.core.persistence.query;

import java.util.ArrayList;
import java.util.List;

import com.wookler.core.persistence.AbstractEntity;

/**
 * Simple Object Query. All specified conditions are used to filter the input
 * result-set.
 * 
 * @author subhagho
 * 
 */
public class SimpleFilterQuery extends Query {
	private List<FilterCondition> conditions = null;

	private ConditionMatcher matcher = new ConditionMatcher();

	/**
	 * Get the parsed filter conditions.
	 * 
	 * @return
	 */
	public List<FilterCondition> filters() {
		return conditions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.persistence.query.Query#parse(java.lang.String)
	 */
	@Override
	public void parse(String query) throws Exception {
		if (query == null || query.isEmpty())
			return;

		String[] filters = query.split(";");
		if (filters == null || filters.length <= 0)
			return;
		FilterConditionParser parser = new FilterConditionParser();
		conditions = new ArrayList<FilterCondition>();
		for (String filter : filters) {
			FilterCondition cond = parser.parse(filter);
			conditions.add(cond);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.persistence.query.Query#doSelect(com.wookler.core.
	 * persistence.AbstractEntity)
	 */
	@Override
	public boolean doSelect(AbstractEntity entity) throws Exception {
		if (conditions != null && conditions.size() > 0) {
			for (FilterCondition condition : conditions) {
				if (!matcher.match(entity, condition.getColumn(),
						condition.getComparator(), condition.getValue())) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.persistence.query.Query#select(java.util.List)
	 */
	@Override
	public List<AbstractEntity> select(List<AbstractEntity> entities)
			throws Exception {
		if (conditions != null && conditions.size() > 0) {
			List<AbstractEntity> filtered = new ArrayList<AbstractEntity>();
			for (AbstractEntity entity : entities) {
				boolean select = true;
				for (FilterCondition condition : conditions) {
					if (!matcher.match(entity, condition.getColumn(),
							condition.getComparator(), condition.getValue())) {
						select = false;
						break;
					}
				}
				if (select)
					filtered.add(entity);
			}
			return filtered;
		}
		return entities;
	}
}
