package gr.interamerican.bo2.utils.meta.formatters;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.GetProperties;
import gr.interamerican.bo2.utils.meta.transformations.FormatArray;

import java.util.Map;

/**
 * Formats a bean as CSV.
 * 
 * @param <B> 
 *        Type of bean.
 */
public class BeanCsvFormatter<B> implements Formatter<B> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Extracts the values of the properties.
	 */
	GetProperties get;
	/**
	 * Formats the values of the properties.
	 */
	FormatArray format;
	
	/**
	 * Creates a new BeanFormatter object. Default property value formatting.
	 * @param properties
	 *        ORDERED property names to include as CSV
	 * @param nullTolerant
	 *        Indicates if the nested property fetching should be null tolerant. @see GetProperties
	 */
	public BeanCsvFormatter(String[] properties, boolean nullTolerant) {
		this(properties, defaultFormatters(properties.length, null), nullTolerant);
	}
	
	/**
	 * Creates a new BeanFormatter object. 
	 * @param properties
	 *        ORDERED property names to include as CSV 
	 * @param formatters
	 *        ORDERED formatters to format property values with 
	 * @param nullTolerant
	 *        Indicates if the nested property fetching should be null tolerant. @see GetProperties
	 */
	public BeanCsvFormatter(String[] properties, Formatter<?>[] formatters, boolean nullTolerant) {
		this.get = new GetProperties(properties, nullTolerant);
		this.format = new FormatArray(formatters);
	}
	
	/**
	 * Creates a new BeanFormatter object. 
	 * @param properties
	 *        ORDERED property names to include as CSV 
	 * @param specialFormatters
	 *        A map containing any special (non-default) formatters required along with
	 *        the index of the property they correspond to.
	 * @param nullTolerant
	 *        Indicates if the nested property fetching should be null tolerant. @see GetProperties
	 *        
	 */
	public BeanCsvFormatter(String[] properties, Map<Integer, Formatter<?>> specialFormatters, boolean nullTolerant) {
		this(properties, defaultFormatters(properties.length, specialFormatters), nullTolerant);
	}

	public String format(B t) {
		Object[] values = get.execute(t);
		String[] columns = format.execute(values);
		return StringUtils.array2String(columns, StringConstants.SEMICOLON);
	}
	
	/**
	 * Creates a Formatters table with the specified length.
	 * 
	 * @param length
	 * @param specialFormatters 
	 * 
	 * @return Returns the table.
	 */
	private static Formatter<?>[] defaultFormatters(int length, Map<Integer, Formatter<?>> specialFormatters) {
		Formatter<?>[] formatters = new Formatter<?>[length];
		for (int i = 0; i < formatters.length; i++) {
			formatters[i] = ObjectFormatter.INSTANCE;
		}
		if(specialFormatters==null) {
			return formatters;
		}
		for(Map.Entry<Integer, Formatter<?>> entry : specialFormatters.entrySet()) {
			formatters[entry.getKey()] = entry.getValue();
		}
		return formatters;
	}

}
