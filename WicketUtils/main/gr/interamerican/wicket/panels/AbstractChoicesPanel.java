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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * An abstract panel that is backing several values contained in a set.<br>
 * The change is made on the backing set on submit.<br>
 * For each value of the set an entry of a {@link ListView} is generated - with
 * an icon beside each entry that is meant to remove that entry.<br>
 * After that {@link ListView}, there is a Panel that is responsible for adding
 * new entries on the {@link Set}.
 * 
 * @param <T>
 *            Type of Entity
 * @see DropDownChoicesPanel
 * @see TextFieldChoicesPanel
 */
@SuppressWarnings("nls")
public abstract class AbstractChoicesPanel<T extends Serializable> extends FormComponentPanel<Set<T>> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 5321L;
	/**
	 * {@link ListModel} of selectedValues
	 */
	private ListModel<T> selectedValues;

	/**
	 * Public Constructor.
	 * 
	 * @param id
	 *            Wicket Id
	 * @param labelValue
	 *            Label on top of the panel
	 * @param currentValues
	 *            Set to be updated on submit
	 * @param panelProvider
	 *            A {@link BiFunction} to create the Panel that adds a new
	 *            entry. String is the wicket id and {@link ListModel} are the
	 *            currently selected values
	 */
	public AbstractChoicesPanel(String id, String labelValue, IModel<Set<T>> currentValues,
			BiFunction<String, ListModel<T>, Panel> panelProvider) {
		super(id, currentValues);
		setOutputMarkupId(true);
		selectedValues = new ListModel<>(new ArrayList<>(currentValues.getObject()));
		SelectedValuesListView listView = new SelectedValuesListView("selectedValues", selectedValues);
		Label labelField = new Label("label", labelValue);
		add(listView, labelField);
		add(panelProvider.apply("component", selectedValues));
	}
	
	@Override
	public void convertInput() {
		Set<T> currentValues = getModelObject();
		currentValues.addAll(selectedValues.getObject());
		currentValues.retainAll(selectedValues.getObject());
		setConvertedInput(currentValues);
	}

	/**
	 * ListView with the currently selected values.
	 */
	private class SelectedValuesListView extends ListView<T> {

		/**
		 * Version UID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Public Constructor.
		 * 
		 * @param id
		 *            Wicket Id
		 * @param model
		 *            Backing {@link IModel}
		 */
		public SelectedValuesListView(String id, IModel<List<T>> model) {
			super(id, model);
		}

		@Override
		protected void populateItem(ListItem<T> item) {
			final T modelObject = item.getModelObject();
			String displayValue = modelObject.toString();
			if (modelObject instanceof Enum) {
				displayValue = ((Enum<?>) modelObject).name();
			}
			item.add(new Label("displayValue", displayValue));
			AjaxLinkWithImagePanel panel = new AjaxLinkWithImagePanel("removeLink", item.getModel(), this::doRemove,
					EmbeddedImage.DELETE);
			item.add(panel);
		}

		/**
		 * Action to remove this value
		 * 
		 * @param target
		 * @param removed
		 */
		void doRemove(AjaxRequestTarget target, T removed) {
			getModelObject().remove(removed);
			target.add(AbstractChoicesPanel.this);
		}
	}
}