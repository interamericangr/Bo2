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
package gr.interamerican.bo2.utils.meta.ext.descriptors;

import gr.interamerican.bo2.arch.ext.Selectable;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.descriptors.AbstractBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;

import java.io.Serializable;
import java.util.Set;

/**
 * BoPropertyDescriptor for {@link Selectable} objects.
 * 
 * TODO: {@link #format(Selectable)} and {@link #parse(String)} implementations are hacks.
 * 
 * @param <C>
 *        Type of Selectable code.
 */
public class SelectableBoPropertyDescriptor<C extends Comparable<? super C>> 
extends AbstractBoPropertyDescriptor<Selectable<C>> 
implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * set of values.
	 */
	 Set<Selectable<C>> choices;

	/**
	 * Creates a new EntryBoPropertyDescriptor object. 
	 *
	 */
	public SelectableBoPropertyDescriptor() {
		super(null);
	}
	
	/**
	 * Gets the set of choices.
	 * 
	 * @return Returns the set of choices.
	 */
	public Set<Selectable<C>> getChoices() {
		return choices;
	}

	/**
	 * Sets the set of choices.
	 * 
	 * @param choices New set of choices.
	 */
	public void setChoices(Set<Selectable<C>> choices) {
		this.choices = choices;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Selectable<C> parse(String value) throws ParseException {
		if(StringUtils.isNullOrBlank(value)) {
			return null;
		}
		/*
		 * Will always attempt to parse the formatted value as long.
		 * If parsing fails, it will be left a String.
		 */
		Comparable resultCode = null;
		try {
			resultCode = new LongParser().parse(value);
		}catch (ParseException e) {
			resultCode = value;
		}
		Selectable result = new TypedSelectableImpl();
		result.setCode(resultCode);
		return Utils.cast(result);
	}
	
	@Override
	public String format(Selectable<C> t) {
		/*
		 * Generic formatting using String.valueOf
		 * TODO: Fix this limitation.
		 */
		if(t==null || t.getCode()==null) {
			return null;
		}
		return String.valueOf(t.getCode());
	}

	@Override
	protected Formatter<Selectable<C>> getFormatter() {
		return null;
	}
	
}
