package gr.interamerican.bo2.impl.open.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

import org.junit.Test;

/**
 * Tests {@link ProxyUtility}.
 */
public class TestProxyUtility {

	/**
	 * Test method for
	 * {@link gr.interamerican.bo2.impl.open.utils.ProxyUtility#getProxy()}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetProxy() {
		assertNull(ProxyUtility.getProxy());
		Bo2.getDefaultDeployment().getProperties().put(ProxyUtility.PROXY_NAME, "localhost");
		Bo2.getDefaultDeployment().getProperties().put(ProxyUtility.PROXY_PASSWORD, "password");
		Bo2.getDefaultDeployment().getProperties().put(ProxyUtility.PROXY_PORT, "1111");
		Bo2.getDefaultDeployment().getProperties().put(ProxyUtility.PROXY_USER, "proxy111");
		Proxy proxy = ProxyUtility.getProxy();
		assertNotNull(proxy);
		assertTrue(proxy.address() instanceof InetSocketAddress);
		InetSocketAddress address = ((InetSocketAddress)proxy.address());
		assertEquals("localhost", address.getHostName());
		assertEquals(1111, address.getPort());
		assertNull(Authenticator.requestPasswordAuthentication("localhost", null, 1112, null, null, null));
		PasswordAuthentication authentication = Authenticator.requestPasswordAuthentication("localhost", null, 1111, null, null, null);
		assertNotNull(authentication);
		assertEquals("proxy111", authentication.getUserName());
		assertArrayEquals("password".toCharArray(), authentication.getPassword());
	}
}