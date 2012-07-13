/**
 * 
 */
package com.wookler.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author subhagho
 * 
 */
public class RegexTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String condition = "VAR IN ['8888','9999','99991','99992']";

			String qr = "\\[(.*)(,.*)*?\\]";
			Pattern pattern = Pattern.compile(qr);
			Matcher matcher = pattern.matcher(condition);
			while (matcher.find()) {
				for (int i = 0; i <= matcher.groupCount(); i++) {
					System.out.println(matcher.group(i));
				}
			}
			// System.out.println(matcher.appendTail(out).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
