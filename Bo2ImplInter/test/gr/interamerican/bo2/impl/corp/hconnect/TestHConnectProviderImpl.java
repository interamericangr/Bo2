package gr.interamerican.bo2.impl.corp.hconnect;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import java.util.ArrayList;
import java.util.Properties;

import jwf.HConnect;

import org.junit.Test;

/**
 * HConnectProviderTest
 * 
 * TODO: tests must have assertions.
 */
public class TestHConnectProviderImpl {
	
	
	/**
	 * transId
	 */
	private static final String transId = "id"; //$NON-NLS-1$
	
	/**
	 * HConnect
	 */
	private static final HConnect hconnect = new HConnect();
	
	
	/**
	 * returnCodes
	 */
	private static final ArrayList<String> returnCodes = new ArrayList<String>();
	
	/**
	 * Properties
	 */
	Properties properties = UtilityForBo2Test.getTestProperties();
	
	/**
	 * HConnectProviderImpl
	 */
	HConnectProviderImpl provider = new HConnectProviderImpl(properties);
	
	/**
	 * Tests initialize
	 */
	@Test
	public void testInitialize(){
		properties.put("CICSPORT", "1527"); //$NON-NLS-1$ //$NON-NLS-2$
		properties.put("CICSID", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		properties.put("CICSUSER", "nakosspy"); //$NON-NLS-1$ //$NON-NLS-2$
		properties.put("CICSPASS", "pass"); //$NON-NLS-1$ //$NON-NLS-2$
		properties.put("TRANSID", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		provider.initialize();
	}
	
	/**
	 * Test setConnect
	 */
	@Test
	public void testSetConnect(){
		provider.setHconnect(hconnect);
	}
	
	
	/**
	 * Test setTransId
	 */
	@Test
	public void testSetTransId(){
		provider.setTransId(transId);
		
	}
	
	/**
	 * Test setBusinessExceptionReturnCodes
	 */
	@Test
	public void testSetBusinessExceptionReturnCodes(){
		provider.setBusinessExceptionReturnCodes(returnCodes);
	}
	

	/**
	 * Test getBusinessExceptionReturnCodes
	 */
	@Test
	public void testGetBusinessExceptionReturnCodes(){
		provider.setBusinessExceptionReturnCodes(returnCodes);
		assertEquals(returnCodes,provider.getBusinessExceptionReturnCodes());
	}
	
	/**
	 * Test getHConnect
	 */
	@Test
	public void testGetHConnect(){
		provider.setHconnect(hconnect);
		assertEquals(hconnect,provider.getHConnect());
	}
	
	/**
	 * Test getTransId
	 */
	@Test
	public void testGetTransId(){
		provider.setTransId(transId);
		assertEquals(transId,provider.getTransId());
	}
	
	
	
}
