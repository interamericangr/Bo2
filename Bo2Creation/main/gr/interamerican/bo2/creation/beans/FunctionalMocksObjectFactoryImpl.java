package gr.interamerican.bo2.creation.beans;

import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Properties;

/**
 * 
 */
public class FunctionalMocksObjectFactoryImpl 
extends ObjectFactoryImpl {
	
	/**
	 * Resource path.
	 */
	private static final String PATH = 
		"/gr/interamerican.bo2.creation.beans.FunctionalMocksObjectFactoryImpl.properties"; //$NON-NLS-1$
	
	/**
	 * Properties.
	 */
	private static final Properties properties = 
		CollectionUtils.readProperties(PATH);
	
	
	
	

	/**
	 * Creates a new FunctionalMocksObjectFactoryImpl object.
	 * 
	 */
	public FunctionalMocksObjectFactoryImpl() {
		super(new ObjectFactoryAssistant(properties));		
	}

}
