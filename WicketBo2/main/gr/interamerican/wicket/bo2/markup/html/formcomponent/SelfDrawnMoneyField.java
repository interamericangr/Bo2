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
package gr.interamerican.wicket.bo2.markup.html.formcomponent;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.meta.descriptors.BigDecimalBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.PropertyDefinition;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MoneyBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.factories.BigDecimalBoPDFactory;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnBigDecimalTextField;
import gr.interamerican.wicket.utils.MarkupConstants;

import java.math.BigDecimal;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * A self drawn form component that binds to a {@link Money} instance.
 * This works by adding to itself a {@link SelfDrawnBigDecimalTextField}
 * that binds the <code>amount</code> BigDecimal property of {@link Money}. 
 * <br/>
 * The {@link MoneyBoPropertyDescriptor} meta-data are applied to the
 * nested text field.
 */
public class SelfDrawnMoneyField extends FormComponentPanel<Money>{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Wicket id of the amount field. 	
	 */
	public static final String AMOUNT_FIELD_ID = "component"; //$NON-NLS-1$
	
	/**
	 * Field that holds the amount.
	 */
	private SelfDrawnBigDecimalTextField amountField;

	/**
	 * Creates a new SelfDrawnMoneyField object. 
	 *
	 * @param id
	 * @param descriptor 
	 *        MoneyBoPropertyDescriptor.
	 */
	public SelfDrawnMoneyField(String id, MoneyBoPropertyDescriptor descriptor) {
		super(id);
		this.setOutputMarkupPlaceholderTag(true);
		IModel<Money> model = new Model<Money>(new MoneyImpl());
		this.setDefaultModel(model);
		amountField = createAmountField(AMOUNT_FIELD_ID, descriptor, model);
		add(amountField);
	}

	/**
	 * Creates a new SelfDrawnMoneyField object. 
	 *
	 * @param id
	 * @param descriptor 
	 *        MoneyBoPropertyDescriptor.
	 * @param model
	 *        The Model of type {@link Money}. 
	 */
	public SelfDrawnMoneyField(String id, IModel<Money> model, MoneyBoPropertyDescriptor descriptor) {
		super(id, model);
		this.setOutputMarkupPlaceholderTag(true);
		amountField = createAmountField(AMOUNT_FIELD_ID, descriptor, model);
		add(amountField);
	}

	/**
	 * Creates the field showing the amount.
	 * 
	 * @param id
	 * @param descriptor
	 * @param model
	 * @return The amount field.
	 */
	private SelfDrawnBigDecimalTextField createAmountField(String id, MoneyBoPropertyDescriptor descriptor, IModel<Money> model) {
		BigDecimalBoPropertyDescriptor amountDescriptor = createAmountDescriptor(descriptor);
		if(model.getObject()==null) {
			model.setObject(new MoneyImpl());
		}
		
		PropertyModel<BigDecimal> amountModel = new PropertyModel<BigDecimal>(model, "amount"); //$NON-NLS-1$
		return new SelfDrawnBigDecimalTextField(id, amountModel, amountDescriptor);
	}
	
	@Override
    protected void onComponentTag(ComponentTag tag) {  
		tag.setName(MarkupConstants.DIV);
		super.onComponentTag(tag);
	}
	
	@Override
	protected void convertInput() {
		/*
		 * If the amountField is disabled, do not take it into consideration at all.
		 */
		if(!amountField.isEnabled()) {
			setConvertedInput(getModel().getObject());
			return;
		}
		
		BigDecimal amount = amountField.getConvertedInput();
		Money money = getModel().getObject();
		money.setAmount(amount);
		setConvertedInput(money);
	}
	
	/**
	 * Creates a {@link BigDecimalBoPropertyDescriptor} for the property <code>amount</code> 
	 * of {@link Money} given the {@link MoneyBoPropertyDescriptor}.
	 * 
	 * @param descriptor
	 *        {@link MoneyBoPropertyDescriptor}.
	 *        
	 * @return a {@link BigDecimalBoPropertyDescriptor}
	 */
	private BigDecimalBoPropertyDescriptor createAmountDescriptor
	(MoneyBoPropertyDescriptor descriptor){
		PropertyDefinition definition = Factory.create(PropertyDefinition.class);
		definition.setIntegerLength(descriptor.getLengthOfIntegerPart());
		definition.setDecimalLength(descriptor.getLengthOfDecimalPart());
		definition.setNullAllowed(descriptor.isNullAllowed());
		definition.setNegativeAllowed(descriptor.isNegativeAllowed());
		definition.setReadOnly(descriptor.isReadOnly());
		definition.setZeroAllowed(descriptor.isZeroAllowed());
		definition.setName("amount"); //$NON-NLS-1$
		definition.setHasDefault(descriptor.isHasDefault());
		String defaultValue = descriptor.isHasDefault() ? String.valueOf(descriptor.getDefaultValue().getAmount()) : null;
		definition.setDefaultValue(defaultValue);
		
		BigDecimalBoPropertyDescriptor amountDesc;		
		amountDesc = BigDecimalBoPDFactory.create(definition);	
		
		amountDesc.setPackageName(Money.class.getPackage().getName());
		amountDesc.setClassName(Money.class.getSimpleName());
		amountDesc.setLabel(descriptor.getLabel());
		amountDesc.setIndex(descriptor.getIndex());
		
		return amountDesc;
	}
	
	/**
	 * Gets the amountTextField.
	 *
	 * @deprecated REMOVE THIS.
	 *
	 * @return Returns the amountTextField
	 */
	@Deprecated
	public SelfDrawnBigDecimalTextField getAmountTextField() {
		return amountField;
	}

}
