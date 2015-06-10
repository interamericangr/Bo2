package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.utils.StreamUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * {@link NamedStreamFactory} for File streams.
 */
public class ClasspathNsFactory 
extends ReadOnlyNsFactory
implements NamedStreamFactory {
	
	
	/**
	 * Creates a new HttpNsFactory.
	 * 
	 */
	public ClasspathNsFactory() {
		super(StreamResourceEnum.CLASSPATH);
	}
	
	@Override
	protected InputStream openInputStream(NamedStreamDefinition def) 
	throws CouldNotCreateNamedStreamException {
		try {
			String uri = def.getUri();
			return StreamUtils.getResourceStream(uri);
		} catch (IOException e) {			
			throw new CouldNotCreateNamedStreamException(e);
		}
	}	

}
