/**
 * 
 */
package com.wookler.core.persistence.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wookler.core.EnumInstanceState;
import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.DataManager;
import com.wookler.core.persistence.query.SimpleDbQuery;
import com.wookler.utils.AbstractParam;
import com.wookler.utils.KeyValuePair;
import com.wookler.utils.ListParam;
import com.wookler.utils.LogUtils;
import com.wookler.utils.ValueParam;
import com.wookler.utils.XMLUtils;

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

	public static final String _PARAM_DBCONFIG_ = "setup";

	public static final String _CONFIG_SETUP_VERSION_ = "db[@version]";

	public static final String _CONFIG_SETUP_ENTITIES_ = "/h2/db/entities/entity";

	public static final String _CONFIG_SETUP_INDEXES_ = "/h2/db/entities/index";

	private int cpoolsize = 10;

	private String dbconfig = null;

	private String connurl = null;

	private String username = null;

	private String password = null;

	private Connection[] conns = null;

	private Queue<Connection> freeconns = new LinkedBlockingQueue<Connection>();

	private boolean checksetup = false;

	public H2DbPersister() {
		key = this.getClass().getCanonicalName();
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
			AbstractParam pkey = params.get(_PARAM_KEY_);
			if (pkey == null)
				throw new Exception(
						"Invalid Configuration : Missing paramter ["
								+ _PARAM_KEY_ + "]");
			if (!(pkey instanceof ValueParam)) {
				throw new Exception(
						"Invalid Configuration : Invalid Parameter type for ["
								+ _PARAM_KEY_ + "]");
			}
			key = ((ValueParam) pkey).getValue();
			if (key == null || key.isEmpty())
				throw new Exception("Invalid Configuration : Param ["
						+ _PARAM_KEY_ + "] is NULL or empty.");

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
				freeconns.add(conns[ii]);
			}
			state = EnumInstanceState.Running;

			param = params.get(_PARAM_DBCONFIG_);
			if (param != null) {
				if (!(param instanceof ValueParam))
					throw new Exception(
							"Invalid Configuration : Invalid parameter type ["
									+ _PARAM_CONN_PASSWD_ + "]");
				dbconfig = ((ValueParam) param).getValue();
				checksetup = true;
			}

			log.info("Created connection pool [size=" + cpoolsize
					+ "], H2 Database [" + connurl + "]");
		} catch (Exception e) {
			state = EnumInstanceState.Exception;
			throw e;
		}
	}

	private void checkSetup() throws Exception {
		XMLConfiguration config = new XMLConfiguration(dbconfig);
		String version = config.getString(_CONFIG_SETUP_VERSION_);
		if (version == null || version.isEmpty())
			throw new Exception(
					"Invalid DB Setup Configuration : Missing parameter ["
							+ _CONFIG_SETUP_VERSION_ + "]");

		if (!checkSchema()) {
			SimpleDbQuery dbq = new SimpleDbQuery();
			List<String> createsql = dbq.getCreateTableDDL(DBVersion.class);
			Connection conn = getConnection(true);
			Statement stmnt = conn.createStatement();
			try {
				for (String sql : createsql) {
					log.debug("TABLE SQL [" + sql + "]");
					stmnt.execute(sql);
				}

				DBVersion dbv = (DBVersion) DataManager
						.newInstance(DBVersion.class);
				dbv.setVersion(version);
				save(dbv);

				NodeList nl = XMLUtils.search(_CONFIG_SETUP_ENTITIES_, config
						.getDocument().getDocumentElement());
				if (nl != null && nl.getLength() > 0) {
					for (int ii = 0; ii < nl.getLength(); ii++) {
						Element elm = (Element) nl.item(ii);
						String eclass = elm.getTextContent();
						if (eclass != null && !eclass.isEmpty()) {
							Class<?> cls = Class.forName(eclass);
							createsql = dbq.getCreateTableDDL(cls);
							for (String sql : createsql) {
								log.debug("TABLE SQL [" + sql + "]");
								stmnt.execute(sql);
							}
						}
					}
				}
				nl = XMLUtils.search(_CONFIG_SETUP_INDEXES_, config
						.getDocument().getDocumentElement());
				if (nl != null && nl.getLength() > 0) {
					for (int ii = 0; ii < nl.getLength(); ii++) {
						Element elm = (Element) nl.item(ii);
						String iclass = elm.getAttribute("entity");
						if (iclass == null || iclass.isEmpty())
							throw new Exception(
									"Invalid Configuration : Missing or empty attribute [entity]");
						Class<?> cls = Class.forName(iclass);

						String iname = elm.getAttribute("name");
						if (iname == null || iname.isEmpty())
							throw new Exception(
									"Invalid Configuration : Missing or empty attribute [name]");
						String icolumns = elm.getAttribute("columns");
						if (icolumns == null || icolumns.isEmpty())
							throw new Exception(
									"Invalid Configuration : Missing or empty attribute [columns]");
						List<KeyValuePair<String>> columns = new ArrayList<KeyValuePair<String>>();
						KeyValuePair<String> cp = new KeyValuePair<String>();
						cp.setKey(iname);
						cp.setValue(icolumns);
						columns.add(cp);

						createsql = dbq.getCreateIndexDDL(cls, columns);
						for (String sql : createsql) {
							log.debug("INDEX SQL [" + sql + "]");
							stmnt.execute(sql);
						}
					}
				}
			} finally {
				if (stmnt != null && !stmnt.isClosed())
					stmnt.close();

			}
		} else {
			List<AbstractEntity> versions = DataManager.get().read("",
					DBVersion.class);
			if (versions == null || versions.isEmpty()) {
				throw new Exception(
						"Error retrieving Schema Version. Database might be corrupted.");
			}
			for (AbstractEntity ver : versions) {
				if (ver instanceof DBVersion) {
					DBVersion dbv = (DBVersion) ver;
					if (dbv.getVersion().compareTo(version) != 0) {
						throw new Exception(
								"Database Version missmatch, Expection version ["
										+ version + "], current DB version ["
										+ dbv.getVersion() + "]");
					}
				}
			}
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
						if (!conn.isClosed()) {
							log.debug("Closing H2 connection...");
							conn.close();
						}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.persistence.AbstractPersister#postinit()
	 */
	@Override
	public void postinit() throws Exception {
		if (checksetup)
			checkSetup();
	}

}
