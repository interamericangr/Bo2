package gr.interamerican.bo2.impl.open.jee.servlet;

import gr.interamerican.bo2.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * {@link HttpServlet} utilities.
 */
public class HttpServletUtils {
	
	/**
	 * There is an inconsistency on the evaluation of GET request parameters across
	 * servlet containers. This utility attempts to provide a more consistently working
	 * solution.
	 * <br/>
	 * {@link HttpServletRequest#getQueryString()} returns the query string that is 
	 * contained in the request URL after the path. The value is not decoded by the 
	 * container. This is something like the following: a=A&b=%20 (two named parameters
	 * a with value 'A' and b with URL encoded value ' '). 
	 * <br/>
	 * This method will retrieve the (possibly) URL encoded value of the specified named
	 * GET parameter and will URL decode it. The decoding mechanism used does not alter 
	 * any String that is not URL encoded.
	 * 
	 * @param req
	 *        Only used to invoke {@link HttpServletRequest#getQueryString()}
	 * @param parameterName
	 *        Name of the parameter
	 * @return Parameter value. Null is returned if the parameter cannot be evaluated
	 *         with the given input.
	 *         
	 * @see URLDecoder
	 */
	@SuppressWarnings("nls")
	public static String getGetParameter(HttpServletRequest req, String parameterName) {
		String queryString = req.getQueryString();
		if(StringUtils.isNullOrBlank(queryString)) {
			return null;
		}
		String[] parameterPairs = queryString.split("&");
		
		for(String parameterPair : parameterPairs) {
			String[] pair = parameterPair.split("=");
			
			if(!parameterName.equals(pair[0])) {
				continue;
			}
			
			if(pair.length==1) {
				return "";
			}
			
			try {
				return URLDecoder.decode(pair[1], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

}
