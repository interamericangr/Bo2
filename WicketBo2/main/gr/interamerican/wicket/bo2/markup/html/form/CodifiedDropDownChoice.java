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

import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.ext.Selectable;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.attributes.Named;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

/**
 * A {@link DropDownChoice} based on many {@link Selectable} objects (that are
 * also {@link Codified} and {@link Named}). The model object type is the type
 * of code of the {@link Selectable} object and <b>NOT</b> the
 * {@link Selectable} itself.<br>
 * The elements on the drop down are displayed according to the order of their
 * names (and their code as a second order criteria). Also a choice renderer is
 * used that displays their names instead of their codes.
 * 
 * @param <C>
 *            Type of code
 * @param <P>
 *            The model object type
 */
public class CodifiedDropDownChoice<C extends Comparable<? super C>, P extends Selectable<C>> extends DropDownChoice<C> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2136L;
	/**
	 * Map used by the {@link InnerChoiceRenderer} that has the display name for
	 * each element of this {@link DropDownChoice}
	 */
	private Map<C, String> valuesMapping;

	/**
	 * Creates a new CodifiedDropDownChoice object.
	 * 
	 * @param id
	 *            Wicket Id of the DDC
	 * @param values
	 *            The Values of the {@link Selectable}'s this DDC will contain
	 * @param clz
	 *            Class of {@link Selectable} objects
	 */
	public CodifiedDropDownChoice(String id, Collection<P> values, Class<P> clz) {
		super(id);
		@SuppressWarnings("nls")
		List<P> sortedValues = CollectionUtils.sort(values, clz, "name", "code");
		valuesMapping = new HashMap<C, String>();
		List<C> list = new ArrayList<C>();
		for (P singleValue : sortedValues) {
			valuesMapping.put(singleValue.getCode(), singleValue.getName());
			list.add(singleValue.getCode());
		}
		setChoices(list);
		setChoiceRenderer(new InnerChoiceRenderer());
	}

	/**
	 * A choice rendered that shows the Display value according to the
	 * {@link CodifiedDropDownChoice#valuesMapping}.
	 */
	class InnerChoiceRenderer implements IChoiceRenderer<C> {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 4687L;

		@Override
		public Object getDisplayValue(C object) {
			return valuesMapping.get(object);
		}

		@Override
		public String getIdValue(C object, int index) {
			return Integer.toString(index);
		}
	}
}