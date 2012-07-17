/**
 * 
 */
package com.wookler.core.persistence.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.AttributeNotFoundException;
import com.wookler.core.persistence.AttributeReflection;
import com.wookler.core.persistence.Entity;
import com.wookler.core.persistence.ReflectionUtils;
import com.wookler.core.persistence.db.SQLDataType;
import com.wookler.core.persistence.db.SqlConditionTransformer;

/**
 * @author subhagho
 * 
 */
public class SimpleDbQuery extends SimpleFilterQuery {
	private static final Logger log = LoggerFactory
			.getLogger(SimpleDbQuery.class);

	private ConditionTransformer transformer = null;

	private List<FilterCondition> postconditions = null;

	public SimpleDbQuery() {
		transformer = new SqlConditionTransformer();
	}

	/**
	 * Parse the filter condition and generate a SQL Select Query.
	 * 
	 * @param type
	 *            - Entity Type
	 * @return
	 * @throws Exception
	 */
	public String parseSelectQuery(Class<?> type) throws Exception {
		if (!type.isAnnotationPresent(Entity.class))
			throw new Exception("Class [" + type.getCanonicalName()
					+ "] has not been annotated as an Entity.");

		List<String> columns = new ArrayList<String>();
		String table = null;
		int limit = parser.getLimit();
		StringBuffer sort = new StringBuffer();
		StringBuffer where = new StringBuffer();

		// Get table name
		Entity eann = (Entity) type.getAnnotation(Entity.class);
		table = eann.recordset();

		// Get Columns
		List<Field> fields = ReflectionUtils.get().getFields(type);
		for (Field field : fields) {
			try {
				AttributeReflection attr = ReflectionUtils.get().getAttribute(
						type, field.getName());
				columns.add(attr.Column);
			} catch (AttributeNotFoundException e) {
				continue;
			}
		}

		// Get Where Clause
		if (conditions != null && conditions.size() > 0) {
			boolean first = true;
			for (AbstractCondition condition : conditions) {
				if (condition instanceof FilterCondition) {
					FilterCondition fc = (FilterCondition) condition;
					if (fc.getComparator() == EnumOperator.Contains) {
						if (postconditions == null)
							postconditions = new ArrayList<FilterCondition>();
						postconditions.add(fc);
						continue;
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
		// Get Sort
		if (parser.getSort() != null && parser.getSort().size() > 0) {
			boolean first = true;
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
		boolean first = true;
		for (String column : columns) {
			if (first)
				first = false;
			else
				qbuff.append(',');
			qbuff.append(' ').append(column);
		}
		qbuff.append(" from ").append(table);
		if (where.length() > 0) {
			qbuff.append(" where ").append(where);
		}
		if (sort.length() > 0) {
			qbuff.append(" order by ").append(sort);
		}
		log.debug("QUERY [" + qbuff.toString() + "]");
		return qbuff.toString();
	}

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
			try {
				AttributeReflection attr = ReflectionUtils.get().getAttribute(
						type, field.getName());

				columns.add(getColumnDDL(attr));
				if (attr.IsKeyColumn) {
					if (keycolumns == null)
						keycolumns = new ArrayList<String>();
					keycolumns.add(attr.Column);
				}
			} catch (AttributeNotFoundException e) {
				continue;
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
			buff.append("alter table " + table
					+ " add constraint primary key (");
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

	private String getColumnDDL(AttributeReflection attr) throws Exception {
		Class<?> type = attr.Field.getType();
		if (attr.Convertor != null) {
			type = attr.Convertor.getDataType();
		} else if (attr.Reference != null) {
			Class<?> rtype = Class.forName(attr.Reference.Class);
			AttributeReflection rattr = ReflectionUtils.get().getAttribute(
					rtype, attr.Reference.Field);
			type = rattr.Field.getType();
		}

		SQLDataType sqlt = SQLDataType.type(type);
		String def = sqlt.name();
		if (sqlt == SQLDataType.VARCHAR2) {
			def = sqlt.name() + "(" + 1024 + ")";
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
