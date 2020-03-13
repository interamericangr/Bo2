package gr.interamerican.bo2.impl.open.utils;

import gr.interamerican.bo2.utils.Utils;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;

/**
 * Utility for {@link Proxy}.
 */
public class ProxyUtility {

	/**
	 * String property for the Proxy Port
	 */
	public final static String PROXY_PORT = "proxyPort"; //$NON-NLS-1$
	/**
	 * String property for the Proxy Name
	 */
	public final static String PROXY_NAME = "proxyName"; //$NON-NLS-1$
	/**
	 * String property for the Proxy User Id
	 */
	public final static String PROXY_USER = "proxyUser"; //$NON-NLS-1$
	/**
	 * String property for the Proxy User's Password
	 */
	public final static String PROXY_PASSWORD = "proxyPassword"; //$NON-NLS-1$

	/**
	 * Gets a Proxy to be used. This is based on the properties set on the
	 * {@link Bo2#getDefaultDeployment()}.<br>
	 * If any of these properties is not set - null will be returned.
	 * 
	 * @return New Proxy Instance
	 */
	public static Proxy getProxy() {
		final String proxyName = Bo2.getDefaultDeployment().getProperties().getProperty(PROXY_NAME);
		final String proxyUser = Bo2.getDefaultDeployment().getProperties().getProperty(PROXY_USER);
		final String proxyPassword = Bo2.getDefaultDeployment().getProperties().getProperty(PROXY_PASSWORD);
		String proxyPort = Bo2.getDefaultDeployment().getProperties().getProperty(PROXY_PORT);
		if (proxyName == null || proxyUser == null || proxyPassword == null || proxyPort == null) {
			return null;
		}
		final int port = Integer.parseInt(proxyPort);
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				if (Utils.equals(proxyName, getRequestingHost()) && port == getRequestingPort()) {
					
					return new PasswordAuthentication(proxyUser, proxyPassword.toCharArray());
				}
				return super.getPasswordAuthentication();
			}
		});
		SocketAddress sa = new InetSocketAddress(proxyName, port);
		return new Proxy(Proxy.Type.HTTP, sa);
	}
}