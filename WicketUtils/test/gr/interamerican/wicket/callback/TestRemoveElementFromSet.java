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
package gr.interamerican.wicket.callback;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Unit test of {@link RemoveElementFromSet}.
 */
public class TestRemoveElementFromSet {

	/**
	 * Test method for {@link RemoveElementFromSet#consume(java.io.Serializable)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConsume() {
		Set<String> set = new HashSet<String>();
		set.add("something");
		set.add("Othersomething");
		set.add("somethingElse");
		RemoveElementFromSet<String> tested = new RemoveElementFromSet<String>(set);
		tested.consume("someone");
		assertEquals(3, set.size());
		tested.consume("something");
		assertEquals(2, set.size());
	}
}