/*
 * Created on 6 Απρ 2006
 *
 */
package gr.interamerican.bo2.impl.corp.hconnect;



import gr.interamerican.bo2.arch.ResourceWrapper;

import java.util.Collection;

import jwf.HConnect;


/**
 * HConnect provider
 * 
 * HConnect is a communications gateway with the mainframe
 * built and used by Interamerican.
 *
 */
public interface HConnectProvider extends ResourceWrapper {
	

	
	/**
	 * Gets an HConnect object.
	 * 
	 * @return Returns an HConnect object
	 */
    public HConnect getHConnect();
    
    /**
     * Gets a collection of strings that indicate a business exception.
     * 
     * When the string returned by the CICS transaction is 
     * @return a Collection with Strings that indicate a business exception
     */
	public Collection<String> getBusinessExceptionReturnCodes();
	
	/**
	 * Gets the id of the CICS transaction that will be called
	 * by the HConnect object.
	 * 
	 * @return Returns the CCIS transaction Id
	 */
	public String getTransId();
	
	
}
