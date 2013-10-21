/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.impl.open.properties;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.implopen.parameters.SampleIntParam;
import gr.interamerican.bo2.samples.implopen.parameters.SampleStringParam;

import java.util.Properties;

import org.junit.Test;

/**
 * unit test for {@link FillDynamicProperiesOperation} 
 */
public class TestFillDynamicPropertiesOperation {
	/**
	 * tests the operation
	 * @throws InitializationException 
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testOperation() throws InitializationException, LogicException, DataException {
		String[] keys = {
			"a0", "a1", "a2", "a3", "a4", "a5", "a6"
		};
		String[] values = {				
			"somevalue",				
			FillDynamicProperiesOperation.DYNAPARM_PREFIX + SampleStringParam.class.getName(),				
			FillDynamicProperiesOperation.DYNAPARM_PREFIX + SampleIntParam.class.getName(),				
			FillDynamicProperiesOperation.COPYPARM_PREFIX + "a0",				
			FillDynamicProperiesOperation.COPYPARM_PREFIX +	"a1",				
			FillDynamicProperiesOperation.COPYPARM_PREFIX +	"a2",				
			"othervalue"
		};
		
		Properties input = new Properties();
		for (int i = 0; i < values.length; i++) {
			input.setProperty(keys[i], values[i]);
		}
		Provider p = Bo2.getDefaultDeployment().getProvider();
		
		FillDynamicProperiesOperation operation = 
			new FillDynamicProperiesOperation();
		operation.setInputProperties(input);
		
		
		operation.init(p);
		operation.open();
		operation.execute();
		operation.close();
	
		Properties output = operation.getOutputProperties();
	
		assertTrue(input.size()==output.size());
		
		assertEquals(output.getProperty(keys[0]), values[0]);
		assertEquals(output.getProperty(keys[1]), SampleStringParam.VALUE);
		assertEquals(output.get(keys[2]), SampleIntParam.VALUE);
		
		assertEquals(output.getProperty(keys[3]), values[0]);
		assertEquals(output.getProperty(keys[4]), SampleStringParam.VALUE);
		assertEquals(output.get(keys[5]), SampleIntParam.VALUE);
		
		assertEquals(output.getProperty(keys[6]), values[6]);
		
		assertNotNull(operation.getInputProperties());
	}
	
}
