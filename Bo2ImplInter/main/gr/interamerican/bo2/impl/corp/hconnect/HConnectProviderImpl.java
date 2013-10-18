package gr.interamerican.bo2.impl.corp.hconnect;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.utils.Exceptions;
import gr.interamerican.bo2.impl.open.utils.Messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import jwf.HConnect;

/**
 * Basic implementation of HConnectProvider.
 * 
 */
public class HConnectProviderImpl 
implements HConnectProvider {	
	
	/**
	 * property key for CICS IP
	 */
	private static final String CICSIP = "CICSIP"; //$NON-NLS-1$
	/**
	 * property key for CICS PORT
	 */
	private static final String CICSPORT = "CICSPORT";	 //$NON-NLS-1$
	/**
	 * property key for CICS ID
	 */
	private static final String CICSID = "CICSID"; //$NON-NLS-1$
	/**
	 * property key for CICS USER
	 */
	private static final String CICSUSER = "CICSUSER"; //$NON-NLS-1$
	/**
	 * property key for CICS PASSWORD
	 */
	private static final String CICSPASS = "CICSPASS"; //$NON-NLS-1$
	/**
	 * property key for TRANS ID
	 */
	private static final String TRANSID = "TRANSID"; //$NON-NLS-1$
	
	/**
	 * Initialization properties.
	 */
	private Properties properties;
	
	/**
	 * Gets a mandatory property from the properties file.
	 * 
	 * @param key Property key
	 * @return Returns the value that is assigned to the key.
	 * 
	 */
	private String getMandatoryProperty(String key)	{
		String tmp = properties.getProperty(key);
		if (tmp==null) {
			throw Exceptions.runtime(Messages.PROPERTY_NOT_FOUND, key);
		}
		return tmp;
	}
	
	/**
	 * Initializes this object from the initialization properties.
	 * 
	 */
	protected void initialize() {		
		String ip = properties.getProperty(CICSIP);
		String strPort = getMandatoryProperty(CICSPORT);
		int port = Integer.parseInt(strPort);
        String cicsid=getMandatoryProperty(CICSID);
        String cicsuser=getMandatoryProperty(CICSUSER);
        String cicspass=getMandatoryProperty(CICSPASS);
        
        this.transId=getMandatoryProperty(TRANSID);
        
        this.hconnect = new HConnect(ip,port);
        this.hconnect.setUserid(cicsuser);
        this.hconnect.setPassword(cicspass);
        this.hconnect.setCicsid(cicsid);
	}
	
	/**
	 * Creates a new HConnectProviderImpl object. 
	 *
	 * @param properties
	 */
	public HConnectProviderImpl(Properties properties) {
		this.properties = properties;
	}
	
	/**
	 * HConnect object
	 */
	private HConnect hconnect;
	
	/**
	 * transaction id
	 */
	private String transId;
	
	/**
	 * business Exception Return Codes
	 */
	private ArrayList<String> businessExceptionReturnCodes = 
		new ArrayList<String>();
	
	
	/**
	 * Assigns a new value to the hconnect.
	 *
	 * @param hconnect the hconnect to set
	 */
	public void setHconnect(HConnect hconnect) {
		this.hconnect = hconnect;
	}

	/**
	 * Assigns a new value to the transId.
	 *
	 * @param transId the transId to set
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	}

	/**
	 * Assigns a new value to the businessExceptionReturnCodes.
	 *
	 * @param businessExceptionReturnCodes the businessExceptionReturnCodes to set
	 */
	public void setBusinessExceptionReturnCodes(
			Collection<String> businessExceptionReturnCodes) {
		this.businessExceptionReturnCodes.clear();
		this.businessExceptionReturnCodes.addAll(businessExceptionReturnCodes);
	}

	public Collection<String> getBusinessExceptionReturnCodes() {		
		return businessExceptionReturnCodes;
	}

	public HConnect getHConnect() {		
		return hconnect;
	}

	public String getTransId() {		
		return transId;
	}

	public void close() throws DataException {
		/* empty */
		
	}
	
}
