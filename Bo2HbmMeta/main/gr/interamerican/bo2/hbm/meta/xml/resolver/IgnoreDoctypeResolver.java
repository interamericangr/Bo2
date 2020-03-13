package gr.interamerican.bo2.hbm.meta.xml.resolver;

import gr.interamerican.bo2.utils.StringConstants;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Resolvers for ignoring hibernate dtd 
 */
public class IgnoreDoctypeResolver implements EntityResolver {
	
	/**
	 * HIBERNATE_ID.
	 */
	@SuppressWarnings("nls")
	private static final List<String> HIBERNATE_IDS = Collections.unmodifiableList(Arrays.asList("-//Hibernate/Hibernate Mapping DTD//EN", "-//Hibernate/Hibernate Mapping DTD 3.0//EN", null));
	
	/**
	 * HIBERNATE_URLS.
	 */
	@SuppressWarnings("nls")
	private static final List<String> HIBERNATE_URLS = Collections.unmodifiableList(Arrays.asList("http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd"));

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        if (HIBERNATE_IDS.contains(publicId) || HIBERNATE_URLS.contains(systemId)) {
            return new InputSource(new StringReader(StringConstants.EMPTY));
        }
        return null;
	}
}