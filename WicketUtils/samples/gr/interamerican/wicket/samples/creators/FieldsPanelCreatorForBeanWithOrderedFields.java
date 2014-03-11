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
package gr.interamerican.wicket.samples.creators;

import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;
import gr.interamerican.wicket.samples.panels.BeanWithOrderedFieldsFormPanel;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * Creator of a panel with the fields of a {@link BeanWithOrderedFields}.
 */
public class FieldsPanelCreatorForBeanWithOrderedFields 
implements PanelCreator<BeanWithOrderedFields> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Panel createPanel(ModeAwareBeanPanelDef<BeanWithOrderedFields> definition) {
		return new BeanWithOrderedFieldsFormPanel(definition.getWicketId(), definition.getBeanModel());
	}

}
