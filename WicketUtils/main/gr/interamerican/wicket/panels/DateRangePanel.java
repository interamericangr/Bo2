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
package gr.interamerican.wicket.panels;

import java.util.Date;

import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.Range;
import gr.interamerican.wicket.model.RangeModel;
import gr.interamerican.wicket.validation.NotNullValidator;

/**
 * Panel with a Model Object of {@link Range} of {@link Date}s.<br>
 * This {@link Panel} must be included on a {@link Form}, due to the fact we
 * include a {@link NotNullValidator} for the {@link Date}s (if one date is
 * filled, the other must be filled as well).
 */
public class DateRangePanel extends Panel {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 31312L;

	/**
	 * Field for 'from'
	 */
	private DateField from;

	/**
	 * Field for 'to'
	 */
	private DateField to;

	/**
	 * Public Constructor.
	 * 
	 * @param id
	 *            Wicket Id
	 * @param iModel
	 *            Model Object
	 */
	@SuppressWarnings("nls")
	public DateRangePanel(String id, IModel<Range<Date>> iModel) {
		super(id);
		from = new DateField("from", new RangeModel<Date>(iModel, true));
		from.setLabel(Model.of("From " + StringUtils.firstCapital(id)));
		to = new DateField("to", new RangeModel<Date>(iModel, false));
		to.setLabel(Model.of("To " + StringUtils.firstCapital(id)));
		add(from, to);
	}

	@Override
	protected void onBeforeRender() {
		from.getForm().add(new NotNullValidator(from, to));
		super.onBeforeRender();
	}
}