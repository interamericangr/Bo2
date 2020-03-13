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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.functions.SerializableFunction;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * A {@link DropDownChoice} based on many objects that provide with the input
 * {@link SerializableFunction}s the actual objects of the
 * {@link DropDownChoice} and the displayed text for each object.<br>
 * The elements on the drop down are displayed according to the order of their
 * displayed text.
 * 
 * @param <C>
 *            The model object type of the {@link DropDownChoice}
 * @param <P>
 *            The Objects we apply the functions upon
 */
public class FunctionBasedDropDownChoice<C, P> extends DropDownChoice<C> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2136L;

	/**
	 * Map used by the {@link InnerChoiceRenderer} that has the display name for
	 * each element of this {@link DropDownChoice}.
	 */
	private final Map<C, String> valuesMapping = new HashMap<>();

	/**
	 * {@link SerializableFunction} to provide the model object
	 */
	private final SerializableFunction<P, C> objectFunction;

	/**
	 * {@link SerializableFunction} to provide the displayed name
	 */
	private final SerializableFunction<P, String> nameFunction;

	/**
	 * Public Constructor.
	 * 
	 * @param id
	 *            Wicket Id
	 * @param objectFunction
	 *            {@link SerializableFunction} to provide the model object
	 * @param nameFunction
	 *            {@link SerializableFunction} to provide the displayed name
	 */
	public FunctionBasedDropDownChoice(String id, SerializableFunction<P, C> objectFunction,
			SerializableFunction<P, String> nameFunction) {
		super(id);
		this.objectFunction = objectFunction;
		this.nameFunction = nameFunction;
		setChoiceRenderer(new InnerChoiceRenderer());
	}

	/**
	 * Public Constructor.
	 * 
	 * @param id
	 *            Wicket Id
	 * @param objectFunction
	 *            {@link SerializableFunction} to provide the model object
	 * @param nameFunction
	 *            {@link SerializableFunction} to provide the displayed name
	 * @param values
	 *            The Values of the 's this DDC will contain
	 */
	public FunctionBasedDropDownChoice(String id, SerializableFunction<P, C> objectFunction,
			SerializableFunction<P, String> nameFunction, Collection<P> values) {
		this(id, objectFunction, nameFunction);
		setChoices(values);
	}

	/**
	 * Sets the Choices of the {@link FunctionBasedDropDownChoice}.
	 *
	 * @param values
	 *            the new choices
	 */
	public void setChoices(Collection<P> values) {
		List<P> sortedValues = CollectionUtils.sort(values, nameFunction);
		List<C> list = new ArrayList<C>();
		for (P singleValue : sortedValues) {
			C code = objectFunction.apply(singleValue);
			valuesMapping.put(code, nameFunction.apply(singleValue));
			list.add(code);
		}
		setChoices(list);
	}

	/**
	 * A choice rendered that shows the Display value according to the
	 * {@link FunctionBasedDropDownChoice#valuesMapping}.
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

		@Override
		public C getObject(String id, IModel<? extends List<? extends C>> choices) {
			return WicketUtils.getObject(this, id, choices);
		}
	}
}