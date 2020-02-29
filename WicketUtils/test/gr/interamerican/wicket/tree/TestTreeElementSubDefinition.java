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
package gr.interamerican.wicket.tree;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import gr.interamerican.bo2.utils.functions.SerializableFunction;

/**
 * Unit tests for {@link TreeElementSubDefinition}
 */
public class TestTreeElementSubDefinition {

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.TreeElementSubDefinition#TreeElementSubDefinition(gr.interamerican.bo2.utils.functions.SerializableFunction, gr.interamerican.wicket.tree.TreeElementDefinition)}.
	 */
	@Test
	public void testTreeElementSubDefinition() {
		TreeElementDefinition<String> childDefinition = new TreeElementDefinition<String>();
		SerializableFunction<String, Collection<String>> getterFunc = Arrays::asList;
		TreeElementSubDefinition<String, String> tested = new TreeElementSubDefinition<String, String>(getterFunc,childDefinition);
		
		assertNotNull(tested);
		assertEquals(childDefinition,tested.getChildDefinition());
		assertEquals(getterFunc, tested.getGetter());
	}

}
