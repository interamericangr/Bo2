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
package gr.interamerican.bo2.samples.utils.meta;

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BigDecimalBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BooleanBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.DateBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.DoubleBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.FloatBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.IntegerBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.LongBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.SelectionBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 */
public class Bean1descriptor 
extends BasicBusinessObjectDescriptor<Bean1> {

	/**
	 * Creates a new Bean1descriptor object. 
	 *
	 */
	public Bean1descriptor() {
		super();
		
		BoPropertyDescriptor<?>[] descriptors = {
				idDescriptor(),
				descriptionDescriptor(),
				issueDateDesccription(),
				renewalDateDesccription(),
				percentageDescriptor(),
				amountDescriptor(),	
				booleanDescriptor(),
				floatDescriptor(),
				integerDescriptor()
		};
		this.setPropertyDescriptors(Arrays.asList(descriptors));
	}
	
	/**
	 * Description for field id.
	 * @return Description for the field.
	 */
	public LongBoPropertyDescriptor idDescriptor() {
		LongBoPropertyDescriptor idDesc = new LongBoPropertyDescriptor();
		idDesc.setName("id"); //$NON-NLS-1$
		idDesc.setHasDefault(false);		
		idDesc.setNegativeAllowed(false);
		idDesc.setNullAllowed(false);
		idDesc.setZeroAllowed(false);
		idDesc.setReadOnly(false);
		idDesc.setPackageName(Bean1.class.getPackage().getName());
		idDesc.setClassName(Bean1.class.getSimpleName());

		return idDesc;
	}
	
	/**
	 * Description for field description.
	 * @return Description for the field.
	 */
	public StringBoPropertyDescriptor descriptionDescriptor() {	
		StringBoPropertyDescriptor descriptionDesc = new StringBoPropertyDescriptor();
		descriptionDesc.setName("description"); //$NON-NLS-1$
		descriptionDesc.setDefaultValue(StringConstants.SPACE);
		descriptionDesc.setHasDefault(true);		
		descriptionDesc.setMinLength(0);
		descriptionDesc.setMaxLength(30);
		descriptionDesc.setNullAllowed(false);
		descriptionDesc.setReadOnly(false);
		descriptionDesc.setPackageName(Bean1.class.getPackage().getName());
		descriptionDesc.setClassName(Bean1.class.getSimpleName());
		return descriptionDesc;
	}
	/**
	 * Description for field description.
	 * @return Description for the field.
	 */
	public StringBoPropertyDescriptor textAreaDesccription() {	
		StringBoPropertyDescriptor descriptionDesc = new StringBoPropertyDescriptor();
		descriptionDesc.setName("testTextArea"); //$NON-NLS-1$
		descriptionDesc.setDefaultValue(StringConstants.SPACE);
		descriptionDesc.setHasDefault(true);		
		descriptionDesc.setMinLength(2);
		descriptionDesc.setMaxLength(60);
		descriptionDesc.setNullAllowed(false);
		descriptionDesc.setReadOnly(false);
		descriptionDesc.setPackageName(Bean1.class.getPackage().getName());
		descriptionDesc.setClassName(Bean1.class.getSimpleName());
		return descriptionDesc;
	}
	
	
	/**
	 * Description for field percentage.
	 * @return Description for the field.
	 */
	public DoubleBoPropertyDescriptor percentageDescriptor() {
		DoubleBoPropertyDescriptor percentageDesc = new DoubleBoPropertyDescriptor();
		percentageDesc.setName("percentage"); //$NON-NLS-1$
		percentageDesc.setDefaultValue(4.2);
		percentageDesc.setHasDefault(true);
		percentageDesc.setLengthOfIntegerPart(12);		
		percentageDesc.setLengthOfDecimalPart(4);
		percentageDesc.setNegativeAllowed(false);
		percentageDesc.setNullAllowed(false);
		percentageDesc.setReadOnly(false);
		percentageDesc.setZeroAllowed(false);	
		percentageDesc.setPackageName(Bean1.class.getPackage().getName());
		percentageDesc.setClassName(Bean1.class.getSimpleName());
		return percentageDesc;
	}
	 
	/**
	 * Description for field amount.
	 * @return Description for the field.
	 */
	public BigDecimalBoPropertyDescriptor amountDescriptor() {
		BigDecimalBoPropertyDescriptor amountDesc = new BigDecimalBoPropertyDescriptor();
		amountDesc.setName("amount"); //$NON-NLS-1$
		amountDesc.setLengthOfIntegerPart(3);		
		amountDesc.setLengthOfDecimalPart(6);
		amountDesc.setNullAllowed(false);
		amountDesc.setNegativeAllowed(false);
		amountDesc.setReadOnly(false);
		amountDesc.setZeroAllowed(false);
		amountDesc.setPackageName(Bean1.class.getPackage().getName());
		amountDesc.setClassName(Bean1.class.getSimpleName());

		return amountDesc;
	}
	
	
	
	/**
	 * Description for field renewalDate.
	 * @return Description for the field.
	 */	
	public DateBoPropertyDescriptor renewalDateDesccription() {
		DateBoPropertyDescriptor renewalDateDesc = new DateBoPropertyDescriptor();
		renewalDateDesc.setName("renewalDate"); //$NON-NLS-1$
		renewalDateDesc.setDefaultValue(DateUtils.getDate(2011, Calendar.NOVEMBER, 25));
		renewalDateDesc.setHasDefault(true);
		renewalDateDesc.setNullAllowed(false);
		renewalDateDesc.setReadOnly(true);
		renewalDateDesc.setPackageName(Bean1.class.getPackage().getName());
		renewalDateDesc.setClassName(Bean1.class.getSimpleName());

		return renewalDateDesc;
	}
	
	/**
	 * Description for field issueDate.
	 * @return Description for the field.
	 */
	public DateBoPropertyDescriptor issueDateDesccription() {
		DateBoPropertyDescriptor issueDateDesc = new DateBoPropertyDescriptor();
		issueDateDesc.setName("issueDate"); //$NON-NLS-1$
		issueDateDesc.setPackageName(Bean1.class.getPackage().getName());
		issueDateDesc.setClassName(Bean1.class.getSimpleName());
		issueDateDesc.setNullAllowed(false);
		return issueDateDesc;
		
	}
	

	/**
	 * Description for field issueDate.
	 * @return Description for the field.
	 */
	public BooleanBoPropertyDescriptor booleanDescriptor() {
		BooleanBoPropertyDescriptor booleanDesc = new BooleanBoPropertyDescriptor();
		booleanDesc.setName("checkField"); //$NON-NLS-1$
		booleanDesc.setPackageName(Bean1.class.getPackage().getName());
		booleanDesc.setClassName(Bean1.class.getSimpleName());
		booleanDesc.setNullAllowed(false);
		return booleanDesc;
		
	}

	
	/**
	 * Description for field amount.
	 * @return Description for the field.
	 */
	public FloatBoPropertyDescriptor floatDescriptor() {
		FloatBoPropertyDescriptor floatDesc = new FloatBoPropertyDescriptor();
		floatDesc.setName("floatField"); //$NON-NLS-1$
		floatDesc.setLengthOfIntegerPart(3);		
		floatDesc.setLengthOfDecimalPart(6);
		floatDesc.setNullAllowed(false);
		floatDesc.setNegativeAllowed(false);
		floatDesc.setReadOnly(false);
		floatDesc.setZeroAllowed(false);
		floatDesc.setPackageName(Bean1.class.getPackage().getName());
		floatDesc.setClassName(Bean1.class.getSimpleName());

		return floatDesc;
	}
	
	/**
	 * Description for field amount.
	 * @return Description for the field.
	 */
	public IntegerBoPropertyDescriptor integerDescriptor() {
		IntegerBoPropertyDescriptor integerDesc = new IntegerBoPropertyDescriptor();
		integerDesc.setName("integerField"); //$NON-NLS-1$
		integerDesc.setLengthOfIntegerPart(3);		
		integerDesc.setLengthOfDecimalPart(0);
		integerDesc.setNullAllowed(false);
		integerDesc.setNegativeAllowed(false);
		integerDesc.setReadOnly(false);
		integerDesc.setZeroAllowed(false);
		integerDesc.setPackageName(Bean1.class.getPackage().getName());
		integerDesc.setClassName(Bean1.class.getSimpleName());

		return integerDesc;
	}
	/**
	 * Description for field id as a Selection field.
	 * 
	 * @return SelectionBoPropertyDescriptor<Long>
	 */
	public SelectionBoPropertyDescriptor<Long> selectionDescriptor(){
		SelectionBoPropertyDescriptor<Long> selectionDesc = new SelectionBoPropertyDescriptor<Long>();
			selectionDesc.setName("id"); //$NON-NLS-1$
			selectionDesc.setPackageName(Bean1.class.getPackage().getName());
			selectionDesc.setClassName(Bean1.class.getSimpleName());
			selectionDesc.setNullAllowed(false);
			selectionDesc.setReadOnly(false);
			
			Set<Long> valuesSet = new HashSet<Long>();
			valuesSet.add(new Long(1));
			valuesSet.add(new Long(2));
			valuesSet.add(new Long(3));
			selectionDesc.setValues(valuesSet);			
			return selectionDesc;
	}
		
}
