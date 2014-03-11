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

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptorOwner;
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
 * 
 * @param <B> type of Bean
 * @param <C> type of Cache code
 * 
 * @see CacheRegistry
 * @see Cache
 * @see BusinessObjectDescriptor
 */
public class SelfDrawnPanelCreatorWithCachedBod
<B extends Serializable, 
 C extends Comparable<? super C>> 
implements PanelCreator<B> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of cache that holds the BusinessObjectDescriptor
	 */
	String cacheName;
	
	/**
	 * BusinessObjectDescriptor entries typeId
	 */
	Long descriptorTypeId;
	
	/**
	 * BusinessObjectDescriptor code
	 */
	C descriptorCode;
	
	/**
	 * Number of columns.
	 */
	private int columns = 1; //DEFAULT 1
	
	/**
	 * Fixture for a drop down. Key is property name,
	 * value is subListCd for the {@link CachedEntryBoPropertyDescriptor}
	 * that corresponds to the name.
	 */
	private Map<String, Long> dropDownFix;

	/**
	 * Creates a new SelfDrawnPanelCreator object.
	 * 
	 * @param cacheName 
	 * @param descriptorTypeId 
	 * @param descriptorCode 
	 */
	public SelfDrawnPanelCreatorWithCachedBod(String cacheName, Long descriptorTypeId, C descriptorCode) {
		this(cacheName, descriptorTypeId, descriptorCode, 1, null);
	}

	/**
	 * Creates a new SelfDrawnPanelCreator object.
	 * 
	 * @param cacheName 
	 * @param descriptorTypeId 
	 * @param descriptorCode 
	 * @param columns 
	 */
	public SelfDrawnPanelCreatorWithCachedBod(String cacheName, Long descriptorTypeId, C descriptorCode, int columns) {
		this(cacheName, descriptorTypeId, descriptorCode, columns, null);
	}
	
	/**
	 * Creates a new SelfDrawnPanelCreator object.
	 * @param cacheName 
	 * @param descriptorTypeId 
	 * @param descriptorCode 
	 * 
	 * @param columns 
	 * @param dropDownFix 
	 */
	public SelfDrawnPanelCreatorWithCachedBod(String cacheName, Long descriptorTypeId, C descriptorCode, int columns, Map<String, Long> dropDownFix) {
		super();
		this.cacheName = cacheName;
		this.descriptorTypeId = descriptorTypeId;
		this.descriptorCode = descriptorCode;
		this.columns = columns;
		this.dropDownFix = dropDownFix;
	}

	public Panel createPanel(ModeAwareBeanPanelDef<B> definition) {
		if (!(definition.getBeanModel() instanceof CompoundPropertyModel)) {
			throw new RuntimeException("The bean model of ModeAwareBeanPanelDef is not a CompoundPropertyModel."); //$NON-NLS-1$
		}
		String id = definition.getWicketId();
		CompoundPropertyModel<B> model = (CompoundPropertyModel<B>) definition.getBeanModel();
		return new SelfDrawnPanel<B>(id, model, beanDescriptor(), columns, dropDownFix);
	}
	
	/**
	 * @return Gets a cached BusinessObjectDescriptor
	 */
	@SuppressWarnings("unchecked")
	BusinessObjectDescriptor<B> beanDescriptor() {
		BusinessObjectDescriptorOwner bodOwner = (BusinessObjectDescriptorOwner) CacheRegistry.<C>getRegisteredCache(cacheName).get(descriptorTypeId, descriptorCode);
		return (BusinessObjectDescriptor<B>) bodOwner.getBusinessObjectDescriptor();
	}

}
