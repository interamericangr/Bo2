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

import java.util.Collection;

import org.apache.wicket.markup.html.form.DropDownChoice;

import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.ext.Selectable;
import gr.interamerican.bo2.utils.attributes.Named;

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
public class CodifiedDropDownChoice<C extends Comparable<? super C>, P extends Selectable<C>>
extends FunctionBasedDropDownChoice<C, P> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2136L;

	/**
	 * Public Constructor.
	 * 
	 * @param id
	 *            Wicket Id of the DDC
	 */
	public CodifiedDropDownChoice(String id) {
		super(id, Selectable::getCode, Selectable::getName);
	}

	/**
	 * Creates a new CodifiedDropDownChoice object.
	 * 
	 * @param id
	 *            Wicket Id of the DDC
	 * @param clz
	 *            Class of {@link Selectable} objects
	 * @deprecated Use {@link #CodifiedDropDownChoice(String)}
	 */
	@Deprecated
	public CodifiedDropDownChoice(String id, @SuppressWarnings("unused") Class<P> clz) {
		super(id, Selectable::getCode, Selectable::getName);
	}

	/**
	 * Creates a new CodifiedDropDownChoice object.
	 * 
	 * @param id
	 *            Wicket Id of the DDC
	 * @param values
	 *            The Values of the {@link Selectable}'s this DDC will contain
	 */
	public CodifiedDropDownChoice(String id, Collection<P> values) {
		super(id, Selectable::getCode, Selectable::getName, values);
	}

	/**
	 * Creates a new CodifiedDropDownChoice object.
	 * 
	 * @param id
	 *            Wicket Id of the DDC
	 * @param values
	 *            The Values of the {@link Selectable}'s this DDC will contain
	 * @param clz
	 *            Class of {@link Selectable} objects
	 * @deprecated Use {@link #CodifiedDropDownChoice(String, Collection)}
	 */
	@Deprecated
	public CodifiedDropDownChoice(String id, Collection<P> values, @SuppressWarnings("unused")  Class<P> clz) {
		super(id, Selectable::getCode, Selectable::getName, values);
	}
}