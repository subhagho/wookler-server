/**
 * 
 */
package com.wookler.core.persistence.csv;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.NotImplementedException;

import au.com.bytecode.opencsv.CSVReader;

import com.wookler.core.EnumInstanceState;
import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.AbstractPersister;
import com.wookler.core.persistence.AttributeReflection;
import com.wookler.core.persistence.DataManager;
import com.wookler.core.persistence.Entity;
import com.wookler.core.persistence.EnumPrimitives;
import com.wookler.core.persistence.EnumRefereceType;
import com.wookler.core.persistence.ReflectionUtils;
import com.wookler.core.persistence.query.SimpleFilterQuery;
import com.wookler.utils.AbstractParam;
import com.wookler.utils.DateUtils;
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
	private HashMap<String, List<AbstractEntity>> cache = new HashMap<String, List<AbstractEntity>>();

	public CSVPersister() {
		classtype = this.getClass().getCanonicalName();
	}

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
	public List<AbstractEntity> read(String query, Class<?> type)
			throws Exception {
		List<AbstractEntity> result = null;
		String cname = type.getCanonicalName();
		if (!cache.containsKey(cname)) {
			load(type);
		}
		List<AbstractEntity> records = cache.get(cname);
		if (query != null && !query.isEmpty()) {
			SimpleFilterQuery filter = new SimpleFilterQuery();
			filter.parse(query);
			result = filter.select(records);
		} else {
			result = records;
		}
		return result;
	}

	protected void load(Class<?> type) throws Exception {
		if (!type.isAnnotationPresent(Entity.class))
			throw new Exception("Class [" + type.getCanonicalName()
					+ "] has not been annotated as an Entity.");
		synchronized (cache) {
			if (cache.containsKey(type.getCanonicalName()))
				return;

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
	}

	protected AbstractEntity parseRecord(Class<?> type, String[] header,
			String[] data) throws Exception {
		AbstractEntity entity = (AbstractEntity) type.newInstance();

		for (int ii = 0; ii < header.length; ii++) {
			AttributeReflection attr = ReflectionUtils.get().getAttribute(type,
					header[ii]);
			if (attr.Convertor != null) {
				attr.Convertor.load(entity, attr.Column, data[ii]);
			} else if (attr.Reference == null) {
				setFieldValue(entity, attr.Field, data[ii]);
			} else {
				String query = attr.Reference.Field + "=" + data[ii];
				List<AbstractEntity> refs = DataManager.get().read(query,
						Class.forName(attr.Reference.Class));
				if (refs != null && refs.size() > 0) {
					if (attr.Reference.Type == EnumRefereceType.One2One) {
						setFieldValue(entity, attr.Field, refs.get(0));
					} else {
						setFieldValue(entity, attr.Field, refs);
					}
				}
			}
		}
		return entity;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wookler.core.persistence.AbstractPersister#setFieldValue(com.wookler
	 * .core.persistence.AbstractEntity, java.lang.reflect.Field,
	 * java.lang.Object)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setFieldValue(AbstractEntity entity, Field fd, Object value)
			throws Exception {
		Object pvalue = value;
		if (fd.getType().equals(String.class)) {
			pvalue = (String) value;
		} else if (fd.getType().equals(Date.class)) {
			pvalue = DateUtils.fromString((String) value);
		} else if (EnumPrimitives.isPrimitiveType(fd.getType())) {
			EnumPrimitives pt = EnumPrimitives.type(fd.getType());
			switch (pt) {
			case ECharacter:
				pvalue = ((String) value).charAt(0);
				break;
			case EShort:
				pvalue = Short.parseShort((String) value);
				break;
			case EInteger:
				pvalue = Integer.parseInt((String) value);
				break;
			case ELong:
				pvalue = Long.parseLong((String) value);
				break;
			case EFloat:
				pvalue = Float.parseFloat((String) value);
				break;
			case EDouble:
				pvalue = Double.parseDouble((String) value);
				break;
			default:
				throw new Exception("Unsupported primitive type [" + pt.name()
						+ "]");
			}
		} else if (fd.getType().isEnum()) {
			Class ecls = fd.getType();
			pvalue = Enum.valueOf(ecls, (String) value);
		} else if (pvalue.getClass().isAnnotationPresent(Entity.class)) {
			pvalue = value;
		} else {
			throw new Exception("Field type ["
					+ fd.getType().getCanonicalName() + "] is not supported.");
		}
		PropertyUtils.setProperty(entity, fd.getName(), pvalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.core.InitializedHandle#dispose()
	 */
	public void dispose() {
		cache.clear();
	}
}
