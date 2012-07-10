/**
 * 
 */
package com.wookler.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author subhagho
 * 
 */
public class DateUtils {
	public static final String _DEFAULT_DATE_FORMAT_ = "MM-dd-yyyy HH:mm:ss";

	/**
	 * Parse the date string using the default date format.
	 * 
	 * @param ds
	 *            - Date String
	 * @return
	 * @throws Exception
	 */
	public static final Date parse(String ds) throws Exception {
		return parse(ds, _DEFAULT_DATE_FORMAT_);
	}

	/**
	 * Parse the date string using the specified date format.
	 * 
	 * @param ds
	 *            - Date String
	 * @param format
	 *            - Date Format
	 * @return
	 * @throws Exception
	 */
	public static final Date parse(String ds, String format) throws Exception {
		DateFormat formatter = new SimpleDateFormat(format);
		return (Date) formatter.parse(ds);
	}

	/**
	 * Format the date using the default date format.
	 * 
	 * @param dt
	 *            - Date
	 * @return
	 * @throws Exception
	 */
	public static final String format(Date dt) throws Exception {
		DateFormat formatter = new SimpleDateFormat(_DEFAULT_DATE_FORMAT_);
		return formatter.format(dt);
	}

	/**
	 * Format the date using the specified date format.
	 * 
	 * @param dt
	 *            - Date
	 * @param format
	 *            - Date Format
	 * @return
	 * @throws Exception
	 */
	public static final String format(Date dt, String format) throws Exception {
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(dt);
	}
}
