package gr.interamerican.bo2.impl.open.records;

import gr.interamerican.bo2.arch.records.Record;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.Utils;

import java.nio.charset.Charset;

/**
 * Base {@link Record} implementation
 */
public abstract class AbstractBaseRecord implements Record {
	
	/**
	 * Charset.
	 */
	private Charset charset;
	
	public void setCharset(Charset charset) {
		this.charset = charset;
	}
	
	/**
	 * @return Returns the charset or the default Bo2 deployment text files charset, if it is not set.
	 */
	protected Charset charset() {
		return Utils.notNull(charset, Bo2UtilsEnvironment.getDefaultTextCharset());
	}

}
