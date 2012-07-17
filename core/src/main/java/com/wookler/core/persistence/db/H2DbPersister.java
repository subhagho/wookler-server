/**
 * 
 */
package com.wookler.core.persistence.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wookler.core.EnumInstanceState;
import com.wookler.utils.AbstractParam;
import com.wookler.utils.ListParam;
import com.wookler.utils.LogUtils;
import com.wookler.utils.ValueParam;

/**
 * @author subhagho
 * 
 */
public class H2DbPersister extends AbstractDbPersister {
	private static final Logger log = LoggerFactory
			.getLogger(H2DbPersister.class);

	private static final long _DEFAULT_LOCK_TIMEOUT_ = 100;

	public static final String _PARAM_POOL_SIZE_ = "poolsize";

	public static final String _PARAM_CONN_URL_ = "url";

	public static final String _PARAM_CONN_USER_ = "user";

	public static final String _PARAM_CONN_PASSWD_ = "password";

	private int cpoolsize = 10;

	private String connurl = null;

	private String username = null;

	private String password = null;

	private Connection[] conns = null;

	private Queue<Connection> freeconns = new LinkedBlockingQueue<Connection>();

	public H2DbPersister() {
		classtype = this.getClass().getCanonicalName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.persistence.db.AbstractDbPersister#getConnection()
	 */
	@Override
	protected Connection getConnection(boolean blocking) throws Exception {
		if (state != EnumInstanceState.Running)
			throw new Exception(
					"Db Persister is not running. Either it has been disposed or errored out. Check log file for details.");
		while (true) {
			synchronized (freeconns) {
				if (freeconns.size() > 0) {
					return freeconns.remove();
				}
			}
			if (blocking)
				Thread.sleep(_DEFAULT_LOCK_TIMEOUT_);
			else
				break;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wookler.core.persistence.db.AbstractDbPersister#releaseConnection
	 * (java.sql.Connection)
	 */
	@Override
	protected void releaseConnection(Connection conn) {
		synchronized (freeconns) {
			freeconns.add(conn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wookler.core.persistence.AbstractPersister#init(com.wookler.utils
	 * .ListParam)
	 */
	@Override
	public void init(ListParam params) throws Exception {
		try {
			Class.forName("org.h2.Driver");

			AbstractParam param = params.get(_PARAM_POOL_SIZE_);
			if (param != null) {
				if (param instanceof ValueParam) {
					String ps = ((ValueParam) param).getValue();
					cpoolsize = Integer.parseInt(ps);
				}
			}
			param = params.get(_PARAM_CONN_URL_);
			if (param == null)
				throw new Exception(
						"Invalid Configuration : Missing parameter ["
								+ _PARAM_CONN_URL_ + "]");
			if (!(param instanceof ValueParam))
				throw new Exception(
						"Invalid Configuration : Invalid parameter type ["
								+ _PARAM_CONN_URL_ + "]");
			connurl = ((ValueParam) param).getValue();

			param = params.get(_PARAM_CONN_USER_);
			if (param == null)
				throw new Exception(
						"Invalid Configuration : Missing parameter ["
								+ _PARAM_CONN_USER_ + "]");
			if (!(param instanceof ValueParam))
				throw new Exception(
						"Invalid Configuration : Invalid parameter type ["
								+ _PARAM_CONN_USER_ + "]");

			username = ((ValueParam) param).getValue();

			param = params.get(_PARAM_CONN_PASSWD_);
			if (param == null)
				throw new Exception(
						"Invalid Configuration : Missing parameter ["
								+ _PARAM_CONN_PASSWD_ + "]");
			if (!(param instanceof ValueParam))
				throw new Exception(
						"Invalid Configuration : Invalid parameter type ["
								+ _PARAM_CONN_PASSWD_ + "]");

			password = ((ValueParam) param).getValue();

			conns = new Connection[cpoolsize];
			for (int ii = 0; ii < cpoolsize; ii++) {
				conns[ii] = DriverManager.getConnection(connurl, username,
						password);
				conns[ii].setAutoCommit(true);
			}
			log.info("Created connection pool [size=" + cpoolsize
					+ "], H2 Database [" + connurl + "]");
			state = EnumInstanceState.Running;
		} catch (Exception e) {
			state = EnumInstanceState.Exception;
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.InitializedHandle#dispose()
	 */
	public void dispose() {
		try {
			if (conns != null && conns.length > 0) {
				for (Connection conn : conns) {
					if (conn != null) {
						if (!conn.isClosed())
							conn.close();
					}
				}
				conns = null;
			}
			freeconns.clear();
			state = EnumInstanceState.Closed;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			LogUtils.stacktrace(log, e);
		}
	}

}
