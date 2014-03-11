package gr.interamerican.bo2.utils.meta.ext.parsers;

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.cache.NamedCacheProvider;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @param <T> Type of entry 
 * @param <C> Type of cache code
 */
public class MultipleCachedEntriesParser
<T extends TypedSelectable<C>, 
 C extends Comparable<? super C>>
extends NamedCacheProvider<C>
implements Parser<Collection<T>> {
	
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
	public MultipleCachedEntriesParser(String cacheName, Long typeId, Parser<C> codeParser) {
		super(cacheName);
		this.typeId = typeId;
		this.codeParser = codeParser;
	}

	@SuppressWarnings("unchecked")
	public List<T> parse(String value) throws ParseException {
		String[] tokens = TokenUtils.splitTrim(value, StringConstants.COMMA);
		List<T> list = new ArrayList<T>();
		for(String token : tokens) {
			C code = codeParser.parse(token);
			list.add((T) cache().get(typeId, code));
		}
		return list;
	}

}
