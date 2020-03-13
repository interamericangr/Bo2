package gr.interamerican.bo2.impl.open.jee.jaxws;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Utilities for JAXB.
 */
public class JaxbUtils {

	/**
	 * Unmarshal XML data from the specified InputStream and return the 
	 * resulting bean.
	 * <br>
	 * This assumes that all necessary classes associated to the bean are
	 * directly/indirectly referenced statically from its class.
	 *
	 * @param <T> the generic type
	 * @param beanType the bean type
	 * @param xml the xml
	 * @return Bean instance
	 * @see JAXBContext
	 * @see Unmarshaller
	 */
	public static <T> T unmarshal(Class<T> beanType, InputStream xml) {
		try {
			JAXBContext jc = JAXBContext.newInstance(beanType);
			Unmarshaller u = jc.createUnmarshaller();
			
			@SuppressWarnings("unchecked")
			T bean = (T) u.unmarshal(xml);
			return bean;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
}
