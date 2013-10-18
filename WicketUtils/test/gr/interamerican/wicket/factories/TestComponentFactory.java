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
package gr.interamerican.wicket.factories;

import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.components.BigDecimalTextField;
import gr.interamerican.wicket.components.DoubleTextField;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.MarkupConstants;
import gr.interamerican.wicket.utils.WicketUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link ComponentFactory}.
 */
public class TestComponentFactory extends WicketTest{
	
	/**
	 * the WicketTester
	 */
	public WicketTester wicketTester = null;

	/**
	 * 
	 */
	@Before
	public void setUp(){	
		wicketTester = new WicketTester();
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addDropDownWithChoiceRenderer(java.lang.String, java.lang.String, java.util.List)}.
	 */
	@Test
	public void testAddDropDownWithChoiceRenderer() {
		DropDownChoice<String> testDropDownChoice = ComponentFactory.addDropDownWithChoiceRenderer(TestPage.TEST_ID,null,new ArrayList<String>());
		wicketTester.startPage(new ComponentFactoryPage(testDropDownChoice, MarkupConstants.SELECT,StringConstants.EMPTY));	
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), DropDownChoice.class); 
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addCheckBoxes(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddCheckBoxes() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addCheckBoxes(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.CHECKBOX));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), CheckBox.class);
		
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addLabels(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddLabels() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addLabels(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.SPAN,StringConstants.EMPTY));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), Label.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addTextFieldsWithoutBinding(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddTextFieldsWithoutBinding() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addTextFieldsWithoutBinding(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), TextField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addTextFields(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddTextFields() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addTextFields(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), TextField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addTextAreaFields(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddTextAreaFields() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addTextAreaFields(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.TEXTAREA,StringConstants.EMPTY));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), TextArea.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addDisableTextFields(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddDisableTextFields() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addDisableTextFields(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), TextField.class);
		wicketTester.isEnabled(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID));
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addDateFields(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddDateFields() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addDateFields(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.SPAN,StringConstants.EMPTY));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), DateField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addDateFieldsWithoutBinding(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddDateFieldsWithoutBinding() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addDateFieldsWithoutBinding(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.SPAN,StringConstants.EMPTY));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), DateField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addDateTextFields(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddDateTextFields() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addDateTextFields(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,StringConstants.EMPTY));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), DateTextField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addRequiredDoubleTextField(org.apache.wicket.MarkupContainer, java.lang.String[], java.lang.Integer)}.
	 */
	@Test
	public void testAddRequiredDoubleTextField() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		Integer numberOfDecimals = 2;
		ComponentFactory.addRequiredDoubleTextField(form, wicketIds,numberOfDecimals);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), DoubleTextField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addDoubleTextField(org.apache.wicket.MarkupContainer, java.lang.String[], java.lang.Integer)}.
	 */
	@Test
	public void testAddDoubleTextField() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		Integer numberOfDecimals = 2;
		ComponentFactory.addDoubleTextField(form, wicketIds, numberOfDecimals);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), DoubleTextField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addDoubleTextFieldWithBinding(java.lang.String, org.apache.wicket.model.CompoundPropertyModel, java.lang.Integer)}.
	 */
	@Test
	public void testAddDoubleTextFieldWithBinding() {
		String wicketId ="doubleField_2"; //$NON-NLS-1$
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		Integer numberOfDecimals = 2;
		Bean1 bean1 = new Bean1();
		TextField<Double> doubleField = ComponentFactory.addDoubleTextFieldWithBinding(wicketId, new CompoundPropertyModel<Bean1>(bean1), numberOfDecimals); 
		form.add(doubleField);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT,wicketId));	
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,wicketId), DoubleTextField.class); 

	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addDoubleTextFields(org.apache.wicket.MarkupContainer, java.lang.String[], java.lang.Object, java.lang.Integer)}.
	 */
	@Test
	public void testAddDoubleTextFields() {
		String wicketId ="doubleField"; //$NON-NLS-1$
		String[] wicketIds =  new String[]{wicketId}; 
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		Integer numberOfDecimals = 2;
		Bean1 bean1 = new Bean1();
		ComponentFactory.addDoubleTextFields(form, wicketIds, bean1, numberOfDecimals);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT, wicketId));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,wicketId), DoubleTextField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addDoubleTextFieldWithoutBinding(org.apache.wicket.MarkupContainer, java.lang.String[], java.lang.Integer)}.
	 */
	@Test
	public void testAddDoubleTextFieldWithoutBinding() {	
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		Integer numberOfDecimals = 2;
		ComponentFactory.addDoubleTextFieldWithoutBinding(form, wicketIds, numberOfDecimals);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), DoubleTextField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addBigDecimalTextFields(org.apache.wicket.MarkupContainer, java.lang.String[], java.lang.Object)}.
	 */
	@Test
	public void testAddBigDecimalTextFields() {
		String wicketId ="doubleField"; //$NON-NLS-1$
		String[] wicketIds = new String[]{wicketId};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		Bean1 bean1 = new Bean1();
		ComponentFactory.addBigDecimalTextFields(form, wicketIds, bean1);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT,wicketId));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,wicketId), TextField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addBigDecimalTextFields(org.apache.wicket.MarkupContainer, java.lang.String[], java.lang.Integer)}.
	 */
	@Test
	public void testAddBigDecimalTextField_With_Decimals() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		Integer numberOfDecimals = 2;
		ComponentFactory.addBigDecimalTextFields(form, wicketIds, numberOfDecimals);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), BigDecimalTextField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addBigDecimalTextFieldsWithoutBinding(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddBigDecimalTextFieldsWithoutBinding() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addBigDecimalTextFieldsWithoutBinding(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), TextField.class);
	}
	
	
	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addBigDecimalTextFieldWithBinding(java.lang.String, org.apache.wicket.model.CompoundPropertyModel, java.lang.Integer)}.
	 */
	@Test
	public void testAddBigDecimalTextFieldWithBinding() {
		String wicketId ="bigDecimalTextField"; //$NON-NLS-1$
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		Integer numberOfDecimals = 2;
		Bean1 bean1 = new Bean1();
		TextField<BigDecimal> bigDecimalField = ComponentFactory.addBigDecimalTextFieldWithBinding(wicketId, new CompoundPropertyModel<Bean1>(bean1), numberOfDecimals); 
		form.add(bigDecimalField);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT,wicketId));	
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,wicketId), BigDecimalTextField.class); 
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addLongTextField(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddLongTextField() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addLongTextField(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), TextField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addIntegerTextField(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddIntegerTextField() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addIntegerTextField(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), TextField.class);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addPercentageTextField(org.apache.wicket.MarkupContainer, java.lang.String[], int)}.
	 */
	@Test
	public void testAddPercentageTextField() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addPercentageTextField(form, wicketIds,4);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.TEXT));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), TextField.class);
	}
	
	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#addAjaxCheckBox(org.apache.wicket.MarkupContainer, java.lang.String[])}.
	 */
	@Test
	public void testAddAjaxCheckBox() {
		String[] wicketIds = new String[]{TestPage.TEST_ID};
		Form<Void> form = new Form<Void>(TestPage.FORM_ID);
		ComponentFactory.addAjaxCheckBox(form, wicketIds);
		wicketTester.startPage(new ComponentFactoryPage(form, MarkupConstants.INPUT,MarkupConstants.CHECKBOX));
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), CheckBox.class);
	}
	
	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#returnViewLabels(String, String)}.
	 */
	@Test
	public void testReturnViewLabels(){
		Label label = ComponentFactory.returnViewLabels(TestPage.TEST_ID,TestPage.TEST_ID);
		wicketTester.startPage(new ComponentFactoryPage(label, MarkupConstants.SPAN,StringConstants.EMPTY));		
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), Label.class);
	}
	
	/**
	 * Test method for {@link gr.interamerican.wicket.factories.ComponentFactory#returnViewLabels(String, Enum)}.
	 */
	@Test
	public void testReturnViewLabelsWithEnum(){

		Label label = ComponentFactory.returnViewLabels(TestPage.TEST_ID,TestEnum.OBJECT1);
		wicketTester.startPage(new ComponentFactoryPage(label, MarkupConstants.SPAN,StringConstants.EMPTY));		
		wicketTester.assertComponent(WicketUtils.wicketPath(TestPage.FORM_ID,TestPage.TEST_ID), Label.class);
	}
	
	/**
	 * Dummy Enum.
	 */
	public enum TestEnum{
	    /**
	     * OBJECT1.
	     */
		OBJECT1;
	}

}
