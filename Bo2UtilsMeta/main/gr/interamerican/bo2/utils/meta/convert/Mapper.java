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
package gr.interamerican.bo2.utils.meta.convert;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.exceptions.ConversionException;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

import java.util.Map;

/**
 * A {@link Mapper} encapsulates the mapping of  
 * 
 * @param <L> 
 *        Input type of the mapping. The type being mapped.
 * @param <R> 
 *        Output type of the mapping. The type of the result.
 */
public class Mapper<L,R> 
implements Converter<L, R> {
	/**
	 * Writer used to get string representations of mapped objects.
	 */
	Formatter<L> writer;
	/**
	 * Parser used to parse strings into objects.
	 */
	Parser<R> parser;
	/**
	 * Map with the mapping rows.
	 */
	Map<String, String> map;
	/**
	 * Description of this mapper.
	 */
	String description=StringConstants.EMPTY;
	
	public R convert(L l) throws ConversionException {		
		String sl = format(l);
		String sr = map.get(sl);
		if (sr==null) {
			return null;
		}
		try {
			return parse(sr);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}		
	}

	
	/**
	 * Creates a new Mapper object. 
	 *
	 * @param writer
	 *        Writer used to create string representations of the
	 *        objects being the input to this mapper.  
	 * @param parser
	 *        Parser used to parse the objects being the outputs of
	 *        this mapper.
	 * @param map
	 *        Map containing the value to value mappings.
	 */
	public Mapper(Formatter<L> writer, Parser<R> parser, Map<String, String> map) {
		super();
		this.writer = writer;
		this.parser = parser;
		this.map = map;
	}

	/**
	 * Gets the description.
	 *
	 * @return Returns the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Assigns a new value to the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {		
		return description;
	}
	
	/**	 
	 * Parses the specified string and converts it to the
	 * type of R.
	 *  
	 * @param sr
	 * @return R the output type of the mapping. The type of the result.
	 * @throws ParseException
	 */
	public R parse(String sr) throws ParseException {
		return parser.parse(sr);
	}

	/**
	 * Transforms an object of type L to its string representation.
	 * 
	 * @param l
	 * @return The object that will be represented as string. 
	 */
	public String format(L l) {
		String sl = writer.format(l);
		return sl;
	}
	
}
