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
package gr.interamerican.bo2.impl.open.hibernate.utils.reflect.analyze;

import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.po.utils.DefaultPoAnalyzer;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;

import org.junit.Test;

/**
 * Tests {@link HibernateAwarePoAnalyzerResolver}.
 */
public class TestHibernateAwarePoAnalyzerResolver {

	/**
	 * Test method for {@link HibernateAwarePoAnalyzerResolver#getAnalyzer(Class, String)}.
	 * 
	 * @throws InitializationException
	 */
	@Test
	public void testGetAnalyzer() throws InitializationException {
		Bo2Session.setProvider(Bo2.getProvider());
		HibernateAwarePoAnalyzerResolver tested = new HibernateAwarePoAnalyzerResolver();
		assertTrue(tested.getAnalyzer(User.class, "LOCALDB") instanceof HibernatePoAnalyzer); //$NON-NLS-1$
		assertTrue(tested.getAnalyzer(User.class, "wrongManager") instanceof DefaultPoAnalyzer); //$NON-NLS-1$
		assertTrue(tested.getAnalyzer(UserKey.class, "LOCALDB") instanceof DefaultPoAnalyzer); //$NON-NLS-1$
	}
}