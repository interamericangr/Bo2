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
package gr.interamerican.bo2.utils.meta.ext.factories;

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.Meta;
import gr.interamerican.bo2.utils.meta.descriptors.PropertyDefinition;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.factories.BoPDFactoryUtils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.FormatterResolver;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.parsers.ParserResolver;

/**
 * Factory for {@link CachedEntryBoPropertyDescriptor}s.
 */
public class CachedEntryBoPDFactory {
	
	/**
	 * Creates a {@link CachedEntryBoPropertyDescriptor} given a {@link PropertyDefinition}.
	 * 
	 * @param <C> 
	 *        Type of cache code.
	 * @param pd
	 *        PropertyDefinition
	 *        
	 * @return Returns a CachedEntryBoPropertyDescriptor
	 * 
	 * @throws ParseException
	 */
	@SuppressWarnings("nls")
	public static <C extends Comparable<? super C>> 
	CachedEntryBoPropertyDescriptor<TypedSelectable<C>, C> 
	create(PropertyDefinition pd) 
	throws ParseException {

		Parser<C> parser = ParserResolver.<C>getParser(CacheRegistry.<C>getRegisteredCacheCodeType(pd.getCacheName()));
		Formatter<C> formatter = FormatterResolver.<C>getFormatter(CacheRegistry.<C>getRegisteredCacheCodeType(pd.getCacheName()));
		
		CachedEntryBoPropertyDescriptor<TypedSelectable<C>, C> result = 
			new CachedEntryBoPropertyDescriptor<TypedSelectable<C>, C>(pd.getListCd(), pd.getSubListCd(), pd.getCacheName(), parser, formatter);
		
		if(pd.getHasDefault()) {
			String value = pd.getDefaultValue();
			try {
				TypedSelectable<C> entry = result.parse(value);
				result.setDefaultValue(entry);
			} catch (ParseException e) {
				String msg = StringUtils.concat(
					"Could not parse " + pd.getDefaultValue() + " for property " + pd.getName() + 
					"of entry [" + pd.getListCd() + "," + StringUtils.toString(pd.getSubListCd()) + "]"); 
				throw new ParseException(msg);
			}
		}
		result.setMaxLength(Meta.getDefaultFormatterLength);
		BoPDFactoryUtils.addCommonStuff(result, pd);
		return result;
	}
	
	/**
	 * Hidden constructor. 
	 */
	private CachedEntryBoPDFactory(){ /* empty */ }
}
