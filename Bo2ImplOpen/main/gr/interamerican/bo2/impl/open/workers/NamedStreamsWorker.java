package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.utils.Bo2DeploymentParams;

/**
 * Worker that provides a facade to the default {@link NamedStreamsProvider}.
 * Checked exceptions are rethrown as {@link RuntimeException}s to simplify API use.
 * 
 * @see NamedStreamsProvider
 * @see Bo2DeploymentParams#getStreamsManagerName()
 */
public class NamedStreamsWorker extends AbstractBaseWorker {
	
	/** Underlying NamedStreamsProvider. */
	NamedStreamsProvider nsp;
	
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		String mngr = Bo2.getDefaultDeployment().getDeploymentBean().getStreamsManagerName();
		nsp = getProvider().getResource(mngr, NamedStreamsProvider.class);
	}
	
	/**
	 * Gets the stream.
	 *
	 * @param <T> the generic type
	 * @param streamName the stream name
	 * @return stream
	 * @see NamedStreamsProvider#getStream(String)
	 */
	@SuppressWarnings("unchecked")
	public <T> NamedStream<T> getStream(String streamName) {
		try {
			return (NamedStream<T>) nsp.getStream(streamName);
		} catch (InitializationException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Gets the shared stream.
	 *
	 * @param <T> the generic type
	 * @param streamName the stream name
	 * @return shared stream
	 * @see NamedStreamsProvider#getSharedStream(String)
	 */
	@SuppressWarnings("unchecked")
	public <T> NamedStream<T> getSharedStream(String streamName) {
		try {
			return (NamedStream<T>) nsp.getSharedStream(streamName);
		} catch (InitializationException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Register stream.
	 *
	 * @param stream the stream
	 * @see NamedStreamsProvider#registerStream(NamedStream)
	 */
	public void registerStream(NamedStream<?> stream) {
		nsp.registerStream(stream);
	}
	
	/**
	 * Register stream definition.
	 *
	 * @param definition the definition
	 * @see NamedStreamsProvider#registerStreamDefinition(NamedStreamDefinition)
	 */
	public void registerStreamDefinition(NamedStreamDefinition definition) {
		nsp.registerStreamDefinition(definition);
	}
	
	/**
	 * Register shared stream.
	 *
	 * @param stream the stream
	 * @see NamedStreamsProvider#registerSharedStream(NamedStream)
	 */
	public void registerSharedStream(NamedStream<?> stream) {
		nsp.registerSharedStream(stream);
	}
	
	/**
	 * Convert.
	 *
	 * @param <T> the generic type
	 * @param nameOfStreamToConvert the name of stream to convert
	 * @param typeOfNewStream the type of new stream
	 * @param nameOfNewStream the name of new stream
	 * @return converted stream
	 * @throws DataException the data exception
	 * @see NamedStreamsProvider#convert(String, StreamType, String)
	 */
	@SuppressWarnings("unchecked")
	public <T> NamedStream<T> convert(String nameOfStreamToConvert, StreamType typeOfNewStream, String nameOfNewStream) throws DataException {
		return (NamedStream<T>) nsp.convert(nameOfStreamToConvert, typeOfNewStream, nameOfNewStream);
	}

}
