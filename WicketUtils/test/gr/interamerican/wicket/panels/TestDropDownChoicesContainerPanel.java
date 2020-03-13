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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.util.ListModel;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;

/**
 * Test for {@link DropDownChoicesContainerPanel}
 */
public class TestDropDownChoicesContainerPanel extends WicketTest {

	/**
	 * The wicket id of the {@link DropDownChoice} inside the {@link DropDownChoicesContainerPanel}
	 */
	private static final String DDC_WICKET_ID = "ddc"; //$NON-NLS-1$

	/**
	 * Test method for {@link DropDownChoicesContainerPanel}
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testDropDownChoicesContainerPanel() {
		DropDownChoicesContainerPanel<BigInteger> ddcContainterPanel = startAndTestComponent(DropDownChoicesContainerPanel.class);
		DropDownChoice<BigInteger> ddc = (DropDownChoice<BigInteger>) ddcContainterPanel.get(DDC_WICKET_ID);
		assertFalse(ddc.getChoices().isEmpty());
		assertEquals(2, ddc.getChoices().size());
		assertEquals(ddc.getChoices(), Arrays.asList(BigInteger.ONE, BigInteger.TEN));
	}
	
	@Override
	protected Component initializeComponent(String wicketId) {
		ListModel<BigInteger> currentValuesModel = new ListModel<>();
		List<BigInteger> currentValues = new ArrayList<>();
		currentValues.add(BigInteger.ZERO);
		currentValuesModel.setObject(currentValues);
		Set<BigInteger> valueSet = new HashSet<>();
		valueSet.add(BigInteger.ZERO);
		valueSet.add(BigInteger.ONE);
		valueSet.add(BigInteger.TEN);
		return new DropDownChoicesContainerPanel<>(wicketId, currentValuesModel, valueSet);
	}
}
