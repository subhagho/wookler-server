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

	private FilterConditionParser parser = null;

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

		parser = new FilterConditionParser();
		conditions = parser.parse(query);
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
		List<AbstractEntity> results = doSelect(entities);
		if (parser.getSort() != null) {
			EntityListSorter sorter = new EntityListSorter(parser.getSort());
			sorter.sort(results);
		}
		if (parser.getLimit() > 0) {
			results = results.subList(0, parser.getLimit());
		}
		return results;
	}

	public List<AbstractEntity> doSelect(List<AbstractEntity> entities)
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
