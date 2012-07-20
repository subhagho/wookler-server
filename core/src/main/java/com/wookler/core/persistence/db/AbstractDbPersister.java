/**
 * 
 */
package com.wookler.core.persistence.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.wookler.core.EnumInstanceState;
import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.AbstractPersister;
import com.wookler.core.persistence.AttributeReflection;
import com.wookler.core.persistence.Entity;
import com.wookler.core.persistence.EnumPrimitives;
import com.wookler.core.persistence.ReflectionUtils;
import com.wookler.core.persistence.query.SimpleDbQuery;

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

		if (!type.isAnnotationPresent(Entity.class))
			throw new Exception("Class [" + type.getCanonicalName()
					+ "] has not been annotated as an Entity.");

		Connection conn = getConnection(true);
		try {
			return read(query, type, conn);
		} finally {
			if (conn != null)
				releaseConnection(conn);
		}
	}

	private List<AbstractEntity> read(String query, Class<?> type,
			Connection conn) throws Exception {
		SimpleDbQuery parser = new SimpleDbQuery();
		parser.parse(query);

		String selectsql = parser.getSelectQuery(type);
		Statement stmnt = conn.createStatement();
		try {
			ResultSet rs = stmnt.executeQuery(selectsql);
			List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
			List<Field> fields = ReflectionUtils.get().getFields(type);
			while (rs.next()) {
				Object obj = type.newInstance();
				if (!(obj instanceof AbstractEntity))
					throw new Exception("Unsupported Entity type ["
							+ type.getCanonicalName() + "]");
				AbstractEntity entity = (AbstractEntity) obj;
				setEntity(entity, rs, fields);
				entities.add(entity);
			}
			return entities;
		} finally {
			if (stmnt != null && !stmnt.isClosed())
				stmnt.close();
		}
	}

	private void setEntity(AbstractEntity entity, ResultSet rs,
			List<Field> fields) throws Exception {
		for (Field field : fields) {
			AttributeReflection attr = ReflectionUtils.get().getAttribute(
					entity.getClass(), field.getName());
			if (attr == null)
				continue;
			Entity eann = (Entity) entity.getClass()
					.getAnnotation(Entity.class);
			String table = eann.recordset();
			setColumnValue(table, rs, attr, entity);

		}
	}

	private void setColumnValue(String tabprefix, ResultSet rs,
			AttributeReflection attr, AbstractEntity entity) throws Exception {

		if (EnumPrimitives.isPrimitiveType(attr.Field.getType())) {
			EnumPrimitives prim = EnumPrimitives.type(attr.Field.getType());
			switch (prim) {
			case ECharacter:
				String sv = rs.getString(tabprefix + "." + attr.Column);
				if (!rs.wasNull()) {
					PropertyUtils.setSimpleProperty(entity,
							attr.Field.getName(), sv.charAt(0));
				}
				break;
			case EShort:
				short shv = rs.getShort(tabprefix + "." + attr.Column);
				if (!rs.wasNull()) {
					PropertyUtils.setSimpleProperty(entity,
							attr.Field.getName(), shv);
				}
				break;
			case EInteger:
				int iv = rs.getInt(tabprefix + "." + attr.Column);
				if (!rs.wasNull()) {
					PropertyUtils.setSimpleProperty(entity,
							attr.Field.getName(), iv);
				}
				break;
			case ELong:
				long lv = rs.getLong(tabprefix + "." + attr.Column);
				if (!rs.wasNull()) {
					PropertyUtils.setSimpleProperty(entity,
							attr.Field.getName(), lv);
				}
				break;
			case EFloat:
				float fv = rs.getFloat(tabprefix + "." + attr.Column);
				if (!rs.wasNull()) {
					PropertyUtils.setSimpleProperty(entity,
							attr.Field.getName(), fv);
				}
				break;
			case EDouble:
				double dv = rs.getDouble(tabprefix + "." + attr.Column);
				if (!rs.wasNull()) {
					PropertyUtils.setSimpleProperty(entity,
							attr.Field.getName(), dv);
				}
				break;
			default:
				throw new Exception("Unsupported Data type [" + prim.name()
						+ "]");
			}
		} else if (attr.Convertor != null) {
			String value = rs.getString(tabprefix + "." + attr.Column);
			if (!rs.wasNull()) {
				attr.Convertor.load(entity, attr.Column, value);
			}
		} else if (attr.Field.getType().equals(String.class)) {
			String value = rs.getString(tabprefix + "." + attr.Column);
			if (!rs.wasNull()) {
				PropertyUtils.setSimpleProperty(entity, attr.Field.getName(),
						value);
			}
		} else if (attr.Field.getType().equals(Date.class)) {
			long value = rs.getLong(tabprefix + "." + attr.Column);
			if (!rs.wasNull()) {
				Date dt = new Date(value);
				PropertyUtils.setSimpleProperty(entity, attr.Field.getName(),
						dt);
			}
		} else if (attr.Reference != null) {
			Class<?> rt = Class.forName(attr.Reference.Class);
			Object obj = rt.newInstance();
			if (!(obj instanceof AbstractEntity))
				throw new Exception("Unsupported Entity type ["
						+ rt.getCanonicalName() + "]");
			AbstractEntity rentity = (AbstractEntity) obj;
			List<Field> fields = ReflectionUtils.get().getFields(rt);
			setEntity(rentity, rs, fields);
			PropertyUtils.setSimpleProperty(entity, attr.Field.getName(),
					rentity);
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
