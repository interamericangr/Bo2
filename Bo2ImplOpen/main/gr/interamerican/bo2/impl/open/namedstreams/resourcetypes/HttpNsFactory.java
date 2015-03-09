package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * {@link NamedStreamFactory} for File streams.
 */
public class HttpNsFactory 
extends ReadOnlyNsFactory
implements NamedStreamFactory {

	/**
	 * Creates a new HttpNsFactory.
	 * 
	 */
	public HttpNsFactory() {
		super(StreamResource.HTTP);
	}
	
	@Override
	protected InputStream openInputStream(NamedStreamDefinition def) 
	throws CouldNotCreateNamedStreamException {
		try {
			String uri = def.getUri();
			URL url = new URL(uri);
			URLConnection connection = url.openConnection();
			return connection.getInputStream();
		} catch (MalformedURLException e) {
			throw new CouldNotCreateNamedStreamException(e);
		} catch (IOException e) {
			throw new CouldNotCreateNamedStreamException(e);
		}
	}	
	

}
