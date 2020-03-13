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
package gr.interamerican.wicket.bo2.markup.html.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import gr.interamerican.bo2.utils.meta.descriptors.StringSelectionsBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.utils.SelfDrawnComponentsConfiguration;
import gr.interamerican.wicket.utils.MarkupConstants;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * Self-drawn string selection {@link Palette}.
 */
public class SelfDrawnStringPallete 
extends Palette<String>{
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Style
	 */
	private static final String WIDTH_STYLE ="width:200px"; //$NON-NLS-1$
	
	/**
	 * Number of choices to be visible on the screen with out scrolling.
	 */
	private static final int rows = 10;
	
	/**
	 * Allow user to move selections up and down.
	 */
	private static boolean allowOrder = false;
	
	/**
	 * Creates a new SelfDrawnPalleteForEntries object. 
	 * 
	 * @param id 
	 * @param descriptor  
	 * @param model 
	 */
	public SelfDrawnStringPallete(String id, IModel<List<String>> model, StringSelectionsBoPropertyDescriptor descriptor) {
		super(id, model, Model.of(descriptor.getAvailableValues()), new ChoiceRenderer(), rows, allowOrder);
		setOutputMarkupPlaceholderTag(true);
    	setEnabled(!descriptor.isReadOnly());
		add(SelfDrawnComponentsConfiguration.DEFAULT_PALLETE_THEME.get());
	}
	
	/**
	 * Creates a new SelfDrawnPalleteForEntries object.
	 * 
	 * @param id 
	 * @param descriptor 
	 */
	public SelfDrawnStringPallete(String id, StringSelectionsBoPropertyDescriptor descriptor) {
		this(id, new ListModel<String>(new ArrayList<String>()), descriptor);
	}
	
	@Override
    protected void onComponentTag(ComponentTag tag) {  
		tag.setName(MarkupConstants.SPAN);
		tag.put(MarkupConstants.STYLE, WIDTH_STYLE);
		super.onComponentTag(tag);
	}
	
	/**
	 * Simple ChoiceRenderer
	 */
	static class ChoiceRenderer implements IChoiceRenderer<String> {

		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Object getDisplayValue(String object) {
			return object;
		}

		@Override
		public String getIdValue(String object, int index) {
			return object;
		}

		@Override
		public String getObject(String id, IModel<? extends List<? extends String>> choices) {
			return WicketUtils.getObject(this, id, choices);
		}
	}
}