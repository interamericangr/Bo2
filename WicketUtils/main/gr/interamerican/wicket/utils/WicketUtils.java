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
package gr.interamerican.wicket.utils;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.util.Map;
import java.util.Properties;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public  class WicketUtils {
	
	/**
	 * Logger
	 */
	static final Logger LOGGER = LoggerFactory.getLogger(WicketUtils.class.getName());
	
	/**
	 * hidden constructor. 
	 * 
	 */
	private WicketUtils() {
		/* empty */
	}
	
	/**
	 * Renders the specific fields.
	 * Δίνουμε ως όρισμα ένα Array απο field ids και τον Container που τα περιέχει 
	 * και η μέθοδος τα κάνει Render.
	 * 
	 * @param target 
	 * @param container 
	 * @param fieldsToRender 
	 * 
	 */
	public static void renderFields(AjaxRequestTarget target,WebMarkupContainer container, String[] fieldsToRender) {
		for (String s :fieldsToRender){
			container.get(s).setOutputMarkupPlaceholderTag(true);
			target.add(container.get(s));
		}
	}
	
	/**
	 * Controls the Visibility of  the specific fields.
	 * Δίνουμε ως όρισμα ένα Array απο field ids , τον Container που τα περιέχει 
	 * και το visibility (True for visible and False for invisible)
	 * και η μεθοδος καθορίζει το Visibility για το καθένα.
	 * 
	 * @param container 
	 * @param fields 
	 * @param visibility 
	 */
	public static void setVisibility(WebMarkupContainer container, String[] fields, Boolean visibility){
		for(String field : fields){
			container.get(field).setVisible(visibility);
		}
	}
	/**
	 * Initializes the FeedBackPanel.
	 * 
	 * @return feedbackPanel 
	 */
	public static FeedbackPanel createFeedbackPanel() {
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel"); //$NON-NLS-1$
		feedbackPanel.setOutputMarkupPlaceholderTag(true);		
		return feedbackPanel;
	}
	
	/**
	 * Validates that a form component is not null.
	 * 
	 * An error resource key that consists of error.formComponentId 
	 * must be defined relevant to the owner component.
	 * 
	 * @param owner
	 *        Owner component that does the validation and the resource key
	 *        is defined relevant to it.
	 * @param formComponent
	 *        For component being validated.
	 */
	public static void validateNotNull (Component owner, FormComponent<?> formComponent) {
		if (formComponent.getModelObject()==null) {
			String resourceKey = "error." + formComponent.getId(); //$NON-NLS-1$
			error(owner,resourceKey);
		}
	}
	
	/**
	 * Validates that a form component is not null.
	 *
	 * @param owner
	 *        Owner component that does the validation and the resource key
	 *        is defined relevant to it.
	 * @param resourceKey
	 *        Resource key for the resource message. The resource key must be
	 *        relevant to the owner component.
	 * @param formComponents 
	 *        Form components being validated.
	 */
	public static void validateNotNull (Component owner, String resourceKey, FormComponent<?>... formComponents) {
		for (FormComponent<?> formComponent : formComponents) {
			if (formComponent.getModelObject()==null) {
				error(owner,resourceKey);
			}
		}
	}	
	
	/**
	 * Triggers error on a component.
	 * 
	 * @param component
	 *        Component on which error is triggered.  
	 * @param resourceKey
	 *        Resource key for the resource message. The resource key must be
	 *        relevant to the component.
	 */
	public static void error (Component component, String resourceKey) {
		StringResourceModel model = new StringResourceModel(resourceKey, component, null);
		String message = model.toString();
		component.error(message);		
	}
	
	/**
	 * Returns a wicket path given an ordered array of wicket ids, e.g.:
	 * {"a", "bc", "def"} --> "a:bc:def"
	 * 
	 * @param componentIds
	 * @return the wicket path
	 */
	public static String wicketPath(String...componentIds) {
		StringBuffer sb = new StringBuffer();
		for (String id : componentIds) {
			if(!StringUtils.isNullOrBlank(id)) {
				sb.append(id + StringConstants.COLON);
			}
		}
		sb.setLength(sb.length()-1);
		return sb.toString();
	}
	
	/**
	 * Sets the enabled property on a number of wicket components.
	 * 
	 * @param enabled
	 * @param components
	 */
	public static void setEnabled(boolean enabled, Component...components) {
		for(Component cmp : components) {
			if(cmp!=null) {
				cmp.setEnabled(enabled);
			}
		}
	}
	
	/**
	 * Sets the visible property on a number of wicket components.
	 * 
	 * @param visible
	 * @param components
	 */
	public static void setVisible(boolean visible, Component...components) {
		for(Component cmp : components) {
			if(cmp!=null) {
				cmp.setVisible(visible);
			}
		}
	}
	
	/**
	 * Sets the required  property on a number of form components.
	 * 
	 * @param container 
	 * @param fields 
	 * @param required 
	 */
	public static void setRequired(WebMarkupContainer container, String[] fields, Boolean required) {
		for(String field : fields){
			FormComponent<?> cmp = (FormComponent<?>) container.get(field);
			cmp.setRequired(required);
		}
	}
	
	/**
	 * Δινουμε ως όρισμα ένα αντικείμενο και μας Επιστρέφει 
	 * ένα CompoundPropertyModel για το Αντικείμενο Αυτο.
	 * Σε περίπτωση που το Αντικείμενο είναι null επιστρέφει null.
	 * @param <T> 
	 * 
	 * 
	 * @param obj 
	 * @return cpm
	 */
	public static <T> CompoundPropertyModel<T>  returnModel(T obj){
		CompoundPropertyModel<T> cpm = null;
		if(obj!= null){
			cpm = new CompoundPropertyModel<T>(obj);
		}
		return cpm;
	}
	
	/**
	 * Method that enables Required Validators.
	 * @param container  - the component that contains the required fields
	 * @param mandatoryFields - the required fields 
	 * 
	 */
	public static void enableRequiredValidators(MarkupContainer container, String[] mandatoryFields){
		for(String mandatoryField: mandatoryFields){
			((FormComponent<?>)container.get(mandatoryField)).setRequired(true);
		}
	}
	
	/**
	 * Method that disables Required Validators.
	 * @param container - the component that contains the required fields
	 * @param mandatoryFields - the required fields 
	 * 
	 */
	public static void disableRequiredValidators(MarkupContainer container, String[] mandatoryFields){
		for(String mandatoryField: mandatoryFields){
			((FormComponent<?>) container.get(mandatoryField)).setRequired(false);
		}
	}
	
	/**
	 * Method that disables Components.
	 * @param container - the component that contains the required fields
	 * @param components - the wicketids of the components we want to disable
	 * 
	 */
	public static void disableComponents(MarkupContainer container, String[]... components){
		for(String[] set: components){
			disableComponents(container, set);
		}
	}
	
	/**
	 * Method that disables Components.
	 * @param container - the component that contains the required fields
	 * @param components - the wicketids of the components we want to disable
	 * 
	 */
	public static void disableComponents(MarkupContainer container, String[] components){
		for(String cmp: components){
			FormComponent<?> formComponent = ((FormComponent<?>) container.get(cmp));
			disableComponent(formComponent);
		}
	}
	
	/**
	 * Some FormComponent types need special treatment when disabling.
	 * The logic for these cases can be added here. 
	 * 
	 * @param <T>
	 *        Type of model object.
	 * @param component
	 *        FormComponent to disable.
	 */
	public static <T> void disableComponent(FormComponent<T> component) {
		if(component instanceof TextArea) {
			AttributeModifier mod = new AttributeModifier(MarkupConstants.READONLY, new Model<String>(MarkupConstants.READONLY));
			component.add(mod);
		} else {
			component.setEnabled(false);
		}
	}
	
	/**
	 * Mounts a number of bookmarkable wicket pages, from a resource properties file.
	 * <br/>
	 * If a Page class is not found, an ERROR is logged to facilitate continuous integration
	 * test execution.
	 * 
	 * @param fPath
	 *        Resource path of properties file.
	 * @param webApplication
	 *        {@link WebApplication} instance.
	 */
	@SuppressWarnings({ "unchecked", "nls" })
	public static void mountBookmarkablePagesFromFile(String fPath, WebApplication webApplication) {
		Properties p = CollectionUtils.readProperties(fPath);
		Map<String, String> properties = Utils.cast(p);
		for(Map.Entry<String, String> entry : properties.entrySet()) {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(entry.getValue());
			} catch (ClassNotFoundException e) {
				String msg = StringUtils.concat(
						"Non existant bookmarkable page class: ",
						entry.getValue().toString(),
						". This is acceptable for unit tests, but FATAL in every other case and should be investigated.");
				LOGGER.error(msg);
				continue;
			}
			if(!Page.class.isAssignableFrom(clazz)) {
				throw new RuntimeException(entry.getValue() + " should extend " + Page.class.getName()); //$NON-NLS-1$
			}
			webApplication.mountPage(entry.getKey(), (Class<Page>) clazz);
		}
	}
	
	/**
	 * Sets the default model object without checking the new model
	 * object equality to the old one.
	 * @param cmp
	 * @param t
	 */
	public static <T> void setDefaultModelObject(Component cmp, T t) {
		cmp.modelChanging();
		@SuppressWarnings("unchecked")
		IModel<T> model = (IModel<T>) cmp.getDefaultModel();
		model.setObject(t);
		cmp.modelChanged();
	}
	
}
