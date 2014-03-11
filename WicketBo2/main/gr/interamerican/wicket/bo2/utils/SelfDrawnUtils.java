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
package gr.interamerican.wicket.bo2.utils;

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.beans.PairWithComparableLeft;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.wicket.behavior.ValidationStyleBehavior;
import gr.interamerican.wicket.bo2.markup.html.form.ChoiceRendererForEntry;
import gr.interamerican.wicket.bo2.markup.html.form.ChoiceRendererForEntryOwner;
import gr.interamerican.wicket.bo2.markup.html.form.DropDownChoiceForEntry;
import gr.interamerican.wicket.bo2.markup.html.form.DropDownChoiceForEntryOwner;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnForm;
import gr.interamerican.wicket.bo2.markup.html.formcomponent.SelfDrawnMoneyField;
import gr.interamerican.wicket.bo2.markup.html.panel.SelfDrawnPanel;
import gr.interamerican.wicket.bo2.validation.BoPropertyDescriptorValidatorWrapper;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.components.CallbackActionBehavior;
import gr.interamerican.wicket.utils.WicketUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Utilities for self-drawn wicket {@link Component}s.
 * 
 * TODO: Create SelfDrawnPanelUtils to package gr.interamerican.wicket.bo2.markup.html.panel
 */
public class SelfDrawnUtils {
		
	/**
	 * Hidden constructor of utility class.
	 */
	private SelfDrawnUtils() { /* hidden, empty */ }
		
	/**
	 * Returns the component of a SelfDrawnPanel under the specific repeater wicketId.
	 * 
	 * @param selfDrawnPanel 
	 *        Self drawn panel.
	 * 
	 * @param wicketId
	 *        The wicketId of the repeater that has this component.
	 * 
	 * @return selfDrawnComponent
	 *         Component in the repeater that has the specified wicket id.
	 */
	public static Component getComponentFromSelfDrawnPanel(MarkupContainer selfDrawnPanel, String wicketId){
		if(selfDrawnPanel == null){
			return null;
		}
		return selfDrawnPanel.get(wicketId);
	}
	
	/**
	 * Adds a behavior on a self drawn panel component that is executed on the "onchange" javascript event.
	 * 
	 * 
	 * @param selfDrawnPanel
	 *        Self drawn panel.
	 * @param wicketId
	 *        The wicketId of the repeater that has this component.
	 * @param callbackAction 
	 * 		  The {@link CallbackAction} that is executed "onchange".
	 */
	public static void addUpdatingBehavior(MarkupContainer selfDrawnPanel, String wicketId, CallbackAction callbackAction){
		Component component = getComponentFromSelfDrawnPanel(selfDrawnPanel, wicketId);
		component.add(new CallbackActionBehavior("onchange", callbackAction)); //$NON-NLS-1$
	}
	
	/**
	 * Adds a behavior on a set of self drawn panel components that is executed on 
	 * the "onchange" javascript event of each component.
	 * 
	 * @param selfDrawnPanel
	 *        Self drawn panel.
	 * @param wicketIds
	 *        The wicketIds of the repeaters that has the components.
	 * @param callbackAction 
	 * 		  The {@link CallbackAction} that is executed "onchange".
	 */
	public static void addUpdatingBehavior(MarkupContainer selfDrawnPanel, Set<String> wicketIds, CallbackAction callbackAction){
		for(String wicketId : wicketIds) {
			addUpdatingBehavior(selfDrawnPanel, wicketId, callbackAction);
		}
	}
	
	/**
	 * Code called on any self-drawn form component.
	 * 
	 * @param component
	 *        FormComponent
	 * @param descriptor
	 *        BoPropertyDescriptor
	 * 
	 * @param <T>
	 *        Type of component's model object.
	 */
	public static <T> void standardSelfDrawnFormComponentStuff(FormComponent<T> component, BoPropertyDescriptor<T> descriptor) {
		component.setOutputMarkupPlaceholderTag(true);
		component.add(ValidationStyleBehavior.INSTANCE);
		component.add(new BoPropertyDescriptorValidatorWrapper<T>(descriptor));
    	if(descriptor.isHasDefault() && component.getDefaultModelObject()==null){
    		component.setDefaultModelObject(descriptor.getDefaultValue());
        }
    	if(descriptor.isReadOnly()) {
    		disableComponent(component);
    	}
    	/*
    	 * This check cannot be off-loaded to the NotNullValidator, since
    	 * in this case, wicket will not process any validators attached
    	 * on the component. Having to set this here means that state changes
    	 * of the nullAllowed property of the BoPropertyDescriptor are not
    	 * propagated to the self-drawn Component. This is not, however, a
    	 * major issue, since it is not expected that the BoPropertyDescriptor
    	 * of a self-drawn Component will have this property changed after
    	 * the Component is created.
    	 */
    	component.setRequired(!descriptor.isNullAllowed());
	}
	
	/**
	 * Disables a component.
	 * 
	 * @param component
	 */
	static <T> void disableComponent(FormComponent<T> component) {
		if(component instanceof SelfDrawnMoneyField) {
			SelfDrawnMoneyField sdmf = (SelfDrawnMoneyField) component;
			sdmf.get(SelfDrawnMoneyField.AMOUNT_FIELD_ID).setEnabled(false);
		} else {
			WicketUtils.disableComponent(component);
		}
	}
	
	/**
	 * Code called on any self-drawn form component - unchecked version.
	 * 
	 * @param component
	 *        FormComponent
	 * @param descriptor
	 *        BoPropertyDescriptor
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void standardSelfDrawnFormComponentStuffUnchecked(FormComponent component, BoPropertyDescriptor descriptor) {
		standardSelfDrawnFormComponentStuff(component, descriptor);
	}
	
	
	
	/**
	 * Sort the values of {@link DropDownChoice} component that 
	 * extend {@link TranslatableEntry}.
	 * 
	 * @param <L> 
	 * 		  Type of language id.
	 * @param <T>
	 * 		  Type of Values.
	 * 
	 * @param dropDownComponent
	 *		  The DropDownChoice.
	 */
	public static <L,T extends TranslatableEntry<?, ?, L>> void sortCachedEntries(DropDownChoiceForEntry<L,T> dropDownComponent){
		List<? extends T> choices = dropDownComponent.getChoices();
		@SuppressWarnings("unchecked")
		ChoiceRendererForEntry<L, T> choiceRendererForEntry = (ChoiceRendererForEntry<L, T>) dropDownComponent.getChoiceRenderer();
		List<T> sortedValueList = sortPairByLeftValue(choices, choiceRendererForEntry);
		dropDownComponent.setChoices(sortedValueList);
	}

	/**
	 * Sort the values of {@link DropDownChoice} component that 
	 * extend {@link TranslatableEntryOwner}.
	 * @param <L> 
	 * 		  Type of language id.
	 * @param <T>
	 * 		  Type of Values.
	 * @param dropDownComponent
	 * 		  The DropDownChoice. 
	 */
	public static <L, T extends TranslatableEntryOwner<?,?,L>> void sortCachedEntryOwners(DropDownChoiceForEntryOwner<L,T> dropDownComponent){
		List<? extends T> choices = dropDownComponent.getChoices();
		@SuppressWarnings("unchecked")
		ChoiceRendererForEntryOwner<L, T> choiceRendererForEntryOwner = (ChoiceRendererForEntryOwner<L, T>) dropDownComponent.getChoiceRenderer();
		List<T> sortedValueList = sortPairByLeftValue(choices, choiceRendererForEntryOwner);
		dropDownComponent.setChoices(sortedValueList);
	}

	/**
	 * Sort a Collection of {@link Pair}s by Left Value.
	 * Sort a Collection of {@link Pair}s by Left Value.
	 * 
	 * @param <T>
	 * 		 Type of Values.
	 * @param choices
	 * 		 List of choices.
	 * @param choiceRenderer
	 * 		 The ChoiceRenderer that returns the translatedValue.
	 * @return List<T>
	 */
	public static <T> List<T> sortPairByLeftValue(List<? extends T> choices, IChoiceRenderer<T> choiceRenderer) {
		List<PairWithComparableLeft<String, T>> pairList = new ArrayList<PairWithComparableLeft<String, T>>();
		for(T choice : choices){
			Object translatedValue = choiceRenderer.getDisplayValue(choice);
			PairWithComparableLeft<String, T> pair = new PairWithComparableLeft<String, T>(StringUtils.toString(translatedValue), choice);
			pairList.add(pair);
		}
		Collections.sort(pairList);
		
		List<T> sortedValueList = new ArrayList<T>();
		for(PairWithComparableLeft<String, T> pair : pairList){
			sortedValueList.add(pair.getRight());
		}
		return sortedValueList;
	}
		
	/**
	 * Returns the component of the SelfDrawnForm with the specific attributeName.
	 *  
	 * @param attributeName
	 * @param selfDrawnForm
	 * @return selfDrawnComponent
	 */
	public static Component getComponentFromSelfDrawnForm(SelfDrawnForm<?> selfDrawnForm,String attributeName) {
		SelfDrawnPanel<?> selfDrawnInnerPanel = 
			(SelfDrawnPanel<?>) selfDrawnForm.get(SelfDrawnForm.PANEL_WICKET_ID);
		return getComponentFromSelfDrawnPanel(selfDrawnInnerPanel,attributeName);
	}
	
	/**
	 * Returns the label of a SelfDrawnPanel for the specific repeater wicketId.
	 * 
	 * @param selfDrawnPanel 
	 *        Self drawn panel.
	 * 
	 * @param fieldId
	 *        The wicketId of the repeater that has this component.
	 * 
	 * @return Label in the repeater that has the specified wicket id.
	 */
	public static Component getLabelFromSelfDrawnPanel(Panel selfDrawnPanel, String fieldId){
		if(selfDrawnPanel == null){
			return null;
		}
		String labelWicketId = SelfDrawnPanel.labelWicketIdWithPropertyName(fieldId);
		return selfDrawnPanel.get(labelWicketId);
	}
	
}
