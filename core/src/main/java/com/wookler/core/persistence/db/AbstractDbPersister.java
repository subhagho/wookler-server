/**
 * 
 */
package com.wookler.core.persistence.db;

import java.sql.Connection;
import java.util.List;

import com.wookler.core.EnumInstanceState;
import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.AbstractPersister;

/**
 * @author subhagho
 * 
 */
public abstract class AbstractDbPersister extends AbstractPersister {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.InitializedHandle#state()
	 */
	public EnumInstanceState state() {
		return state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wookler.core.persistence.AbstractPersister#read(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public List<AbstractEntity> read(String query, Class<?> type)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wookler.core.persistence.AbstractPersister#save(com.wookler.core.
	 * persistence.AbstractEntity)
	 */
	@Override
	public void save(AbstractEntity record) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.persistence.AbstractPersister#save(java.util.List)
	 */
	@Override
	public void save(List<AbstractEntity> records) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wookler.core.persistence.AbstractPersister#delete(com.wookler.core
	 * .persistence.AbstractEntity)
	 */
	@Override
	public void delete(AbstractEntity record) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * Get a handle to the DB Connection.
	 * 
	 * @param blocking
	 *            - Request connection in blocking mode.
	 * @return
	 * @throws Exception
	 */
	protected abstract Connection getConnection(boolean blocking)
			throws Exception;

	/**
	 * Release the connection back to the Queue.
	 * 
	 * @param conn
	 */
	protected abstract void releaseConnection(Connection conn);
}
