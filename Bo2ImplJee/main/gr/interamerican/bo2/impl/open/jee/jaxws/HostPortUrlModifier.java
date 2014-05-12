package gr.interamerican.bo2.impl.open.jee.jaxws;

import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.TokenUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Receives a host and a port separated by ':'
 * and produces a URL that is identical to the input
 * minus these two.
 */
public class HostPortUrlModifier implements UrlModifier {

	/**
	 * new host
	 */
	String host;
	
	/**
	 * new port
	 */
	int port;
	
	/**
	 * Creates a new HostPortUrlModifier object.
	 *  
	 * @param hostPort
	 *        e.g. "127.0.0.1:8080"
	 */
	public HostPortUrlModifier(String hostPort) {
		String[] tokens = TokenUtils.splitTrim(hostPort, StringConstants.COLON);
		this.host = tokens[0];
		this.port = 80;
		if(tokens.length > 1) {
			port = NumberUtils.string2Int(tokens[1]);
		}
	}
	
	public URL execute(URL a) {
		
		
		try {
			return new URL(
				a.getProtocol(), host, port, a.getFile());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

}
