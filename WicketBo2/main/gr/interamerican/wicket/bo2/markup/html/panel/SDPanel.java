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
package gr.interamerican.wicket.bo2.markup.html.panel;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.wicket.bo2.factories.meta.GenericBoPDComponentFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

/**
 * 
 * @param <T>
 */
public class SDPanel<T extends Serializable> extends BaseSelfDrawnPanel<T> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Item panel to be rendered in the grid cell.
	 */
	private static final String GRID_WICKET_ID = "rows"; //$NON-NLS-1$	

	/**
	 * Creates a new SelfDrawnPanelTest object.
	 * 
	 * @param id
	 * @param tModel
	 * @param businessObjectDescriptor
	 * @param cols 
	 */
	@SuppressWarnings("rawtypes")
	public SDPanel(String id, final CompoundPropertyModel<T> tModel,
			BusinessObjectDescriptor<T> businessObjectDescriptor, int cols) {
		super(id, tModel);
		setOutputMarkupPlaceholderTag(true);
		String labelStr = businessObjectDescriptor.getLabel();
		Label label = new Label(TITLE_LABEL_WICKET_ID, labelStr);
		add(label);
		if (StringUtils.isNullOrBlank(labelStr)) {
			label.setVisible(false);
		}

		List<BoPropertyDescriptor<?>> specs = businessObjectDescriptor.getPropertyDescriptors();
		List<BoPropertyDescriptor> specsRaw = new ArrayList<BoPropertyDescriptor>(specs);
		specsRaw = CollectionUtils.sort(specsRaw, BoPropertyDescriptor.class, INDEX_PROPERTY_NAME);

		IDataProvider<BoPropertyDescriptor> dataProvider = new ListDataProvider<BoPropertyDescriptor>(specsRaw);
		GridView<BoPropertyDescriptor> gridView = new GridView<BoPropertyDescriptor>(GRID_WICKET_ID, dataProvider) {

			/**
			 * serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateEmptyItem(Item<BoPropertyDescriptor> item) {				
				item.add(new Label(LABEL_WICKET_ID, new Model<String>(StringConstants.EMPTY)),new EmptyPanel(COMPONENT_WICKET_ID));
			}

			@Override
			protected void populateItem(Item<BoPropertyDescriptor> item) {
				BoPropertyDescriptor<?> spec = item.getModelObject();
				Pair<Component, Component> pair = GenericBoPDComponentFactory.INSTANCE.draw(
						tModel.bind(spec.getName()), spec, COMPONENT_WICKET_ID, LABEL_WICKET_ID);

				if (pair.getLeft() == null) {
					pair.setLeft(empty(LABEL_WICKET_ID));
				}
				if (pair.getRight() == null) {
					pair.setRight(empty(COMPONENT_WICKET_ID));
				}				
				item.add(pair.getLeft(), pair.getRight());				
			}
		};
		gridView.setOutputMarkupId(true);
		gridView.setColumns(cols);		
		add(gridView);
	}
	
	/**
	 * 
	 * Creates a new SDPanel object. 
	 *
	 * @param id
	 * @param tModel
	 * @param businessObjectDescriptor
	 */
	public SDPanel(String id, final CompoundPropertyModel<T> tModel,
			BusinessObjectDescriptor<T> businessObjectDescriptor) {
		this(id, tModel, businessObjectDescriptor, 1);
	}

	/**
	 * Gets the labelId.
	 * 
	 * @return Returns the labelId
	 */
	public static String getLabelId() {
		return LABEL_WICKET_ID;
	}

	/**
	 * Gets the repeaterId.
	 * 
	 * @return Returns the repeaterId
	 */
	public static String getRepeaterId() {
		return REPEATER_WICKET_ID;
	}

	/**
	 * Gets the componentId.
	 * 
	 * @return Returns the componentId
	 */
	public static String getComponentId() {
		return COMPONENT_WICKET_ID;
	}

}
