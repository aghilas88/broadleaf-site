/**
 * 
 */
package org.broadleafcommerce.openadmin.server.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author javacares@gmail.com
 *
 */
public abstract class ServletUtils {

	public static String getContentRange(long total, int limit, int offset) {
		return "items " +  offset + "-" + (offset + limit) + "/" + total;
	}

	public static int[] getRangeLimitAndOffset(HttpServletRequest request) {
		int[] result = new int[2];
		return result;
	}

}
