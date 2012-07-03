package com.wookler.utils;

import org.slf4j.Logger;

/**
 * Class defines utility functions for Logging.
 * 
 * @author subhagho
 * 
 */
public class LogUtils {
	/**
	 * Print the stacktrace for the exception in the logfile. This will print
	 * only if DEBUG mode is enabled.
	 * 
	 * @param logger
	 *            - Logger instance.
	 * @param ex
	 *            - Exception
	 */
	public static void stacktrace(Logger logger, Throwable ex) {
		if (logger.isDebugEnabled()) {
			logger.debug("*******************************************************************");
			logger.debug(ex.getLocalizedMessage());
			StackTraceElement[] ste = ex.getStackTrace();
			if (ste != null && ste.length > 0) {
				for (StackTraceElement st : ste) {
					logger.debug(st.getClassName() + "." + st.getMethodName()
							+ "() [" + st.getFileName() + ":"
							+ st.getLineNumber() + "]");
				}
			}
			if (ex.getCause() != null) {
				stacktrace(logger, ex.getCause());
			}
		}
	}
}
