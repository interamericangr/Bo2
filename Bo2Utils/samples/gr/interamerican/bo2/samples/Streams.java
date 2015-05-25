package gr.interamerican.bo2.samples;

import gr.interamerican.bo2.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Streams utility.
 */
public class Streams {
	
	
	/**
	 * Creates an inputstream that reads the specified lines.
	 * 
	 * @param charset
	 * @param lines
	 * 
	 * @return Returns the inputstream.
	 */
	public static InputStream input(Charset charset, String... lines) {
		String str = StringUtils.concatSeparated("\n", lines); //$NON-NLS-1$
		byte[] bytes = str.getBytes(charset);
		return new ByteArrayInputStream(bytes);		
	}
	
	

	

}
