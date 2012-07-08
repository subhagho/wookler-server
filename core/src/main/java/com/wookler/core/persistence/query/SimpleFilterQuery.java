/**
 * 
 */
package com.wookler.core.persistence.query;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.persistence.query.Query#parse(java.lang.String)
	 */
	@Override
	public void parse(String query) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.persistence.query.Query#doSelect(com.wookler.core.
	 * persistence.AbstractEntity)
	 */
	@Override
	public boolean doSelect(AbstractEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.persistence.query.Query#select(java.util.List)
	 */
	@Override
	public List<AbstractEntity> select(List<AbstractEntity> entities)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
