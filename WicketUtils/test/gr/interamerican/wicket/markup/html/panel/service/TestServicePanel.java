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
package gr.interamerican.wicket.markup.html.panel.service;

import static org.junit.Assert.assertNotNull;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;
import gr.interamerican.wicket.test.WicketTest;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class TestServicePanel  extends WicketTest{
	
	/**
	 * ServicePanelWithBackDefImpl
	*/
    private ServicePanelWithBackDefImpl impl = new ServicePanelWithBackDefImpl();
		
    /**
     * panel
     */
    private  ServicePanelImpl panel;
	
	/**
	 * Init
	 */
	@Before
	public void Init(){
		impl.setWicketId("wicketId"); //$NON-NLS-1$
		panel = new ServicePanelImpl(impl);
	}
	
	/**
	 * Test GetDefinition
	 */
	@Test
	public void testGetDefinition(){
		assertNotNull(panel.getDefinition());
	}
	
	/**
	 * implemenation to test
	 */
	private class ServicePanelImpl extends ServicePanel{

		/**
		 * UID.
		 */
		private static final long serialVersionUID = -7526385759755064958L;


		/**
		 * Creates a new ServicePanelImpl object. 
		 *
		 * @param definition
		 */
		public ServicePanelImpl(ServicePanelDef definition) {
			super(definition);
		}

		@Override
		protected void paint()  {/*empty */}

		@Override
		protected void init()  {/*empty */}


		@Override
		protected void validateDef() {/*empty */}
		
	}
	
	
}
