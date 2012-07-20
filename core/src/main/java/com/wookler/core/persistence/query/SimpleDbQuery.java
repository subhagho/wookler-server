/**
 * 
 */
package com.wookler.core.persistence.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.AttributeReflection;
import com.wookler.core.persistence.Entity;
import com.wookler.core.persistence.ReflectionUtils;
import com.wookler.core.persistence.db.SQLDataType;
import com.wookler.core.persistence.db.SqlConditionTransformer;
import com.wookler.utils.KeyValuePair;

/**
 * Class encapsulates the Query definition for a JDBC compliant Database.
 * 
 * @author subhagho
 * 
 */
public class SimpleDbQuery extends SimpleFilterQuery {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(SimpleDbQuery.class);

	private ConditionTransformer transformer = null;

	private List<FilterCondition> joinconditions = null;

	private List<FilterCondition> postconditions = null;

	public SimpleDbQuery() {
		transformer = new SqlConditionTransformer();
	}

	/**
	 * Get the insert Query(s) for the entity and all referenced entities.
	 * 
	 * @param type
	 *            - Entity Type.
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getInsertQuery(Class<?> type) throws Exception {
		HashMap<String, String> queries = new HashMap<String, String>();
		getInsertQuery(type, queries);
		return queries;
	}

	private void getInsertQuery(Class<?> type, HashMap<String, String> queries)
			throws Exception {
		if (!type.isAnnotationPresent(Entity.class))
			throw new Exception("Class [" + type.getCanonicalName()
					+ "] has not been annotated as an Entity.");

		// Get table name
		Entity eann = (Entity) type.getAnnotation(Entity.class);
		String table = eann.recordset();

		StringBuffer query = new StringBuffer();
		query.append("insert into ").append(table).append(" ( ");
		StringBuffer values = new StringBuffer();
		values.append(" values (");

		// Get Columns
		List<Field> fields = ReflectionUtils.get().getFields(type);

		List<AttributeReflection> refs = null;
		boolean first = true;

		for (Field field : fields) {
			AttributeReflection attr = ReflectionUtils.get().getAttribute(type,
					field.getName());
			if (attr == null)
				continue;
			if (attr.Reference != null) {
				if (refs == null)
					refs = new ArrayList<AttributeReflection>();
				refs.add(attr);
			}
			if (first)
				first = false;
			else {
				query.append(',');
				values.append(',');
			}
			query.append(attr.Column);
			values.append('?');

		}
		query.append(" ) ");
		values.append(" ) ");

		if (values != null)
			query.append(values);
		queries.put(type.getCanonicalName(), query.toString());
		if (refs != null) {
			for (AttributeReflection attr : refs) {
				Class<?> rt = Class.forName(attr.Reference.Class);
				getInsertQuery(rt, queries);
			}
		}
	}

	/**
	 * Get the update Query(s) for the entity and all referenced entities.
	 * 
	 * @param type
	 *            - Entity Type.
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getUpdateQuery(Class<?> type) throws Exception {
		HashMap<String, String> queries = new HashMap<String, String>();
		getUpdateQuery(type, queries);
		return queries;
	}

	private void getUpdateQuery(Class<?> type, HashMap<String, String> queries)
			throws Exception {
		if (!type.isAnnotationPresent(Entity.class))
			throw new Exception("Class [" + type.getCanonicalName()
					+ "] has not been annotated as an Entity.");

		// Get table name
		Entity eann = (Entity) type.getAnnotation(Entity.class);
		String table = eann.recordset();

		StringBuffer query = new StringBuffer();
		query.append("update table ").append(table).append(" set ");
		StringBuffer where = null;

		// Get Columns
		List<Field> fields = ReflectionUtils.get().getFields(type);

		List<AttributeReflection> refs = null;
		boolean first = true;
		boolean wfirst = true;

		for (Field field : fields) {
			AttributeReflection attr = ReflectionUtils.get().getAttribute(type,
					field.getName());
			if (attr == null)
				continue;
			if (attr.IsKeyColumn
					|| attr.Column
							.compareTo(AbstractEntity._TX_TIMESTAMP_COLUMN_) == 0) {
				if (wfirst) {
					where = new StringBuffer();
					where.append(" where ");
					wfirst = false;
				} else
					where.append(" and ");
				where.append(attr.Column).append("=?");
				continue;
			}

			if (attr.Reference != null) {
				if (refs == null)
					refs = new ArrayList<AttributeReflection>();
				refs.add(attr);
			}
			if (first)
				first = false;
			else
				query.append(',');
			query.append(attr.Column).append("=?");

		}
		if (where != null)
			query.append(where);
		queries.put(type.getCanonicalName(), query.toString());
		if (refs != null) {
			for (AttributeReflection attr : refs) {
				Class<?> rt = Class.forName(attr.Reference.Class);
				getUpdateQuery(rt, queries);
			}
		}
	}

	/**
	 * Parse the filter condition and generate a SQL Select Query.
	 * 
	 * @param type
	 *            - Entity Type
	 * @return
	 * @throws Exception
	 */
	public String getSelectQuery(Class<?> type) throws Exception {
		if (!type.isAnnotationPresent(Entity.class))
			throw new Exception("Class [" + type.getCanonicalName()
					+ "] has not been annotated as an Entity.");

		List<String> columns = new ArrayList<String>();
		List<String> tables = new ArrayList<String>();
		int limit = parser.getLimit();
		StringBuffer sort = new StringBuffer();
		StringBuffer where = new StringBuffer();

		getColumns(type, tables, columns);

		// Get Where Clause
		boolean first = true;
		if (conditions != null && conditions.size() > 0) {
			for (AbstractCondition condition : conditions) {
				if (condition instanceof FilterCondition) {
					FilterCondition fc = (FilterCondition) condition;
					if (fc.getComparator() == EnumOperator.Contains) {
						if (postconditions == null)
							postconditions = new ArrayList<FilterCondition>();
						postconditions.add(fc);
					}
					String strcond = transformer.transform(condition, type);
					if (first)
						first = false;
					else
						where.append(" and ");
					where.append(strcond);
				}
			}
		}
		if (joinconditions != null && joinconditions.size() > 0) {
			for (AbstractCondition condition : joinconditions) {
				if (condition instanceof FilterCondition) {
					FilterCondition fc = (FilterCondition) condition;
					String strcond = transformer.transform(fc, null);
					if (first)
						first = false;
					else
						where.append(" and ");
					where.append(strcond);
				}
			}
		}
		// Get Sort
		if (parser.getSort() != null && parser.getSort().size() > 0) {
			first = true;
			for (SortColumn column : parser.getSort()) {
				if (first)
					first = false;
				else {
					sort.append(",");
				}
				sort.append(' ').append(column.getColumn());
				if (column.getOrder() == EnumSortOrder.ASC) {
					sort.append(" ASC");
				} else {
					sort.append(" DESC");
				}
			}
		}

		// Create query
		StringBuffer qbuff = new StringBuffer("select ");
		if (limit > 0)
			qbuff.append(" top ").append(limit);
		first = true;
		for (String column : columns) {
			if (first)
				first = false;
			else
				qbuff.append(',');
			qbuff.append(' ').append(column);
		}
		first = true;
		for (String table : tables) {
			if (first) {
				first = false;
				qbuff.append(" from ");
			} else
				qbuff.append(",");
			qbuff.append(table).append(' ');
		}
		if (where.length() > 0) {
			qbuff.append(" where ").append(where);
		}
		if (sort.length() > 0) {
			qbuff.append(" order by ").append(sort);
		}
		return qbuff.toString();
	}

	private void getColumns(Class<?> type, List<String> tables,
			List<String> columns) throws Exception {
		// Get table name
		Entity eann = (Entity) type.getAnnotation(Entity.class);
		String table = eann.recordset();
		tables.add(table);

		// Get Columns
		List<Field> fields = ReflectionUtils.get().getFields(type);

		List<AttributeReflection> refs = null;

		for (Field field : fields) {
			AttributeReflection attr = ReflectionUtils.get().getAttribute(type,
					field.getName());
			if (attr == null)
				continue;
			if (attr.Reference != null) {
				if (refs == null)
					refs = new ArrayList<AttributeReflection>();
				refs.add(attr);
			} else
				columns.add(table.concat(".").concat(attr.Column));

		}
		if (refs != null && refs.size() > 0) {
			for (AttributeReflection ref : refs) {
				Class<?> rtype = Class.forName(ref.Reference.Class);
				Entity reann = (Entity) rtype.getAnnotation(Entity.class);
				String rtable = reann.recordset();
				FilterCondition cond = new FilterCondition(rtable.concat(".")
						.concat(ref.Reference.Field), EnumOperator.Equal, table
						.concat(".").concat(ref.Column));
				if (joinconditions == null)
					joinconditions = new ArrayList<FilterCondition>();

				joinconditions.add(cond);
				getColumns(rtype, tables, columns);
			}
		}
	}

	/**
	 * Generate the CREATE TABLE script for an Entity type. This will also
	 * generate the DROP and PRIMARY KEY constraints (if applicable).
	 * 
	 * @param type
	 *            - Entity Type
	 * @return List of generated SQL statements.
	 * 
	 * @throws Exception
	 */
	public List<String> getCreateTableDDL(Class<?> type) throws Exception {
		if (!type.isAnnotationPresent(Entity.class))
			throw new Exception("Class [" + type.getCanonicalName()
					+ "] has not been annotated as an Entity.");

		List<String> stmnts = new ArrayList<String>();
		List<String> columns = new ArrayList<String>();
		List<String> keycolumns = null;

		String table = null;

		// Get table name
		Entity eann = (Entity) type.getAnnotation(Entity.class);
		table = eann.recordset();

		// Drop table statement
		stmnts.add("drop table if exists " + table + " cascade");

		// Get Columns
		List<Field> fields = ReflectionUtils.get().getFields(type);
		for (Field field : fields) {
			AttributeReflection attr = ReflectionUtils.get().getAttribute(type,
					field.getName());
			if (attr == null)
				continue;
			columns.add(getColumnDDL(attr));
			if (attr.IsKeyColumn) {
				if (keycolumns == null)
					keycolumns = new ArrayList<String>();
				keycolumns.add(attr.Column);
			}
		}

		// Create table statement
		StringBuffer buff = new StringBuffer();
		buff.append("create table (");
		boolean first = true;
		for (String column : columns) {
			if (first)
				first = false;
			else
				buff.append(",");
			buff.append(column);
		}
		buff.append(")");

		stmnts.add(buff.toString());

		// Create primary key (if any)
		if (keycolumns != null && keycolumns.size() > 0) {
			buff = new StringBuffer();
			buff.append("alter table ").append(table)
					.append(" add constraint primary key (");
			first = true;
			for (String column : keycolumns) {
				if (first)
					first = false;
				else
					buff.append(",");
				buff.append(column);
			}
			buff.append(")");
			stmnts.add(buff.toString());
		}
		return stmnts;
	}

	/**
	 * Generate a CREATE INDEX statement for the specified Entity.
	 * 
	 * @param type
	 *            - Entity Type.
	 * @param keycolumns
	 *            - Set of indexed columns.
	 * @return
	 * @throws Exception
	 */
	public List<String> getCreateIndexDDL(Class<?> type,
			List<KeyValuePair<String>> keycolumns) throws Exception {
		if (!type.isAnnotationPresent(Entity.class))
			throw new Exception("Class [" + type.getCanonicalName()
					+ "] has not been annotated as an Entity.");

		List<String> stmnts = new ArrayList<String>();
		String table = null;

		// Get table name
		Entity eann = (Entity) type.getAnnotation(Entity.class);
		table = eann.recordset();

		for (KeyValuePair<String> keys : keycolumns) {
			String idxname = keys.getKey();
			if (idxname == null || idxname.isEmpty())
				continue;
			String[] columns = keys.getValue().split(",");
			if (columns == null || columns.length <= 0)
				continue;

			String dropstmnt = "drop index if exists " + idxname;
			stmnts.add(dropstmnt);

			StringBuffer buff = new StringBuffer();
			buff.append("create index ").append(idxname).append(" on ")
					.append(table);
			buff.append(" ( ");
			boolean first = true;
			for (String column : columns) {
				if (first)
					first = false;
				else
					buff.append(",");
				String cname = column.trim();
				AttributeReflection attr = ReflectionUtils.get().getAttribute(
						type, cname);
				if (attr == null)
					throw new Exception(
							"No column definition found for column [" + column
									+ "] for Entity ["
									+ type.getCanonicalName() + "]");
				buff.append(attr.Column);
			}
			buff.append(")");
			stmnts.add(buff.toString());
		}
		return stmnts;
	}

	private String getColumnDDL(AttributeReflection attr) throws Exception {
		Class<?> type = attr.Field.getType();
		AttributeReflection tattr = attr;
		if (attr.Convertor != null) {
			type = attr.Convertor.getDataType();
		} else if (attr.Reference != null) {
			Class<?> rtype = Class.forName(attr.Reference.Class);
			AttributeReflection rattr = ReflectionUtils.get().getAttribute(
					rtype, attr.Reference.Field);
			type = rattr.Field.getType();
			tattr = rattr;
		}

		SQLDataType sqlt = SQLDataType.type(type);
		String def = sqlt.name();
		if (sqlt == SQLDataType.VARCHAR2) {
			int size = 128;
			if (tattr.Size > 0)
				size = tattr.Size;

			def = sqlt.name().concat("(").concat(String.valueOf(size))
					.concat(")");
		}
		return attr.Column + " " + def;
	}

	/**
	 * Post Selects further refine the result set based on conditions that
	 * aren't supported by SQL. (CONTAINS)
	 * 
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public List<AbstractEntity> postSelect(List<AbstractEntity> entities)
			throws Exception {
		if (postconditions != null && postconditions.size() > 0) {
			List<AbstractEntity> filtered = new ArrayList<AbstractEntity>();
			for (AbstractEntity entity : entities) {
				boolean select = true;
				for (FilterCondition condition : postconditions) {
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
