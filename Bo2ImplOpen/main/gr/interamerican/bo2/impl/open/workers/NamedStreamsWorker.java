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
	
	/**
	 * Underlying NamedStreamsProvider
	 */
	NamedStreamsProvider nsp;
	
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		String mngr = Bo2.getDefaultDeployment().getDeploymentBean().getStreamsManagerName();
		nsp = getProvider().getResource(mngr, NamedStreamsProvider.class);
	}
	
	/**
	 * @see NamedStreamsProvider#getStream(String)
	 * @param streamName
	 * @return stream
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
	 * @see NamedStreamsProvider#getSharedStream(String)
	 * @param streamName
	 * @return shared stream
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
	 * @see NamedStreamsProvider#registerStream(NamedStream)
	 * 
	 * @param stream 
	 */
	public void registerStream(NamedStream<?> stream) {
		nsp.registerStream(stream);
	}
	
	/**
	 * @see NamedStreamsProvider#registerStreamDefinition(NamedStreamDefinition)
	 * 
	 * @param definition 
	 */
	public void registerStreamDefinition(NamedStreamDefinition definition) {
		nsp.registerStreamDefinition(definition);
	}
	
	/**
	 * @see NamedStreamsProvider#registerSharedStream(NamedStream)
	 * 
	 * @param stream 
	 */
	public void registerSharedStream(NamedStream<?> stream) {
		nsp.registerSharedStream(stream);
	}
	
	/**
	 * @see NamedStreamsProvider#convert(String, StreamType, String)
	 * 
	 * @param nameOfStreamToConvert 
	 * @param typeOfNewStream 
	 * @param nameOfNewStream
	 * 
	 * @return converted stream
	 * @throws DataException 
	 */
	@SuppressWarnings("unchecked")
	public <T> NamedStream<T> convert(String nameOfStreamToConvert, StreamType typeOfNewStream, String nameOfNewStream) throws DataException {
		return (NamedStream<T>) nsp.convert(nameOfStreamToConvert, typeOfNewStream, nameOfNewStream);
	}

}
