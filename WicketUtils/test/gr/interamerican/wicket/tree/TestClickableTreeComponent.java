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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Wicket test for {@link ClickableNestedTree}.
 */
public class TestClickableTreeComponent extends WicketTest{

	/**
	 * Test that renders the tree component, and clicks the "Expand" button on its root node.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testTreeFunctionality() {
		TreeElementDefinition<String> rootDefinition = new TreeElementDefinition<String>();
		TreeElementDefinition<String> childDefinition = new TreeElementDefinition<>(String::toString);
		rootDefinition.addSubdefinition(TestClickableTreeComponent::toStringList, childDefinition);
		
		TreeElement<String> rootElement = new TreeElement<String>("Root", rootDefinition, StringConstants.EMPTY, StringConstants.EMPTY);
		TreeProvider treeModel = new TreeProvider(rootElement);
		ClickableNestedTree tree = new ClickableNestedTree("tree", treeModel);

		tester.startComponentInPage(tree);
		tester.clickLink("tree:subtree:branches:1:node:junction",false);
		tester.assertNoErrorMessage();
		tester.assertNoFeedbackMessage(FeedbackMessage.ERROR);
	}

	/**
	 * Method that converts a String into a List, that contains the characters of the input String. 
	 * @param input The input String.
	 * @return A list filled with the input's characters.
	 */
	static List<String> toStringList(String input) {
		List<String> result = new ArrayList<String>();
		char[] charArray = input.toCharArray();
		for(char c: charArray) {
			result.add(String.valueOf(c));
		}
		return result;
	}

}
