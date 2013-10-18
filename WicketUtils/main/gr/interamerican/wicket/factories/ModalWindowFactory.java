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
package gr.interamerican.wicket.factories;

import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Properties;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

/**
 * 
 */
public class ModalWindowFactory {
	/**
	 * 
	 */
	private static final String WICKET_ID = "id"; //$NON-NLS-1$
	/**
	 * 
	 */
	private static final String TITLE = "title"; //$NON-NLS-1$
	/**
	 * 
	 */
	private static final String INITIAL_WIDTH = "initialWidth"; //$NON-NLS-1$
	/**
	 * 
	 */
	private static final String INITIAL_HEIGHT = "initialHeight"; //$NON-NLS-1$
	/**
	 * 
	 */
	private static final String WIDTH_UNIT = "widthUnit"; //$NON-NLS-1$
	/**
	 * 
	 */
	private static final String HEIGHT_UNIT = "heightUnit"; //$NON-NLS-1$
	/**
	 * 
	 */
	private static final String RESIZABLE = "resizable"; //$NON-NLS-1$
	/**
	 * 
	 */
	private static final String USE_INITIAL_HEIGHT = "useInitialHeight"; //$NON-NLS-1$
	/**
	 * 
	 */
	private static String wicketId;
	/**
	 * 
	 */
	private static String title;
	/**
	 * 
	 */
	private static Integer initialWidth;
	/**
	 * 
	 */
	private static Integer initialHeight;
	/**
	 * 
	 */
	private static String widthUnit;
	/**
	 * 
	 */
	private static String heightUnit;
	/**
	 * 
	 */
	private static Boolean resizable;
	/**
	 * 
	 */
	private static Boolean useInitialHeight;
	
	/**
	 * 
	 */
	private static final Integer DEFAULT_SIZE = 200;

	/**
	 * Create a modalWindow with a specific properties file. 
	 * The properties file have to contains the properties of modalWindow.
	 * @param path Path to the properties file.
	 * 		  
	 * @return the ModalWindow
	 */
	public static ModalWindow createModalWindow(String path){	
		initializeProperties(path);	
		ModalWindow modalWindow = new ModalWindow(wicketId); 
		modalWindow.setTitle(title); 
		modalWindow.setInitialWidth(initialWidth);
		modalWindow.setInitialHeight(initialHeight);
		modalWindow.setWidthUnit(widthUnit);
		modalWindow.setHeightUnit(heightUnit);
		modalWindow.setResizable(resizable);
		modalWindow.setUseInitialHeight(useInitialHeight);
		return modalWindow;
	}

	/**
	 * @param path
	 */
	private static void initializeProperties(String path) {
		 Properties properties = CollectionUtils.readProperties(path);
		 wicketId = properties.getProperty(WICKET_ID);
		 title = properties.getProperty(TITLE);
		 initialWidth = getInitialWidth(properties);
		 initialHeight = getInitialHeight(properties);
		 widthUnit = properties.getProperty(WIDTH_UNIT);
		 heightUnit = properties.getProperty(HEIGHT_UNIT);
		 resizable = getResizable(properties);
		 useInitialHeight = getUseInitialHeight(properties);
	}

	/**
	 * @param properties
	 * 
	 * @return initialWidth
 	 */
	private static Integer getInitialWidth(Properties properties) {
		String prop = properties.getProperty(INITIAL_WIDTH);
		return (gr.interamerican.bo2.utils.StringUtils.isNullOrBlank(prop)? DEFAULT_SIZE: Integer.parseInt(prop)) ;
	}
	
	/**
	 * @param properties 
	 * 
	 * @return initialHeght
	 */
	private static Integer getInitialHeight(Properties properties){
		String prop= properties.getProperty(INITIAL_HEIGHT);
		return (gr.interamerican.bo2.utils.StringUtils.isNullOrBlank(prop) ? DEFAULT_SIZE : Integer.parseInt(prop)) ;
	}
	
	/**
	 * @param properties
	 * 
	 * @return resizable
	 */
	private static Boolean getResizable(Properties properties){
		String prop = properties.getProperty(RESIZABLE);
		return Boolean.parseBoolean(prop);
	}
	
	/**
	 * @param properties
	 * 
	 * @return useInitialHeight
	 */
	private static Boolean getUseInitialHeight(Properties properties){
		String prop = properties.getProperty(USE_INITIAL_HEIGHT);
		return Boolean.parseBoolean(prop);	
	}

}
