package gr.interamerican.bo2.utils.meta.ext.formatters;

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * {@link Formatter} implementation for {@link TranslatableEntry}.
 * <br/>
 * This formatter depends on a languageId being retrievable from the Bo2Session
 * thread local singletons.
 * 
 * @param <L> Language id type.
 */
public class TranslatableEntryFormatter<L> implements Formatter<TranslatableEntry<?, ?, L>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String format(TranslatableEntry<?, ?, L> t) {
		L languageId = Bo2Session.<Object, L>getSession().getLanguageId();
		return t.getTranslation(languageId);
	}

}
