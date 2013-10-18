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
package gr.interamerican.bo2.utils;

import static gr.interamerican.bo2.utils.TimeUtils.hours2millis;
import static gr.interamerican.bo2.utils.TimeUtils.minutes2millis;
import static gr.interamerican.bo2.utils.TimeUtils.seconds2millis;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link TimeUtils}.
 */
public class TestTimeUtils {
	
	/**
	 * Tests hours2millis
	 */
	@Test
	public void testHours2Millis() {
		assertEquals(minutes2millis(180), hours2millis(3));
	}
	
	/**
	 * Tests minutes2millis
	 */
	@Test
	public void testMinutes2Millis() {
		assertEquals(seconds2millis(120), minutes2millis(2));
	}
	
	/**
	 * Tests minutes2millis
	 */
	@Test
	public void testSeconds2Millis() {
		assertEquals(1000L, seconds2millis(1));
	}


}
