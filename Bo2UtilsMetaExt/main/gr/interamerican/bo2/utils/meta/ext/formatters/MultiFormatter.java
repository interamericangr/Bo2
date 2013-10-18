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
package gr.interamerican.bo2.utils.meta.ext.formatters;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.beans.TypeBasedSelection;
import gr.interamerican.bo2.utils.meta.formatters.DateFormatter;
import gr.interamerican.bo2.utils.meta.formatters.DecimalFormatter;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link MultiFormatter} delegates formatting to another
 * {@link Formatter} based on the type of the object being 
 * formatted.
 */
public class MultiFormatter 
implements Formatter<Object>{
	
	/**
	 * Formatters.
	 */
	TypeBasedSelection<Formatter<?>> formatters;

	/**
	 * Creates a new MultiFormatter object. 
	 *
	 */
	public MultiFormatter() {
		super();
		formatters = new TypeBasedSelection<Formatter<?>>();
		int decimalDigits = 2;
		formatters.registerSelection(Money.class, MoneyFormatter.INSTANCE);
		formatters.registerSelection(Double.class, new DecimalFormatter<Double>(decimalDigits));
		formatters.registerSelection(BigDecimal.class, new DecimalFormatter<BigDecimal>(decimalDigits));
		DateFormat df = new SimpleDateFormat(Bo2UtilsEnvironment.getShortDateFormatPattern());
		formatters.registerSelection(Date.class, new DateFormatter(df));
		formatters.registerSelection(TranslatableEntry.class, new TranslatableEntryFormatter<Object>());
		formatters.registerSelection(TranslatableEntryOwner.class, new TranslatableEntryOwnerFormatter<Object>());
		formatters.registerSelection(Object.class, new ObjectFormatter());
	}
	
	@Override
	public String format(Object t) {
		@SuppressWarnings("unchecked")
		Formatter<Object> f = (Formatter<Object>) formatters.select(t);
		return f.format(t);
	} 
	
	

}
