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

import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;
import gr.interamerican.wicket.utils.MarkupConstants;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Self-drawn text area component.
 */
public class SelfDrawnTextArea extends TextArea<String>{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * maxLength
     */
    int maxLength;
    
    /**
     * Creates a new SelfDrawnTextArea object.
     *
     * @param id
     * @param descriptor
     */
    public SelfDrawnTextArea(String id, StringBoPropertyDescriptor descriptor) {
        super(id, new Model<String>());
        this.maxLength = descriptor.getMaxLength();
        SelfDrawnUtils.<String>standardSelfDrawnFormComponentStuff(this, descriptor);
    }
      
    /**
     * Creates a new SelfDrawnTextArea object.
     *
     * @param id
     * @param descriptor
     * @param model
     */
    public SelfDrawnTextArea(String id, IModel<String> model, StringBoPropertyDescriptor descriptor) {
        super(id, model);
        this.maxLength = descriptor.getMaxLength();
        SelfDrawnUtils.<String>standardSelfDrawnFormComponentStuff(this, descriptor);
    }

    /**
     * Defines default columns and rows size.
     */
    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName(MarkupConstants.TEXTAREA);
        tag.put(MarkupConstants.COLS, MarkupConstants.COLS_VALUE);
        int rowsValue = Math.round(maxLength/MarkupConstants.COLS_VALUE);
        rowsValue = (rowsValue>MarkupConstants.MAX_ROWS ? MarkupConstants.MAX_ROWS : rowsValue);
        tag.put(MarkupConstants.ROWS, rowsValue);
        super.onComponentTag(tag);
    }
    
    @Override
    protected void onDisabled(ComponentTag tag) {
    	tag.put(MarkupConstants.READONLY, MarkupConstants.READONLY);
    }
   
}
