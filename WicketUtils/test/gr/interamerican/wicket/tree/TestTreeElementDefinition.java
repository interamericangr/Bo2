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
import gr.interamerican.wicket.callback.PickAction;

/**
 * Unit test for {@link TreeElementDefinition}.
 */
public class TestTreeElementDefinition {

	/**
	 * Test method for {@link gr.interamerican.wicket.tree.TreeElementDefinition#TreeElementDefinition(gr.interamerican.wicket.callback.PickAction, gr.interamerican.bo2.utils.functions.SerializableFunction)}.
	 */
	@Test
	public void testTreeElementDefinitionPickActionOfTSerializableFunctionOfTString() {
		PickAction<String> action = (t,b) -> t.notify();
		SerializableFunction<String, String> labelFunc = String::toString;
		TreeElementDefinition<String> tested = new TreeElementDefinition<>(action,labelFunc);
		assertNotNull(tested);
		assertEquals(action,tested.action);
		assertEquals(labelFunc,tested.labelFunction);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.tree.TreeElementDefinition#addSubdefinition(gr.interamerican.bo2.utils.functions.SerializableFunction, gr.interamerican.wicket.tree.TreeElementDefinition)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAddSubdefinition() {
		SerializableFunction<String, String> labelFunction = s-> s + "Child Element";
		TreeElementDefinition<String> childDefinition = new TreeElementDefinition<String>(labelFunction);
		SerializableFunction<String, Collection<String>> func = Arrays::asList;

		TreeElementDefinition<String> tested = new TreeElementDefinition<String>();
		tested.addSubdefinition(func, childDefinition);
		
		assertEquals(1,tested.getSubDefinitions().size());
		assertEquals(labelFunction, tested.subDefinitions.get(0).getChildDefinition().getLabelFunction());
		assertEquals(func, tested.subDefinitions.get(0).getGetter());
		assertEquals(childDefinition, tested.subDefinitions.get(0).getChildDefinition());
	}

}
