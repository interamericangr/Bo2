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
package gr.interamerican.bo2.creation.util;

import static gr.interamerican.bo2.creation.util.CodeGenerationConstants.COMMA;
import static gr.interamerican.bo2.creation.util.CodeGenerationConstants.COMMENT_END;
import static gr.interamerican.bo2.creation.util.CodeGenerationConstants.COMMENT_START;
import static gr.interamerican.bo2.creation.util.CodeGenerationConstants.CR;
import static gr.interamerican.bo2.creation.util.CodeGenerationConstants.EMPTY_STRING;
import static gr.interamerican.bo2.creation.util.CodeGenerationConstants.GT;
import static gr.interamerican.bo2.creation.util.CodeGenerationConstants.LINE_COMMENT;
import static gr.interamerican.bo2.creation.util.CodeGenerationConstants.LT;
import static gr.interamerican.bo2.creation.util.CodeGenerationConstants.PARAMETER_SUFFIX;
import static gr.interamerican.bo2.creation.util.CodeGenerationConstants.SPACE;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
@SuppressWarnings("nls")
public class CodeGenerationUtilities {

	/**
	 * Hidden constructor.. 
	 *
	 */
	private CodeGenerationUtilities() { /*empty*/}
	
	/**
	 * Singleton map with standard defaults.
	 */
	static final Map<Class<?>, String> DEFAULTS_STRINGS = 
		new HashMap<Class<?>, String>();	
	static {		
		DEFAULTS_STRINGS.put(double.class, "0.0");
		DEFAULTS_STRINGS.put(int.class, "0");
		DEFAULTS_STRINGS.put(long.class, "0L");
		DEFAULTS_STRINGS.put(short.class, "0");
		DEFAULTS_STRINGS.put(boolean.class, "false");
		DEFAULTS_STRINGS.put(char.class, "' '");
		DEFAULTS_STRINGS.put(byte.class, "0");
	}	
	
	
	
	/** 
	 * Cleans up a type from any generic types, because the
	 * Javassist 3.9 compiler does not support Java 1.5.
	 * 
	 * @param type a type
	 * @return the type without generic
	 */	
	public static String cleanUpGenericFromType(String type) {
		return StringUtils.removePartBetweenElements(type, LT, GT);
	}
	
	/**
	 * This method will clear java code from comments.
	 * 
	 * @param code
	 *        String that contains java code.
	 *        
	 * @return Returns the string.
	 */
	public static String cleanJavaCode(String code) {
		String s = clearJavaComments(code);
		String[] lines = TokenUtils.toLines(s, true);
		return StringUtils.concatSeparated(CR, lines);
	}
	
	/**
	 * Clears java comments from java code.
	 * @param code
	 * 
	 * @return The code cleared from eny comments.
	 */
	public static String clearJavaComments(String code) {
		String cleaned = code;
		String temp;
		do {
			temp = cleaned;
			cleaned = StringUtils.removePartBetweenElements
				(cleaned, COMMENT_START, COMMENT_END);
			cleaned = StringUtils.removePartBetweenElements
				(cleaned, LINE_COMMENT, CR);
		} while (!cleaned.equals(temp));
		return cleaned;
	}
	
	
	/**
	 * Generates a string of method parameters to be put in a method declaration
	 * 
	 * @param parameterTypes the types of the parameters (ordered)
	 * @return the generated arguments string
	 */
	public static  String generateMethodDeclarationParameters(Class<?>[] parameterTypes) {		
		String res = EMPTY_STRING;		
		if(parameterTypes.length == 0) {
			return res;
		}		
		int ctr=0;
		for(Class<?> parameterType : parameterTypes) {
			res = res + parameterType.getCanonicalName() + SPACE + 
			PARAMETER_SUFFIX + ctr + COMMA + SPACE;
			ctr++;
		}
		
		return res.substring(0, res.length()-2);
	}

	/**
	 * Generates a string of method arguments to be put in a method invocation
	 * 
	 * @param population the number of parameters
	 * @return the generated arguments string
	 */
	public static String generateMethodInvocationParameters(Integer population) {
		String res = EMPTY_STRING;		
		if(population == 0) {
			return res;
		}		
		for (int i = 0; i < population; i++) {
			res = res + PARAMETER_SUFFIX + i + COMMA + SPACE;
		}		
		return res.substring(0, res.length()-2);
	}
	
	/**
	 * Creates a new ClassCreationException.
	 * 
	 * @param type
	 * 
	 * @return Returns a new ClassCreationException.
	 */
	public static ClassCreationException typeNotSupported(Class<?> type) {
		String msg = type.getName() + " is not supported by this ClassCreator."; //$NON-NLS-1$
		return new ClassCreationException(msg);
	}
	
	/**
	 * Gets a String that represents a default value for a type.
	 * @param clazz 
	 *        Type.
	 * 
	 * @return returns a String that represents a default value for a type.
	 */
	public static String getDefaultValueString(Class<?> clazz) {
		return StringUtils.toString(DEFAULTS_STRINGS.get(clazz));
	}
	

}
