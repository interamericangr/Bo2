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
package gr.interamerican.bo2.impl.open.po.utils;

import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.analysis.PoAnalysis;

import org.junit.Test;

/**
 * Unit Test of {@link DefaultPoAnalyzerResolver}.
 */
public class TestDefaultPoAnalyzerResolver {

	/**
	 * Test method for {@link DefaultPoAnalyzerResolver#getAnalyzer(Class, String)}.
	 */
	@Test
	public void testGetAnalyzer() {
		DefaultPoAnalyzerResolver tested = DefaultPoAnalyzerResolver.INSTANCE;
		tested.registerAnalyzer(getClass(), "foo", new DummyAnalyzer()); //$NON-NLS-1$
		assertTrue(tested.getAnalyzer(null, "foo") instanceof DefaultPoAnalyzer); //$NON-NLS-1$
		assertTrue(tested.getAnalyzer(getClass(), "foo") instanceof DummyAnalyzer); //$NON-NLS-1$
		assertTrue(tested.getAnalyzer(getClass(), "oo") instanceof DefaultPoAnalyzer); //$NON-NLS-1$
	}
}

/**
 * Dummy {@link PoAnalyzer}
 */
class DummyAnalyzer implements PoAnalyzer {

	@Override
	public PoAnalysis getAnalysis(Class<?> class1) {
		return Factory.create(PoAnalysis.class);
	}
}