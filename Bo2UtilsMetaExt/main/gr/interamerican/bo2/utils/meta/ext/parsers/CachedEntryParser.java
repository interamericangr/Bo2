package gr.interamerican.bo2.utils.meta.ext.parsers;

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.cache.NamedCacheProvider;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

/**
 * 
 * @param <T> Type of entry 
 * @param <C> Type of cache code
 */
public class CachedEntryParser
<T extends TypedSelectable<C>, 
 C extends Comparable<? super C>>
extends NamedCacheProvider<C>
implements Parser<T> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Entry typeId
	 */
	protected Long typeId;
	
	/**
	 * Parser for entry code.
	 */
	protected Parser<C> codeParser;
	
	/**
	 * Creates a new CachedEntryParser object.
	 *  
	 * @param cacheName 
	 * @param typeId 
	 * @param codeParser 
	 */
	public CachedEntryParser(String cacheName, Long typeId, Parser<C> codeParser) {
		super(cacheName);
		this.typeId = typeId;
		this.codeParser = codeParser;
	}
	
	@SuppressWarnings("unchecked")
	public T parse(String value) throws ParseException {
		return (T) cache().get(typeId, codeParser.parse(value));
	}

}
