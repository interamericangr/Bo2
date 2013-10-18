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
package gr.interamerican.bo2.arch.utils.copiers;

import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.samples.po.SampleKey;

import org.junit.Test;

/**
 * Test for KeyCopier
 */
public class TestKeyCopier {

	/**
	 * KeyCopier
	 */
	KeyCopier copier = new KeyCopier();
	
	
	/**
	 * Test copy
	 */
	@Test
	public void testCopy(){
		SampleKey sample = new SampleKey();
		SampleKey newKey = (SampleKey) copier.copy(sample);
		assertNotNull(newKey);	
	}
	
}
	
	
	
