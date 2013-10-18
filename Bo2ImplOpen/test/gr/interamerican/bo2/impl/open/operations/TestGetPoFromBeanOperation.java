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
package gr.interamerican.bo2.impl.open.operations;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import org.junit.Test;

/**
 * Unit test for {@link GetPoFromBeanOperation}.
 *  
 */
public class TestGetPoFromBeanOperation {
	
	
	
	/**
	 * Unit test for the whole lifecycle.
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws InitializationException 
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testLifecycle() throws InstantiationException, IllegalAccessException, InitializationException, LogicException, DataException {
		VariableDefinition<Integer> vd1 = 
			new VariableDefinition<Integer>("id", Integer.class);
		VariableDefinition<String> vd2 = 
			new VariableDefinition<String>("name", String.class);
		VariableDefinition<?>[] vars = {vd1, vd2};
		String newClass = "gr.interamerican.synth.TestGetPoFromBeanOperationSyntheticBean";
		Class<?> clazz = Factory.compileJavaBean(newClass, vars);
		Object bean = clazz.newInstance();
		Integer id = UtilityForBo2Test.getExistingUserId();
		ReflectionUtils.setProperty("id", id, bean);
		ReflectionUtils.setProperty("name", "Mr Bean", bean);
		
		Provider provider = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();
		
		GetPoFromBeanOperation<User> getPo = new GetPoFromBeanOperation<User>(User.class);
		getPo.init(provider);
		getPo.open();
		getPo.setBean(bean);
		getPo.execute();
		getPo.close();
		
		User us = getPo.getPo();
		assertNotNull(us);
		assertEquals(us.getId(), id);
		
	}
	
	/**
	 * Tests getKeyProperties()
	 * @throws LogicException 
	 */
	@Test
	public void testGetKeyProperties() throws LogicException {
		GetPoFromBeanOperation<User> getPo = new GetPoFromBeanOperation<User>(User.class);
		String[] actuals = getPo.getKeyProperties();
		String expecteds[] = new String[]{"id"}; //$NON-NLS-1$
		assertArrayEquals(expecteds, actuals);
	}

}
