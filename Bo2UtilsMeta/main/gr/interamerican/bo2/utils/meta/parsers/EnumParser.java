package gr.interamerican.bo2.utils.meta.parsers;

import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

/**
 * Parser for enums.
 * @param <E> 
 */
public class EnumParser<E extends Enum<E>> extends AbstractParser<E>{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Type of the enum.
	 */
	Class<E> enumType;
	
	/**
	 * Creates a new EnumParser object. 
	 * @param enumType 
	 */
	public EnumParser(Class<E> enumType) {
		this.enumType = enumType;
	}

	@Override
	protected E mainParse(String value) throws ParseException {
		return Enum.valueOf(enumType, value);
	}

}
