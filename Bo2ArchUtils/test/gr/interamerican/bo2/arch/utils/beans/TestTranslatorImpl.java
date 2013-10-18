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
package gr.interamerican.bo2.arch.utils.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Tests TranslatorImpl
 */
public class TestTranslatorImpl {
	
	/**
	 * translator instance for this test
	 */
	private TranslatorImpl<Long, Long> translator = 
		new TranslatorImpl<Long,Long>();
	
	/**
	 * tests TranslatorImpl.learn()
	 */
	@Test
	public void learnTranslateTest() {
		String expected = "translation";  //$NON-NLS-1$		
		translator.learn(1L, 2L, expected);
		String actual = translator.translate(1L, 2L);
		assertEquals(expected, actual); 
	}
	
	/**
	 * tests TranslatorImpl.translate()
	 */
	@Test
	public void translateFailedTest() {
		
		assertNull(translator.translate(2L, 2L));
	}
	
	/**
	 * test learn when languageId is null
	 */
	@Test
	public void testLearn_nullValue(){
		translator.learn(1l, null,"translation"); //$NON-NLS-1$
	}
	
	
	/**
	 * test learn when languageId is null
	 */
	@Test
	public void testTranslate_nullValue(){
		assertNull(translator.translate(1l, null));
	}

	
//	private class Sample extends TranslatorImpl<Integer,Integer>{
//		
//		private Sample(){
//			super.
//		}
//	}
	
}
