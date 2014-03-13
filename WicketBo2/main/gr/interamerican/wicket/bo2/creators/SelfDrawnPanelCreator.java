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
package gr.interamerican.wicket.bo2.creators;

import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.panel.SelfDrawnPanel;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;

import java.io.Serializable;
import java.util.Map;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * {@link PanelCreator} implementation based on {@link SelfDrawnPanel}
 * Use this for {@link BusinessObjectDescriptor}s created programmatically.
 * For cached BusinessObjectDescriptors see {@link SelfDrawnPanelCreatorWithCachedBod}
 * 
 * @param <B>
 *            type of Bean
 */
public class SelfDrawnPanelCreator<B extends Serializable> implements PanelCreator<B> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * B descriptor.
	 */
	BusinessObjectDescriptor<B> beanDescriptor;
	
	/**
	 * Number of columns.
	 */
	private int columns = 1; //DEFAULT 1
	
	/**
	 * Fixture for a drop down. Key is property name,
	 * value is subListCd for the {@link CachedEntryBoPropertyDescriptor}
	 * that corresponds to the name.
	 */
	private final Map<String, Long> dropDownFix;

	/**
	 * Creates a new SelfDrawnPanelCreator object.
	 * 
	 * @param beanDescriptor
	 */
	public SelfDrawnPanelCreator(BusinessObjectDescriptor<B> beanDescriptor) {
		super();
		this.beanDescriptor = beanDescriptor;
		this.dropDownFix = null;
	}

	/**
	 * Creates a new SelfDrawnPanelCreator object.
	 * 
	 * @param beanDescriptor
	 * @param columns 
	 */
	public SelfDrawnPanelCreator(BusinessObjectDescriptor<B> beanDescriptor, int columns) {
		super();
		this.beanDescriptor = beanDescriptor;
		this.columns = columns;
		this.dropDownFix = null;
	}
	
	/**
	 * Creates a new SelfDrawnPanelCreator object.
	 * 
	 * @param beanDescriptor
	 * @param columns 
	 * @param dropDownFix 
	 */
	public SelfDrawnPanelCreator(BusinessObjectDescriptor<B> beanDescriptor, int columns, Map<String, Long> dropDownFix) {
		super();
		this.beanDescriptor = beanDescriptor;
		this.columns = columns;
		this.dropDownFix = dropDownFix;
	}

	public Panel createPanel(ModeAwareBeanPanelDef<B> definition) {
		if (!(definition.getBeanModel() instanceof CompoundPropertyModel)) {
			throw new RuntimeException("The bean model of ModeAwareBeanPanelDef is not a CompoundPropertyModel."); //$NON-NLS-1$
		}
		String id = definition.getWicketId();
		CompoundPropertyModel<B> model = (CompoundPropertyModel<B>) definition.getBeanModel();
		return new SelfDrawnPanel<B>(id, model, beanDescriptor, columns, dropDownFix);
	}

}
