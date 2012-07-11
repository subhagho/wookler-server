/**
 * 
 */
package com.wookler.core.persistence.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author subhagho
 * 
 */
public class FilterConditionParser {
	private static final String _QUOTES_REGEX_ = "['|\"](.*?)['|\"]";

	private HashMap<String, String> quoted = new HashMap<String, String>();

	public List<FilterCondition> parse(String condition)
			throws Exception {
		quoted.clear();
		List<FilterCondition> conditions = new ArrayList<FilterCondition>();
		String filterstr = parseQuoted(condition);
		String[] filters = filterstr.split(";");
		if (filters != null) {
			for (String filter : filters) {
				FilterCondition cond = parseCondition(filter);
				if (cond != null)
					conditions.add(cond);
			}
		}
		return conditions;
	}

	private FilterCondition parseCondition(String filter) throws Exception {
		for (String oper : EnumOperator._OPERATOR_TOKENS_) {
			if (filter.indexOf(oper) >= 0) {
				String[] parts = filter.split(oper);
				if (parts.length < 2)
					throw new Exception("Error parsing filter condition ["
							+ filter + "]");
				if (parts[0] == null || parts[0].isEmpty())
					throw new Exception("Error parsing filter condition ["
							+ filter + "] : Missing condition field.");
				if (parts[1] == null || parts[1].isEmpty())
					throw new Exception("Error parsing filter condition ["
							+ filter + "] : Missing condition value.");
				parts[1] = parts[1].trim();
				if (quoted.containsKey(parts[1])) {
					parts[1] = quoted.get(parts[1]);
				}
				EnumOperator eoper = EnumOperator.parse(oper);
				FilterCondition cond = new FilterCondition(parts[0], eoper,
						parts[1]);
				return cond;
			}
		}
		for (String oper : EnumOperator._OPERATOR_KEYWORDS_) {
			String regex = "(.*) (?i)" + oper + "(.*)";
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(filter);
			while (matcher.find()) {
				String column = matcher.group(1);
				String value = matcher.group(2).trim();
				if (quoted.containsKey(value)) {
					value = quoted.get(value);
				}
				EnumOperator eoper = EnumOperator.parse(oper);
				switch (eoper) {
				case Like:
				case Contains:
					FilterCondition cond = new FilterCondition(column, eoper,
							value);
					return cond;
				case Between:
					String vregx = "\\[(.*),(.*)\\]";
					Pattern vpattrn = Pattern.compile(vregx);
					Matcher vmatch = vpattrn.matcher(value);
					while (vmatch.find()) {
						String[] values = new String[2];
						values[0] = vmatch.group(1).trim();
						if (quoted.containsKey(values[0]))
							values[0] = quoted.get(values[0]);
						values[1] = vmatch.group(2).trim();
						if (quoted.containsKey(values[1]))
							values[1] = quoted.get(values[1]);

						FilterCondition bcond = new FilterCondition(column,
								eoper, values);
						return bcond;
					}
				}
			}
		}
		throw new Exception("Error parsing filter condition [" + filter + "]");
	}

	private String parseQuoted(String condition) {
		Pattern pattern = Pattern.compile(_QUOTES_REGEX_);
		Matcher matcher = pattern.matcher(condition);
		int index = quoted.size();
		StringBuffer out = new StringBuffer();
		while (matcher.find()) {
			String part = matcher.group(1);
			String key = "QUOTED_STRING_" + index;
			matcher.appendReplacement(out, key);
			quoted.put(key, part.trim());
			index++;
		}
		return matcher.appendTail(out).toString();
	}
}
