package gr.interamerican.bo2.utils.meta.ext.parsers;

import gr.interamerican.bo2.arch.ext.OwnedEntry;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.cache.NamedCacheProvider;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

/**
 * 
 * @param <T> Type of entry 
 * @param <C> Type of cache code
 */
public class CachedEntryOwnerParser
<T extends TranslatableEntryOwner<C, ?, ?>, 
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
	public CachedEntryOwnerParser(String cacheName, Long typeId, Parser<C> codeParser) {
		super(cacheName);
		this.typeId = typeId;
		this.codeParser = codeParser;
	}

	@SuppressWarnings("unchecked")
	public T parse(String value) throws ParseException {
		TypedSelectable<C> typedSelectable = cache().get(typeId, codeParser.parse(value));
		if (typedSelectable==null) {
			return null;
		}
		if(!(typedSelectable instanceof OwnedEntry)) {
			@SuppressWarnings("nls")
			String msg = "Entry [" + typedSelectable.getTypeId() + ", " + typedSelectable.getCode() + "] is not an OwnedEntry";
			throw new RuntimeException(msg);
		}
		OwnedEntry<C, ?, ?> entry = (OwnedEntry<C, ?, ?>) typedSelectable;
		return (T) entry.getOwner();
	}

}
