package gr.interamerican.bo2.impl.open.runtime.concurrent;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;

/**
 * Utility class to help create stream names.
 */
class SharedStreams {
	
	/**
	 * Provider name for the provider used to initialized shared
	 * streams used a log files by multithreaded processes.
	 */
	private static final String LOGFILES_PROVIDER_NAME = "LOGFILES"; //$NON-NLS-1$
	
	/**
	 * Postfix of successes stream name.
	 */
	private static final String SUCCESSES = "_SUCCESSES"; //$NON-NLS-1$
	
	/**
	 * Postfix of failures stream name.
	 */
	private static final String FAILURES = "_FAILURES"; //$NON-NLS-1$
	
	/**
	 * Postfix of stacktraces stream name.
	 */
	private static final String STACKTRACES = "_STACKTRACES"; //$NON-NLS-1$
	
	/**
	 * Gets the stream name of the successes stream.
	 * 
	 * @param prefix
	 * @param postFix 
	 * 
	 * @return Gets the stream name of the successes stream.
	 */
	private static final String streamName(String prefix, String postFix) {
		return prefix.trim() + postFix;		
	}
	
	
	
	
	
	
	/**
	 * Gets the stream from the specified provider, using the
	 * specified prefix and postfix.
	 * 
	 * @param p
	 *        Provider.
	 * @param prefix
	 *        Prefix of stream name.
	 * @param postfix 
	 *        Postfix of stream name.
	 *        
	 * @return Returns the stream.
	 * 
	 * @throws InitializationException
	 */
	private static NamedPrintStream stream(Provider p, String prefix, String postfix) 
	throws InitializationException {		
		NamedStreamsProvider nsp = p.getResource(LOGFILES_PROVIDER_NAME, NamedStreamsProvider.class);	
		String logname = streamName(prefix, postfix); 
		return (NamedPrintStream) nsp.getSharedStream(logname);
	}
	
	/**
	 * Gets the successes stream from the specified provider, using the
	 * specified stream name prefix.
	 * 
	 * @param p
	 *        Provider.
	 * @param prefix
	 *        Prefix of stream name.
	 *        
	 * @return Returns the stream.
	 * 
	 * @throws InitializationException
	 */
	public static NamedPrintStream successes(Provider p, String prefix) 
	throws InitializationException {		
		return stream(p, prefix, SUCCESSES);
	}
	
	/**
	 * Gets the successes stream from the specified provider, using the
	 * specified stream name prefix.
	 * 
	 * @param p
	 *        Provider.
	 * @param prefix
	 *        Prefix of stream name.
	 *        
	 * @return Returns the stream.
	 * 
	 * @throws InitializationException
	 */
	public static NamedPrintStream failures(Provider p, String prefix) 
	throws InitializationException {		
		return stream(p, prefix, FAILURES);
	}
	
	/**
	 * Gets the stacktraces stream from the specified provider, using the
	 * specified stream name prefix.
	 * 
	 * @param p
	 *        Provider.
	 * @param prefix
	 *        Prefix of stream name.
	 *        
	 * @return Returns the stream.
	 * 
	 * @throws InitializationException
	 */
	public static NamedPrintStream stacktraces(Provider p, String prefix) 
	throws InitializationException {		
		return stream(p, prefix, STACKTRACES);
	}
	
	/**
	 * Gets the stacktraces stream from the specified provider, using the
	 * specified stream name prefix.
	 * 
	 * @param p
	 *        Provider.
	 * @param prefix
	 *        Prefix of stream name.
	 *        
	 * @return Returns the stream. If an InitializationException occurs during\
	 *         stream initialization, then returns null.
	 */
	public static NamedPrintStream optionalStacktraces(Provider p, String prefix) {
		try {
			return stacktraces(p, prefix);
		} catch (InitializationException e) {
			return null;
		}
	}
	
	/**
	 * Gets the stream with the specified name from the specified provider.
	 * 
	 * @param p
	 *        Provider.
	 * @param streamName
	 *        stream name.
	 *        
	 * @return Returns the stream.
	 * 
	 * @throws InitializationException
	 */
	public static NamedStream<?> sharedStream(Provider p, String streamName) 
	throws InitializationException {		
		NamedStreamsProvider nsp = p.getResource(LOGFILES_PROVIDER_NAME, NamedStreamsProvider.class);
		return nsp.getSharedStream(streamName);
	}


	

}
