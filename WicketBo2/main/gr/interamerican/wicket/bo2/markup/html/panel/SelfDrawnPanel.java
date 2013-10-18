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
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.wicket.bo2.factories.meta.GenericBoPDComponentFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * A {@link Panel} that is able to draw its own components.
 * The components are drawn inside a {@link RepeatingView}.
 * <br/>
 * Each component may optionally contain a label to indicate
 * what it is.
 * <br/>
 * This Panel requires a {@link CompoundPropertyModel}. If the
 * model object of this model changes, the chage is propagated
 * to the contained components.
 * 
 * @param <T>
 *        Type of model object.
 */
public class SelfDrawnPanel<T extends Serializable> extends Panel {
	
    /**
     *serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Wicket id of Panel's label.
     * TODO: is this necessary?
     */
    private static final String TITLE_LABEL_WICKET_ID = "titleLabel"; //$NON-NLS-1$
    
    /**
     * Wicket id of label.
     */
    private static final String LABEL_WICKET_ID="label"; //$NON-NLS-1$
    
    /**
     * Wicket id of component.
     */
    private static final String COMPONENT_WICKET_ID="component"; //$NON-NLS-1$
   
    /**
     * Wicket id for the repeater.
     */
    private static final String REPEATER_WICKET_ID = "repeater"; //$NON-NLS-1$
   
    /**
     * Name of index property of {@link BoPropertyDescriptor}.
     * This is used to sort the components within the panel.
     */
    private static final String INDEX_PROPERTY_NAME = "index"; //$NON-NLS-1$

    /**
     * Creates an empty invisible label.
     *
     * @param id Id of the label.
     *
     * @return Returns an empty invisible label.
     */
    private Label empty(String id) {
        Label l = new Label(id);
        l.setVisible(false);
        return l;
    }

    /**
     * Creates a new SelfDrawnPanelTest object.
     *
     * @param id
     * @param tModel
     * @param businessObjectDescriptor
     */
    public SelfDrawnPanel(String id, CompoundPropertyModel<T> tModel, BusinessObjectDescriptor<T> businessObjectDescriptor) {
        super(id, tModel);
        setOutputMarkupPlaceholderTag(true);
        String labelStr = businessObjectDescriptor.getLabel();
        Label label = new Label(TITLE_LABEL_WICKET_ID,labelStr);
        this.add(label);
        if(StringUtils.isNullOrBlank(labelStr)) {
            label.setVisible(false);
        }
        RepeatingView rows = new RepeatingView(REPEATER_WICKET_ID);
       
        List<BoPropertyDescriptor<?>> specs = businessObjectDescriptor.getPropertyDescriptors();   
        @SuppressWarnings("rawtypes")
        List<BoPropertyDescriptor> specsRaw = new ArrayList<BoPropertyDescriptor>(specs);
        specsRaw = CollectionUtils.sort(specsRaw, BoPropertyDescriptor.class, INDEX_PROPERTY_NAME);
       
        for (BoPropertyDescriptor<?> spec : specsRaw) {
             Pair<Component, Component> pair = GenericBoPDComponentFactory.INSTANCE.draw(
            		 tModel.bind(spec.getName()), spec, COMPONENT_WICKET_ID, LABEL_WICKET_ID);
             
            if (pair.getLeft()==null) {
                pair.setLeft(empty(LABEL_WICKET_ID)); 
            }
            if (pair.getRight()==null) {
                pair.setRight(empty(COMPONENT_WICKET_ID)); 
            }
            WebMarkupContainer wmc = new WebMarkupContainer(spec.getName());
            wmc.add(pair.getLeft());
            wmc.add(pair.getRight());

            rows.add(wmc);
        }       
        this.add(rows);
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
