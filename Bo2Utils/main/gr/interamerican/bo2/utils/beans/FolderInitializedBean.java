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
package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.IOException;

/**
 * This bean is a container of String fields that can be 
 * initialized by the files of a folder.
 * 
 * The folder can be any folder in the file system, or a package in the
 * local class path. The bean search for a file with the name of each
 * String property it has, and it will initialize the property with the
 * contents of the file. If there is no file with the name of a property,
 * then this property will be initialized with null. Files are expected 
 * to have the .txt extension.
 */
public class FolderInitializedBean {
	
	/**
	 * Extension.
	 */
	private static final String EXTENSION = ".txt"; //$NON-NLS-1$
	
	/**
	 * Creates a new PropertiesInitializedBean object.
	 *  
	 * @param path 
	 *        Path to the folder or package. Path must end with folder
	 *        delimiter.
	 *         
	 * @param inClassPath 
	 *        Indicator if the folder is located in the classpath or in
	 *        the file system. 
	 */
	public FolderInitializedBean(String path, boolean inClassPath) {
		super();
		
		PropertyDescriptor[] beanProperties = 
			JavaBeanUtils.getBeansProperties(this.getClass());
		for (int i = 0; i < beanProperties.length; i++) {
			PropertyDescriptor pd = beanProperties[i];
			Class<?> type = pd.getPropertyType();
			if (type==String.class) {				
				String filePath = path + pd.getName() + EXTENSION;
				String[] lines;
				try {
					lines = inClassPath ?
						StreamUtils.readResourceFile(filePath) :
						StreamUtils.readFile(filePath);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				String value = lines==null ?
					null : 
					StringUtils.array2String(lines, StringConstants.NEWLINE);
				JavaBeanUtils.setProperty(pd, value, this);
			}
		}
	}

}
