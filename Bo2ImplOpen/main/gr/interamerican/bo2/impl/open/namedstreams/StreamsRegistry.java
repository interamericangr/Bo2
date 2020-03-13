package gr.interamerican.bo2.impl.open.namedstreams;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.AssociationTable;
import gr.interamerican.bo2.utils.beans.MultipleValuesMap;

import java.util.Set;

import org.slf4j.Logger;

/**
 * Registry for NamedStreams.
 */
@SuppressWarnings("nls")
public class StreamsRegistry {
	
	/** Logger. */
	Logger logger;
	
	/**
	 * Associates stream with name.
	 */
	AssociationTable<String, NamedStream<?>> streams = 
		new AssociationTable<String, NamedStream<?>>();
	
	/**
	 * Associates stream with set of providers accessing it.
	 */
	MultipleValuesMap<NamedStream<?>, NamedStreamsProvider> providersOfStream = 
		new MultipleValuesMap<NamedStream<?>, NamedStreamsProvider>();
	

	/**
	 * Associates a provider with the streams it is accessing.
	 */
	MultipleValuesMap<NamedStreamsProvider, NamedStream<?>> streamsOfProvider = 
		new MultipleValuesMap<NamedStreamsProvider, NamedStream<?>>();
	
	
	/**
	 * Registers that the specified stream is accessed by the specified
	 * provider.
	 *
	 * @param ns the ns
	 * @param nsp the nsp
	 */
	public void register(NamedStream<?> ns, NamedStreamsProvider nsp) {
		registerStream(ns);
		registerProvider(ns, nsp);
	}
	
	
	
	/**
	 * Registers the specified NamedStream.
	 * 
	 * @param ns
	 *        NamedStream to register.
	 */	
	void registerStream(NamedStream<?> ns) {
		String name = ns.getName();
		NamedStream<?> existing = streams.getRight(name);
		if (existing!=null) {
			if (existing!=ns) {		
				String msg = StringUtils.concat
					("A different stream with name ", name,	"is already registered");
				throw new RuntimeException(msg);
			}
		} else {
			streams.associate(ns.getName(), ns);
			if (logger!=null) {
				String logmsg="Registered stream with name " + name;
				logger.debug(logmsg);			
			}
		}
	}
	
	/**
	 * Registers that the specified stream is accessed by the specified
	 * provider.
	 *
	 * @param ns the ns
	 * @param nsp the nsp
	 */
	void registerProvider(NamedStream<?> ns, NamedStreamsProvider nsp) {
		providersOfStream.put(ns, nsp);
		streamsOfProvider.put(nsp, ns);
			
		if (logger!=null) {
			int providersCount=providersOfStream.size(ns);	
			int streamsCount=providersOfStream.size(ns);
			String logmsg=StringUtils.concat (
					"Stream ", ns.getName(), 
					" [+] now accessed by ", Integer.toString(providersCount),
					". The provider is accessing ", Integer.toString(streamsCount),
					" streams.");			
			logger.debug(logmsg);			
		}
	}
	
	
	/**
	 * Gets a stream with its name. The {@link NamedStreamsProvider} requesting the
	 * stream is added to the providers accessing it. If the stream does not exist,
	 * null is returned. This should notify the provider, that he must create and
	 * register the stream himself.
	 *
	 * @param name the name
	 * @param nsp the nsp
	 * @return Returns the stream registered with the specified name.
	 */
	public NamedStream<?> getStream(String name, NamedStreamsProvider nsp) {
		NamedStream<?> ns = streams.getRight(name);
		if (ns!=null) {
			registerProvider(ns, nsp);
		}
		return ns;		
	}
	
	
	
	
	
	
	
	
	/**
	 * Releases the streams accessed by the specified {@link NamedStreamsProvider}.
	 * If this was the last provider accessing the stream, the stream is unregistered 
	 * and closed.
	 *  
	 *
	 * @param nsp the nsp
	 * @throws DataException the data exception
	 */
	public void releaseStreams(NamedStreamsProvider nsp) throws DataException {		
		Set<NamedStream<?>> nsSet = streamsOfProvider.get(nsp);
		streamsOfProvider.remove(nsp);
		for (NamedStream<?> ns : nsSet) {
			providersOfStream.remove(ns, nsp);
			int count = providersOfStream.size(ns);
			if (count==0) {
				close(ns);
			}
		}
	}
	

	/**
	 * Closes the specified {@link NamedStream}.
	 *
	 * @param ns the ns
	 * @throws DataException the data exception
	 * @throws DataOperationNotSupportedException the data operation not supported exception
	 */
	void close(NamedStream<?> ns) throws DataException {
		ns.close();
		streams.removeRight(ns);		
	}

	/**
	 * Gets the logger.
	 *
	 * @return Returns the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * Sets the logger.
	 *
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	

}
