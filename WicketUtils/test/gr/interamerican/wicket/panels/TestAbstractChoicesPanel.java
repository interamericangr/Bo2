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
package gr.interamerican.wicket.panels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;

import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link AbstractChoicesPanel}.
 */
public class TestAbstractChoicesPanel extends WicketTest implements Serializable {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Sample {@link ListModel} 
	 */
	private ListModel<BigInteger> listModel;

	/**
	 * Flag indicating if submit was run
	 */
	private boolean submited = false;

	/**
	 * Unit test for {@link AbstractChoicesPanel#AbstractChoicesPanel(String, String, IModel, java.util.function.BiFunction)}
	 */
	@Test
	public void testAbstractChoicesPanel() {
		IModel<Set<BigInteger>> testValueSet = Model.ofSet(new HashSet<>());
		AbstractChoicesPanel<BigInteger> tested = new AbstractChoicesPanel<BigInteger>(TestPage.TEST_ID, "testedLabel", //$NON-NLS-1$
				testValueSet, (i, l) -> {
					listModel = l;
					return new TextFieldChoicesContainerPanel<>(i, l, BigInteger.class);}) {

			/** Version UID */
			private static final long serialVersionUID = 1L;
		};
		LegacyCallbackAction doTheTest = (t) -> {
			assertEquals(1, testValueSet.getObject().size());
			submited = true;
		};
		
		TestPage testPage = new TestPage(doTheTest, tested, Markup.div);
		tester.startPage(testPage);
		commonAssertions_noError();
		listModel.getObject().add(BigInteger.ONE);
		// The Form Testing
		FormTester formTester = tester.newFormTester(TestPage.FORM_ID);
		assertFalse(submited);
		formTester.submit(TestPage.SUBMIT_BUTTON_ID);
		assertEquals(1, testValueSet.getObject().size());
		assertTrue(submited);
	}
}
