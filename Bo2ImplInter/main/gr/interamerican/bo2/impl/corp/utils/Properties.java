/*
 * Created on 30 Ìבת 2006
 *
 */
package gr.interamerican.bo2.impl.corp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Subclass of <code>java.util.Properties</code> that solves an 
 * internationalization problem.
 * 
 * The basic implementation of <code>java.util.Properties</code> when 
 * trying to load a Properties object from an InputStream, assumes
 * that the file encoding is ISO 8859-1. This subclass will assume
 * that the file encoding is the default file encoding that is returned
 * by <code> System.getProperty("file.encoding") </code>. 
 * <br/>
 * The implementation of the <code>load(InputStream inStream)</code> 
 * method is based on the implementation of SUN's 1.4 runtime.
 *
 */
public class Properties extends java.util.Properties {
    
	/**
	 * Serialization algorithm version.
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * default system character encoding.
	 */
	private String characterEncoding=System.getProperty("file.encoding").trim(); //$NON-NLS-1$

	/**
	 * key value separators.
	 */
	private static final String keyValueSeparators = "=: \t\r\n\f"; //$NON-NLS-1$

	/**
	 * strict key value separators.
	 */
	private static final String strictKeyValueSeparators = "=:"; //$NON-NLS-1$

	

	/**
	 * white space characters.
	 */
	private static final String whiteSpaceChars = " \t\r\n\f"; //$NON-NLS-1$


	/* (non-Javadoc)
	 * @see java.util.Properties#load(java.io.InputStream)
	 */
	@Override
	public synchronized void load(InputStream inStream) throws IOException {

		BufferedReader in;

		
		//inStream = LocalizedInputStream.localize(inStream);     /*ibm@9781 ibm@11406*/
		//LocalizedInputStream.dontUnwrap(inStream);              /*ibm@5319*/
		in = new BufferedReader(new InputStreamReader(inStream, characterEncoding));
	while (true) {
			// Get next line
			String line = in.readLine();
			if (line == null)
				return;

			if (line.length() > 0) {
				// Continue lines that end in slashes if they are not comments
				char firstChar = line.charAt(0);

				//Start of ibm.26655
				int count = 0;
				boolean endOfEmptyLine = false;

				while ((firstChar == ' ') || (firstChar == '\t')) {
					count++;
					if (count == line.length()) {
						endOfEmptyLine = true;
						break;
					}
					firstChar = line.charAt(count);
				}

				if (endOfEmptyLine) continue;
				//End of ibm.26655
				if ((firstChar != '#') && (firstChar != '!')) {
					while (continueLine(line)) {
						String nextLine = in.readLine();
						if(nextLine == null)
							nextLine = ""; //$NON-NLS-1$
						String loppedLine = line.substring(0, line.length()-1);
						// Advance beyond whitespace on new line
						int startIndex=0;
						for(startIndex=0; startIndex<nextLine.length(); startIndex++)
							if (whiteSpaceChars.indexOf(nextLine.charAt(startIndex)) == -1)
								break;
						nextLine = nextLine.substring(startIndex,nextLine.length());
						line = new String(loppedLine+nextLine);
					}

					// Find start of key
					int len = line.length();
					int keyStart;
					for(keyStart=0; keyStart<len; keyStart++) {
						if(whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1)
							break;
					}

					// Blank lines are ignored
					if (keyStart == len)
						continue;

					// Find separation between key and value
					int separatorIndex;
					for(separatorIndex=keyStart; separatorIndex<len; separatorIndex++) {
						char currentChar = line.charAt(separatorIndex);
						if (currentChar == '\\')
							separatorIndex++;
						else if(keyValueSeparators.indexOf(currentChar) != -1)
							break;
					}

					// Skip over whitespace after key if any
					int valueIndex;
					for (valueIndex=separatorIndex; valueIndex<len; valueIndex++)
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
							break;

					// Skip over one non whitespace key value separators if any
					if (valueIndex < len)
						if (strictKeyValueSeparators.indexOf(line.charAt(valueIndex)) != -1)
							valueIndex++;

					// Skip over white space after other separators if any
					while (valueIndex < len) {
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
							break;
						valueIndex++;
					}
					String key = line.substring(keyStart, separatorIndex);
					String value = (separatorIndex < len) ? line.substring(valueIndex, len) : ""; //$NON-NLS-1$

					// Convert then store key and value
					key = loadConvert(key);
					value = loadConvert(value);
					put(key, value);
				}
			}
	}
	}


	/**
	 * @return the character encoding
	 */
	public String getCharacterEncoding() {
		return characterEncoding;
	}

	/**
	 * Sets the character encoding used for loading properties
	 * from an InputStream.
	 * 
	 * @param string New character encoding
	 */
	public synchronized void setCharacterEncoding(String string) {
		characterEncoding = string;
	}
	
	
	/**
	 * Converts encoded &#92;uxxxx to unicode chars
	 * and changes special saved chars to their original forms
	 * 
	 * @param theString String to convert.
	 * 
	 * @return Returns the converted string.
	 */
	private String loadConvert (String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);

		for(int x=0; x<len; ) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if(aChar == 'u') {
					// Read the xxxx
					int value=0;
			for (int i=0; i<4; i++) {
				aChar = theString.charAt(x++);
				switch (aChar) {
				  case '0': case '1': case '2': case '3': case '4':
				  case '5': case '6': case '7': case '8': case '9':
					 value = (value << 4) + aChar - '0';
				 break;
			  case 'a': case 'b': case 'c':
						  case 'd': case 'e': case 'f':
				 value = (value << 4) + 10 + aChar - 'a';
				 break;
			  case 'A': case 'B': case 'C':
						  case 'D': case 'E': case 'F':
				 value = (value << 4) + 10 + aChar - 'A';
				 break;
			  default:
							  throw new IllegalArgumentException(
										   "Malformed \\uxxxx encoding."); //$NON-NLS-1$
						}
					}
					outBuffer.append((char)value);
				} else {
					if (aChar == 't')
						outBuffer.append('\t');         /*ibm@7211*/

					else if (aChar == 'r')
						outBuffer.append('\r');         /*ibm@7211*/
					else if (aChar == 'n') {
						/*
						 * ibm@8897 do not convert a \n to a line.separator because
						 * on some platforms line.separator is a String of "\r\n".
						 * When a Properties class is saved as a file (store()) and
						 * then restored (load()) the restored input MUST be the same
						 * as the output (so that Properties.equals() works).
						 *
						 */
						outBuffer.append('\n');         /*ibm@8897 ibm@7211*/
					} else if (aChar == 'f')
						outBuffer.append('\f');         /*ibm@7211*/
					else                                /*ibm@7211*/
						outBuffer.append(aChar);        /*ibm@7211*/
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}	
	
	
	/**
	 * Returns true if the given line is a line that must
	 * be appended to the next line.
	 * 
	 * @param line
	 * @return  Returns true if the given line is a line that must
	 *          be appended to the next line.
	 */
	private boolean continueLine (String line) {
		int slashCount = 0;
		int index = line.length() - 1;
		while((index >= 0) && (line.charAt(index--) == '\\'))
			slashCount++;
		return (slashCount % 2 == 1);
	}	

}
