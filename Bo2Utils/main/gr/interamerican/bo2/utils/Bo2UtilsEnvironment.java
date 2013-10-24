/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

/**
 * Runtime environment for Bo2Utils.
 */
public class Bo2UtilsEnvironment {
		
	/**
	 * Singleton instance.
	 */
	static final Bo2UtilsEnvironment SINGLETON = new Bo2UtilsEnvironment(); 
	
	/**
	 * Sets the environment parameters.
	 * <br/>
	 * This is not part of the public API, do not use it.
	 * 
	 * @param shortDf 
	 * @param isoDf 
	 * @param longDf 
	 * @param textCharset 	   
	 * @param resourceFileCharset 
	 */
	public static void setEnvironment(String shortDf, String isoDf, String longDf, String textCharset, String resourceFileCharset) {
		SINGLETON.dfShortPattern = shortDf;
		SINGLETON.dfIsoPattern = isoDf;
		SINGLETON.dfLongPattern = longDf;
		SINGLETON.textCharset = Charset.forName(textCharset);
		SINGLETON.resourceFileCharset = Charset.forName(resourceFileCharset);
	}
	
	/**
	 * Gets the defaultTextCharset.
	 *
	 * @return Returns the defaultTextCharset
	 */
	public static Charset getDefaultTextCharset() {
		return SINGLETON.textCharset;
	}
	
	/**
	 * Gets the defaultResourceFileCharset.
	 *
	 * @return Returns the defaultResourceFileCharset
	 */
	public static Charset getDefaultResourceFileCharset() {
		return SINGLETON.resourceFileCharset;
	}
	
	/**
	 * Gets the short date format pattern.
	 * 
	 * @return Returns the short date format pattern.
	 */
	public static String getShortDateFormatPattern() {		
		return  SINGLETON.dfShortPattern;
	}
	
	/**
	 * Gets the iso date format pattern.
	 * 
	 * @return Returns the iso date format pattern.
	 */
	public static String getIsoDateFormatPattern() {		
		return  SINGLETON.dfIsoPattern;
	}
	
	/**
	 * Gets the long date format pattern.
	 * 
	 * @return Returns the long date format pattern.
	 */
	public static String getLongDateFormatPattern() {		
		return  SINGLETON.dfLongPattern;
	}
	
	/**
	 * Default date format (short format).
	 */
	private String dfShortPattern = "dd/MM/yyyy";  //$NON-NLS-1$

	/**
	 * ISO date format (ISO format).
	 * 
	 */
	private String dfIsoPattern = "yyyy-MM-dd"; //$NON-NLS-1$
	
	 /**
	 * long date format for calendar objets with time
	 */
	private String dfLongPattern = "yyyy-MM-dd-HH:mm:ss.SSS"; //$NON-NLS-1$
	
	/**
	 * Default text charset. This is the charset that should be used when reading or writing
	 * text files. Initialized with the default platform Charset.
	 */
	private Charset textCharset = Charset.defaultCharset();
	
	/**
	 * Default resource file charset. This is the charset that should be used when reading
	 * resource files. Initialized with the default platform Charset.
	 */
	private Charset resourceFileCharset = Charset.defaultCharset();
	
	/**
	 * Creates a new Bo2UtilsEnvironment object. 
	 * 
	 * Hidden constructor.
	 *
	 */
	private Bo2UtilsEnvironment() {
		super();
	}

	/**
	 * Gets the dfShort.
	 *
	 * @return Returns the dfShort
	 */
	SimpleDateFormat getDfShort() {
		return new SimpleDateFormat(dfShortPattern);
	}

	/**
	 * Gets the dfIso.
	 *
	 * @return Returns the dfIso
	 */
	SimpleDateFormat getDfIso() {
		return new SimpleDateFormat(dfIsoPattern);
	}

	/**
	 * Gets the dfLong.
	 *
	 * @return Returns the dfLong
	 */
	SimpleDateFormat getDfLong() {
		return new SimpleDateFormat(dfLongPattern);
	}
	
	/**
	 * Gets the textCharset.
	 *
	 * @return Returns the textCharset
	 */
	Charset getTextCharset() {
		return textCharset;
	}

}
