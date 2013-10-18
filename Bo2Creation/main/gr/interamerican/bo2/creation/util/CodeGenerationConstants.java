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

import gr.interamerican.bo2.utils.ReflectionUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Constants for CodeGen class
 */
@SuppressWarnings("nls")
public class CodeGenerationConstants {
	
	/**
	 * Hidden constructor.
	 */
	private CodeGenerationConstants() {/* empty */}
	
	/**
	 * a semicolon
	 */
	public static final String SEMICOLON = ";"; 
	
	/**
	 * Start comments
	 */
	public static final String COMMENT_START = "/*";
	
	/**
	 * Start comments.
	 */
	public static final String COMMENT_END = "*/"; 
	
	/**
	 * Line comments.
	 */
	public static final String LINE_COMMENT = "//"; 
	
	/**
	 * End of line.
	 */
	public static final String CR = "\n"; 

	
	/**
	 * an opening bracket
	 */
	public static final String OPENING_CURLY_BRACKET = "{"; 
	
	/**
	 * a closing bracket
	 */
	public static final String CLOSING_CURLY_BRACKET = "}"; 
	
	/**
	 * an opening parenthesis
	 */
	public static final String OPENING_PARENTHESIS = "("; 
	
	/**
	 * a closing parenthesis
	 */
	public static final String CLOSING_PARENTHESIS = ")";
	
	/**
	 * an opening parenthesis
	 */
	public static final String OPENING_BRACKET = "["; 
	
	/**
	 * a closing parenthesis
	 */
	public static final String CLOSING_BRACKET = "]"; 
	
	/**
	 * less than <.
	 */
	public static final String LT = "<"; 
	
	/**
	 * greater than >.
	 */
	public static final String GT = ">"; 
	
	/**
	 * a space
	 */
	public static final String SPACE = " ";  
	
	/**
	 * a tab
	 */
	public static final String TAB = "\t";
	
	/**
	 * change line
	 */
	public static final String NEWLINE = "\n";
	
	/**
	 * equals
	 */
	public static final String EQUALS = "="; 
	
	/**
	 * this java keyword
	 */
	public static final String KEYWORD_THIS = "this"; 
	
	/**
	 * return java keyword
	 */
	public static final String KEYWORD_RETURN = "return";
	
	/**
	 * new java keyword
	 */
	public static final String KEYWORD_NEW = "new";
	
	/**
	 * void java keyword
	 */
	public static final String KEYWORD_VOID = "void";
	
	/**
	 * super java keyword
	 */
	public static final String KEYWORD_SUPER = "super";
	
	/**
	 * null java keyword
	 */
	public static final String KEYWORD_NULL = "null";
	
	/**
	 * if
	 */
	public static final String IF = "if";
	
	/**
	 * a dot
	 */
	public static final String DOT = ".";
	
	/**
	 * a comma
	 */
	public static final String COMMA = ",";
	
	/**
	 * empty string 
	 */
	public static final String EMPTY_STRING = "";
	
	/**
	 * the public modifier
	 */
	public static final String MODIFIER_PUBLIC = "public";
	
	/**
	 * the private modifier
	 */
	public static final String MODIFIER_PRIVATE = "private"; 
	
	/**
	 * suffix of a getter method
	 */
	public static final String GETTER_PREFIX = "get"; 
	
	/**
	 * suffix of a setter method
	 */
	public static final String SETTER_PREFIX = "set"; 
	
	/**
	 * a method parameter suffix (param1, param2 etc) 
	 */
	public static final String PARAMETER_SUFFIX = "param";
	
	/**
	 * class (extension of .class files)
	 */
	public static final String CLASS_EXTENSION = "class";
	
	/**
	 * create (a method of {@link Set})
	 */
	public static final String SET_ADDALL_METHOD = "addAll";
	
	/**
	 * create (a method of {@link Set})
	 */
	public static final String SET_CLEAR_METHOD = "clear";
	
	/**
	 * a double quote
	 */
	public static final String DOUBLEQUOTE = "\"";
	
	/**
	 * an comment indicating an empty code segment
	 */
	public static final String COMMENT_EMPTY = "/* empty */";
		
	/**
	 * the FQCN of String.class 
	 */
	public static final String STRING_CLASSNAME = String.class.getName();
	
	/**
	 * the FQCN of Object.class 
	 */
	public static final String OBJECT_CLASSNAME = Object.class.getName();
	
	/**
	 * the FQCN of HashSet.class 
	 */
	public static final String HASHSET_CLASSNAME = HashSet.class.getName();
	
	/**
	 * the FQCN of Set.class 
	 */
	public static final String SET_CLASSNAME = Set.class.getName();
	
	/**
	 * the FQCN of Set.class 
	 */
	public static final String LONG_CLASSNAME = Long.class.getName();
	
	/**
	 * the FQCN of {@link ReflectionUtils}
	 */
	public static final String REFLECTIONUTILS_CLASSNAME = ReflectionUtils.class.getName();
	
	/**
	 * the FQCN of Class
	 */
	public static final String CLASS_CLASSNAME = Class.class.getName();
	
	/**
	 * the FQCN of {@link Serializable}
	 */
	public static final String SERIALIZABLE = "java.io.Serializable";
	
	/**
	 * the FQCN of {@link Serializable}
	 */
	public static final String SERIALIAL_VERSION_UID = "serialVersionUID";
}
