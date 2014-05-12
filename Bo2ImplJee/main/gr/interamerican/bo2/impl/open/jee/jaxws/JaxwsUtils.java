package gr.interamerican.bo2.impl.open.jee.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;

/**
 * Utilities for jax-ws
 */
public class JaxwsUtils {
	
	/**
	 * Fixes the endPoint URL of the web service port, to facilitate
	 * deployments on different environments.
	 * 
	 * @param port
	 * @param mod 
	 */
	public static void fixEndPointAddress(Object port, UrlModifier mod) {
		if(!(port instanceof BindingProvider)) {
			throw new RuntimeException("port is not an instance of BindingProvider"); //$NON-NLS-1$
		}
		BindingProvider bp = (BindingProvider) port;
		Map<String, Object> context = bp.getRequestContext();
		String endPointAddress = (String) context.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
		try {
			String newEndPointAddress = mod.execute(new URL(endPointAddress)).toExternalForm();
			context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newEndPointAddress);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
