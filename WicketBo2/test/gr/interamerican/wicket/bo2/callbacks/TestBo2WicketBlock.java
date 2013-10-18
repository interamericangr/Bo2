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
package gr.interamerican.wicket.bo2.callbacks;

import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.samples.blocks.DummyBo2WicketBlock;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Bo2WicketBlock}.
 */
@SuppressWarnings("serial")
public class TestBo2WicketBlock 
extends Bo2WicketTest {
	
	/**
	 * Flag used for the tests.
	 */
	private boolean flag;
	
	/**
	 * block for the tests.
	 */
	private Bo2WicketBlock act = new Bo2WicketBlock() {			
		@Override
		public void work() 
		throws InitializationException, DataException, LogicException {			
			flag = true;
		}
	};
	
	
	/**
	 * Tests execute.
	 */
	@Test
	public void testExecute_okScenario() {		
		DummyBo2WicketBlock block = new DummyBo2WicketBlock();
		block.execute();
		assertTrue(block.executed);		
	}
	
	/**
	 * Tests execute throwing initialization exception.
	 */
	@Test(expected=RuntimeException.class)
	public void testExecute_throwingInitializationException() {		
		Bo2WicketBlock block = new Bo2WicketBlock() {			
			@Override
			public void work() throws InitializationException, DataException, LogicException {
				throw new InitializationException();
			}
		};
		block.execute();		
	}	
	
	/**
	 * Tests execute throwing data exception.
	 */
	@Test(expected=RuntimeException.class)
	public void testExecute_throwingDataException() {		
		Bo2WicketBlock block = new Bo2WicketBlock() {			
			@Override
			public void work() throws InitializationException, DataException, LogicException {
				throw new DataException();
			}
		};
		block.execute();		
	}
	
	/**
	 * Tests execute throwing logic exception.
	 */
	@Test(expected=RuntimeException.class)
	public void testExecute_throwingLogicException() {		
		Bo2WicketBlock block = new Bo2WicketBlock() {			
			@Override
			public void work() throws InitializationException, DataException, LogicException {
				throw new LogicException();
			}
		};
		block.execute();		
	}	
	
	/**
	 * Tests getProvider(0.
	 */
	@Test
	public void testGetProvider() {		
		Bo2WicketRequestCycle cycle = newBo2RequestCycle();
		Bo2WicketRequestCycle.beginRequest(cycle);
		flag = false;
		Bo2WicketBlock block = new Bo2WicketBlock() {			
			@Override
			public void work() 
			throws InitializationException, DataException, LogicException {
				Provider p = getProvider();
				Assert.assertNotNull(p);
				flag = true;
			}
		};
		block.execute();
		Assert.assertTrue(flag);
		Bo2WicketRequestCycle.endRequest(cycle);
	}
	
	
	/**
	 * Unit test for callback.
	 */
	@Test
	public void testCallBack_withTarget() {
		Page home = homePage();		
		AjaxRequestTarget target = new AjaxRequestTarget(home);		
		Bo2WicketRequestCycle cycle = newBo2RequestCycle();
		Bo2WicketRequestCycle.beginRequest(cycle);
		flag = false;		
		act.callBack(target);
		Assert.assertTrue(flag);
		Bo2WicketRequestCycle.endRequest(cycle);
	}
	
	/**
	 * Unit test for callback.
	 */
	@Test
	public void testCallBack_withTargetAndForm() {
		Page home = homePage();		
		AjaxRequestTarget target = new AjaxRequestTarget(home);
		Form<Object> form = new Form<Object>("formId"); //$NON-NLS-1$
		Bo2WicketRequestCycle cycle = newBo2RequestCycle();
		Bo2WicketRequestCycle.beginRequest(cycle);
		flag = false;
		act.callBack(target,form);
		Assert.assertTrue(flag);
		Bo2WicketRequestCycle.endRequest(cycle);
	}




	
	

}
