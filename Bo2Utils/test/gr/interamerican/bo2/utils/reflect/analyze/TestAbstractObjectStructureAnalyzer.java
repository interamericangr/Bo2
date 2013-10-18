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
package gr.interamerican.bo2.utils.reflect.analyze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.samples.bean.Family;
import gr.interamerican.bo2.samples.bean.Samples;
import gr.interamerican.bo2.utils.beans.Tree;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import org.junit.Test;

/**
 * Unit tests for {@link AbstractObjectStructureAnalyzer}.
 */
public class TestAbstractObjectStructureAnalyzer {
	
	/**
	 * Tests getStructure(object)
	 *  
	 */	
	@Test
	public void testExecute() {
		Family family = Samples.theAstaireFamily();		
		AbstractObjectStructureAnalyzer analyzer = new DeclaredFieldsAnalyzer();
		Tree<VariableDefinition<?>> tree = analyzer.execute(family);
		assertNotNull(tree);
		assertEquals(5,tree.getNodes().size());
		assertEquals(41,tree.getTotalNodesCount());
		System.out.println(tree);
		
	}

}
