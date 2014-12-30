package gr.interamerican.bo2.impl.open.runtime.concurrent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.batch.LongProcessLauncher;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.beans.Pair;

/**
 * Launcher for {@link LongProcess}es.
 */
public class LPL {
	
	/**
	 * Argument specifier for paths.
	 */
	public static final String PATH = "INPUT_PATH"; //$NON-NLS-1$
	
	/**
	 * Argument specifier for URLs.
	 */
	public static final String URL = "INPUT_URL"; //$NON-NLS-1$
	
	/**
	 * Argument specifier for resource streams.
	 */
	public static final String RESOURCE = "INPUT_RESOURCE"; //$NON-NLS-1$

	
	/**
	 * Main method.
	 * 
	 * @param args
	 */
	@SuppressWarnings({ "nls" })
	public static void main(String[] args) {
		LPL lpl = new LPL();
		Properties p = new Properties();
		for (int i = 0; i < args.length; i++) {
			lpl.parseArgument(p, args[i]);
		}
		String className = p.getProperty(BatchProcessParmNames.LAUNCHER);
		if (StringUtils.isNullOrBlank(className)) {
			String msg = "launcher property not set";
			throw new RuntimeException(msg);
		}
		LongProcessLauncher launcher = (LongProcessLauncher)Factory.create(className);
		launcher.launch(p);
	}
	
	
	/**
	 * Parses an argument.
	 * 
	 * @param properties
	 * @param argument
	 */
	void parseArgument(Properties properties, String argument) {
		Pair<String, String> pair = parse(argument, '=');
		String key = pair.getLeft();
		String value = pair.getRight();
		try {
			InputStream stream = getStream(key, value);
			if (stream!=null) {
				Properties p = CollectionUtils.readEnhancedProperties(stream);
				properties.putAll(p);
			} else {
				properties.setProperty(key, value);
			}			
		} catch (IOException ioe) {
			String msg = "Could not load input stream for argument " + argument; //$NON-NLS-1$
			throw new RuntimeException(msg,ioe);
		}
	}
	
	/**
	 * Opens a stream.
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws IOException
	 */
	InputStream getStream(String key, String value) throws IOException {		
		if (PATH.equals(key)) {
			return StreamUtils.getFileStream(value);
		} else if (RESOURCE.equals(key)) {
			return StreamUtils.getResourceStream(value);
		} else if (URL.equals(key)) {
			java.net.URL url = new java.net.URL(value);
			return url.openStream();
		}
		return null;
	}
	
	/**
	 * Parses the specified argument.
	 * 
	 * @param argument
	 * @param delimiter
	 * @return returns the pair that contains the key as left
	 *         part and the value as right.
	 */
	Pair<String, String> parse(String argument, char delimiter) {
		String[] parts = TokenUtils.split(argument, delimiter);
		if (parts.length!=2) {
			invalid(argument);
		}
		return new Pair<String, String>(parts[0], parts[1]);
	}
	
	/**
	 * Throws a RuntimeException when an invalid argument is found.
	 * 
	 * @param argument
	 */
	void invalid(String argument) {
		String msg = "Invalid argument " + argument; //$NON-NLS-1$
		throw new RuntimeException(msg); 
	}
	
	

}
