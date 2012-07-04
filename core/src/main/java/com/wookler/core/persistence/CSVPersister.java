/**
 * 
 */
package com.wookler.core.persistence;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import com.wookler.core.EnumInstanceState;
import com.wookler.utils.AbstractParam;
import com.wookler.utils.KeyValuePair;
import com.wookler.utils.ListParam;
import com.wookler.utils.ValueParam;

/**
 * @author subhagho
 * 
 */
public class CSVPersister extends AbstractPersister {
	public static final String _PARAM_DATADIR_ = "datadir";
	public static final String _PARAM_KEY_ = "key";

	private String key;
	private String datadir;
	private EnumInstanceState state = EnumInstanceState.Unknown;

	private HashMap<String, List<AbstractEntity>> cache = new HashMap<String, List<AbstractEntity>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.InitializedHandle#key()
	 */
	public String key() {
		return key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.InitializedHandle#init(com.wookler.utils.ListParam)
	 */
	public void init(ListParam param) throws Exception {
		try {
			AbstractParam pkey = param.get(_PARAM_KEY_);
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

			AbstractParam pdd = param.get(_PARAM_DATADIR_);
			if (pdd == null)
				throw new Exception(
						"Invalid Configuration : Missing paramter ["
								+ _PARAM_DATADIR_ + "]");
			if (!(pdd instanceof ValueParam)) {
				throw new Exception(
						"Invalid Configuration : Invalid Parameter type for ["
								+ _PARAM_DATADIR_ + "]");
			}
			datadir = ((ValueParam) pdd).getValue();
			if (datadir == null || datadir.isEmpty())
				throw new Exception("Invalid Configuration : Param ["
						+ _PARAM_DATADIR_ + "] is NULL or empty.");

			state = EnumInstanceState.Running;
		} catch (Exception e) {
			state = EnumInstanceState.Exception;
			throw e;
		}
	}

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
	 * @see com.wookler.core.persistence.AbstractPersister#read(java.util.List)
	 */
	@Override
	public List<AbstractEntity> read(List<KeyValuePair<String>> columnkeys)
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
		throw new NotImplementedException("This is a dummy persister. Write operations are not supported.");
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
		throw new NotImplementedException("This is a dummy persister. Write operations are not supported.");
	}

}
