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
package gr.interamerican.bo2.utils.meta.factories;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.Meta;
import gr.interamerican.bo2.utils.meta.descriptors.DateBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.PropertyDefinition;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Factory for {@link DateBoPropertyDescriptor}s.
 */
public class DateBoPDFactory {

	/**
	 * Creates a {@link DateBoPropertyDescriptor}.
	 * 
	 * @param pd
	 *        PropertyDescriptorDefinition
	 * 
	 * @return returns a DateBoPropertyDescriptor
	 * 
	 * @throws ParseException
	 */
	@SuppressWarnings("nls")
	public static DateBoPropertyDescriptor create(PropertyDefinition pd) throws ParseException {
		DateBoPropertyDescriptor result = new DateBoPropertyDescriptor();
		if (pd.getHasDefault()) {
			String value = pd.getDefaultValue();
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				date = df.parse(value);
				result.setDefaultValue(date);
			} catch (java.text.ParseException pe) {
				String msg = StringUtils.concat(
						"Could not create a Date given the value: ",
						pd.getDefaultValue() + " for property " + pd.getName()); 
				throw new ParseException(msg, pe);
			}
		}
		result.setMaxLength(Meta.getDefaultFormatterLength);
		BoPDFactoryUtils.addCommonStuff(result, pd);
		return result;
	}

	/**
	 * Hidden constructor.
	 */
	private DateBoPDFactory() { /* empty */ }

}
