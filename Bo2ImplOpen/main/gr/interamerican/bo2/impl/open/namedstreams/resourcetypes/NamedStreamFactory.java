package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;

/**
 * Named Streams factory interface.
 */
public interface NamedStreamFactory {
	
	/**
	 * Creates a new NamedStream from its definition.
	 *
	 * @param def        Definition for the named stream.
	 *        
	 * @return Returns the new NamedStream.
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	NamedStream<?> create (NamedStreamDefinition def) 
	throws CouldNotCreateNamedStreamException;
	
	/**
	 * Creates a new {@link NamedStream} with a new {@link StreamType}
	 * from an existing NamedStream. <br>
	 * 
	 * The new NamedStream shares the same resource with the initial.
	 *
	 * @param ns the ns
	 * @param type the type
	 * @param name the name
	 * @return Returns the new NamedStream.
	 * @throws CouldNotConvertNamedStreamException         If the specified conversion is not supported or fails
	 *         for another reason.
	 */
	NamedStream<?> convert (NamedStream<?> ns, StreamType type, String name) 
	throws CouldNotConvertNamedStreamException;
			

}
