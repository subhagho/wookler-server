/**
 * 
 */
package com.wookler.core.persistence.csv;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import au.com.bytecode.opencsv.CSVReader;

import com.wookler.core.EnumInstanceState;
import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.AbstractPersister;
import com.wookler.core.persistence.AttributeReflection;
import com.wookler.core.persistence.Entity;
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
	public List<AbstractEntity> read(List<KeyValuePair<String>> columnkeys,
			Class<AbstractEntity> type) throws Exception {
		List<AbstractEntity> result = null;
		String cname = type.getCanonicalName();
		if (!cache.containsKey(cname)) {
			load(type);
		}
		List<AbstractEntity> records = cache.get(cname);
		for (AbstractEntity rec : records) {

		}
		return result;
	}

	protected void load(Class<AbstractEntity> type) throws Exception {
		if (!type.isAnnotationPresent(Entity.class))
			throw new Exception("Class [" + type.getCanonicalName()
					+ "] has not been annotated as an Entity.");
		Entity eann = (Entity) type.getAnnotation(Entity.class);
		String fname = eann.recordset() + ".CSV";
		String path = datadir + "/" + fname;

		File fi = new File(path);
		if (!fi.exists())
			throw new Exception("Cannot find CSV file [" + path
					+ "] for entity [" + type.getCanonicalName() + "]");
		List<AbstractEntity> entities = new ArrayList<AbstractEntity>();

		CSVReader reader = new CSVReader(new FileReader(path), ',', '"');
		String[] header = null;
		while (true) {
			String[] data = reader.readNext();
			if (data == null)
				break;
			if (header == null) {
				header = data;
				continue;
			}
			AbstractEntity record = parseRecord(type, header, data);
			entities.add(record);
		}
		cache.put(type.getCanonicalName(), entities);
	}

	protected AbstractEntity parseRecord(Class<AbstractEntity> type,
			String[] header, String[] data) throws Exception {
		AbstractEntity entity = (AbstractEntity) type.newInstance();

		return entity;
	}

	protected void setFieldValue(AttributeReflection attr,
			AbstractEntity entity, Field fd, String value) throws Exception {
		Class<?> type = fd.getType();
		if (type.equals(Short.class) || type.equals(short.class)) {
			attr.Setter.invoke(entity, Short.parseShort(value));
		} else if (type.equals(Integer.class) || type.equals(int.class)) {
			attr.Setter.invoke(entity, Integer.parseInt(value));
		} else if (type.equals(Long.class) || type.equals(long.class)) {
			attr.Setter.invoke(entity, Long.parseLong(value));
		} else if (type.equals(Float.class) || type.equals(float.class)) {
			attr.Setter.invoke(entity, Float.parseFloat(value));
		} else if (type.equals(Double.class) || type.equals(double.class)) {
			attr.Setter.invoke(entity, Double.parseDouble(value));
		}
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
		throw new NotImplementedException(
				"This is a dummy persister. Write operations are not supported.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.persistence.AbstractPersister#save(java.util.List)
	 */
	@Override
	public void save(List<AbstractEntity> records) throws Exception {
		throw new NotImplementedException(
				"This is a dummy persister. Write operations are not supported.");
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
		throw new NotImplementedException(
				"This is a dummy persister. Write operations are not supported.");
	}

}
