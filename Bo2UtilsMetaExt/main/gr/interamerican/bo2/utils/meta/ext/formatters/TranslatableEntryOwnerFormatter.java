package gr.interamerican.bo2.utils.meta.ext.formatters;

import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * {@link Formatter} implementation for {@link TranslatableEntryOwner}.
 * <br/>
 * This formatter depends on a languageId being retrievable from the Bo2Session
 * thread local singletons.
 * 
 * @param <L> Language id type.
 */
public class TranslatableEntryOwnerFormatter<L> implements Formatter<TranslatableEntryOwner<?, ?, L>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String format(TranslatableEntryOwner<?, ?, L> t) {
		L languageId = Bo2Session.<Object, L>getSession().getLanguageId();
		return t.getEntry().getTranslation(languageId);
	}

}
