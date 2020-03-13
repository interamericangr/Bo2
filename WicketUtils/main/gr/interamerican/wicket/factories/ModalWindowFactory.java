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
 * A factory for creating ModalWindow objects.
 */
public class ModalWindowFactory {
	
	/** The Constant WICKET_ID. */
	private static final String WICKET_ID = "id"; //$NON-NLS-1$
	
	/** The Constant TITLE. */
	private static final String TITLE = "title"; //$NON-NLS-1$
	
	/** The Constant INITIAL_WIDTH. */
	private static final String INITIAL_WIDTH = "initialWidth"; //$NON-NLS-1$
	
	/** The Constant INITIAL_HEIGHT. */
	private static final String INITIAL_HEIGHT = "initialHeight"; //$NON-NLS-1$
	
	/** The Constant WIDTH_UNIT. */
	private static final String WIDTH_UNIT = "widthUnit"; //$NON-NLS-1$
	
	/** The Constant HEIGHT_UNIT. */
	private static final String HEIGHT_UNIT = "heightUnit"; //$NON-NLS-1$
	
	/** The Constant RESIZABLE. */
	private static final String RESIZABLE = "resizable"; //$NON-NLS-1$
	
	/** The Constant USE_INITIAL_HEIGHT. */
	private static final String USE_INITIAL_HEIGHT = "useInitialHeight"; //$NON-NLS-1$
	
	/** The wicket id. */
	private static String wicketId;
	
	/** The title. */
	private static String title;
	
	/** The initial width. */
	private static Integer initialWidth;
	
	/** The initial height. */
	private static Integer initialHeight;
	
	/** The width unit. */
	private static String widthUnit;
	
	/** The height unit. */
	private static String heightUnit;
	
	/** The resizable. */
	private static Boolean resizable;
	
	/** The use initial height. */
	private static Boolean useInitialHeight;
	
	/** The Constant DEFAULT_SIZE. */
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
	 * Initialize properties.
	 *
	 * @param path the path
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
	 * Gets the initial width.
	 *
	 * @param properties the properties
	 * @return initialWidth
	 */
	private static Integer getInitialWidth(Properties properties) {
		String prop = properties.getProperty(INITIAL_WIDTH);
		return (gr.interamerican.bo2.utils.StringUtils.isNullOrBlank(prop)? DEFAULT_SIZE: Integer.parseInt(prop)) ;
	}
	
	/**
	 * Gets the initial height.
	 *
	 * @param properties the properties
	 * @return initialHeght
	 */
	private static Integer getInitialHeight(Properties properties){
		String prop= properties.getProperty(INITIAL_HEIGHT);
		return (gr.interamerican.bo2.utils.StringUtils.isNullOrBlank(prop) ? DEFAULT_SIZE : Integer.parseInt(prop)) ;
	}
	
	/**
	 * Gets the resizable.
	 *
	 * @param properties the properties
	 * @return resizable
	 */
	private static Boolean getResizable(Properties properties){
		String prop = properties.getProperty(RESIZABLE);
		return Boolean.parseBoolean(prop);
	}
	
	/**
	 * Gets the use initial height.
	 *
	 * @param properties the properties
	 * @return useInitialHeight
	 */
	private static Boolean getUseInitialHeight(Properties properties){
		String prop = properties.getProperty(USE_INITIAL_HEIGHT);
		return Boolean.parseBoolean(prop);	
	}

}
