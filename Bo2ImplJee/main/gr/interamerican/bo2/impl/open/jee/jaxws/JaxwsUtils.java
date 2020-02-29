package gr.interamerican.bo2.impl.open.jee.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceClient;

/**
 * Utilities for jax-ws.
 */
public class JaxwsUtils {
	
	/**
	 * Fixes the endPoint URL of the web service port, to facilitate
	 * deployments on different environments.
	 *
	 * @param port the port
	 * @param mod the mod
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
	
	/**
	 * Returns the {@link URL} a jax-ws web service would use based on the
	 * {@link WebServiceClient} annotation present on the web service class, but
	 * also modified by the {@link UrlModifier} that is passed as an argument.
	 *
	 * @param <S> the generic type
	 * @param urlModifier            Modifies the default url of the web service
	 * @param serviceClass            Class of the generated web service
	 * @return Modified {@link URL} of the web service
	 */
	public static <S extends Service> URL getModifiedUrl(UrlModifier urlModifier, Class<S> serviceClass) {
		WebServiceClient annotation = serviceClass.getAnnotation(WebServiceClient.class);
		URL baseUrl = serviceClass.getResource("."); //$NON-NLS-1$
		try {
			URL defaultUrl = new URL(baseUrl, annotation.wsdlLocation());
			return urlModifier.execute(defaultUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the {@link QName} a jax-ws web service would use based on the
	 * {@link WebServiceClient} annotation present on the web service class.
	 *
	 * @param <S> the generic type
	 * @param serviceClass            Class of the generated web service
	 * @return Corresponding QName
	 */
	public static <S extends Service> QName getQname(Class<S> serviceClass) {
		WebServiceClient annotation = serviceClass.getAnnotation(WebServiceClient.class);
		return new QName(annotation.targetNamespace(), annotation.name());
	}
	
}
