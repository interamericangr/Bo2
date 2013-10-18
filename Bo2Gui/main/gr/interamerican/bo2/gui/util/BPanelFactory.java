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
package gr.interamerican.bo2.gui.util;

import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.GenericsUtils;
import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * Factory that creates {@link BPanel} objects from a
 * properties file. <br/>
 * 
 * The properties file must obligatory have the following properties:
 * <table> 
 * <tr>  
 * <td> panelType </td>
 * <td> Fully qualified class name of the BPanel sub-type
 *      that will be instantiated</td>
 * </tr>
 * <tr> 
 * <td> panelType </td>
 * <td> Fully qualified class name of the panel's model type</td>
 * </tr>
 * </table>
 * The rest properties are copied to the model object.
 * Then a panel is created with the specified model.
 */
public class BPanelFactory {
	
	/**
	 * Creates a new BPanel from a properties file. <br/>
	 * 
	 * The properties file must have the following property set.
	 * <li><code>panelType</code> : 
	 *     Fully qualified class name of the panel's class </li>
	 *     
	 * The method instantiates the panel using {@link #create(Class)}.
	 * Then it uses {@link JavaBeanUtils#copyFromProperties(Properties, Object)} 
	 * to modify the panel's model with the values from the specified 
	 * properties object.
	 * 
	 * 
	 * @param properties
	 *        Properties object specifying the panel and model	  
	 *        types and the model object's property values. 
	 * @param <P> 
	 *        Type of panel.      
	 * @param <T> 
	 *        Type of model.  	          
	 * 
	 * @return Returns a new panel with a model created according to
	 *         the the specified properties.
	 */
	public static <T,P extends BPanel<T>> 
	P create(Properties properties) {
		String panelClassName = CollectionUtils.notEmptyProperty
			("panelType", properties); //$NON-NLS-1$
		@SuppressWarnings("unchecked")
		Class<P> panelType = (Class<P>) Factory.getType(panelClassName);
		Class<T> modelType = getModelClass(panelType);
		T model = Factory.create(modelType);
		JavaBeanUtils.copyFromProperties(properties, model);
		return create(panelType,model);
	}
	
	/**
	 * Creates a new BPanel of the specified type. <br/>
	 * 
	 * The factory method uses {@link GenericsUtils} to find the class
	 * of this panel type's model. Then a model object is instantiated 
	 * by {@link Factory}. The panel is created using the constructor
	 * that takes the model as argument. If no such constructor exists,
	 * then a RuntimeException is thrown.
	 * 
	 * @param panelType 
	 *        Type of panel.
	 * @param <P>
	 *        Type of panel.
	 * @param <T> 
	 *        Type of model.       
	 * 
	 * @return Returns a new panel with a model of the specified class.
	 */	
	public static <T,P extends BPanel<T>> 
	P create(Class<P> panelType) {
		Class<T> modelType = getModelClass(panelType);
		T model = Factory.create(modelType);
		return create(panelType,model);
	}
	
	/**
	 * Creates a new BPanel of the specified type. <br/>
	 * 
	 * The panel is created using the constructor that takes the model as 
	 * argument. If no such constructor exists, then a RuntimeException is 
	 * thrown.
	 * 
	 * @param panelType 
	 *        Type of panel.
	 * @param model 
	 *        Model of the specified panel type.       
	 * @param <P>
	 *        Type of panel.
	 * @param <T> 
	 *        Type of model.       
	 * 
	 * 
	 * @return Returns a new panel with a model of the specified class.
	 */	
	public static <T,P extends BPanel<T>> 
	P create(Class<P> panelType, T model) {		
		Constructor<P> constructor = 
			ReflectionUtils.getConstructor(panelType, model.getClass());		
		if (constructor==null) {
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(
					"Class ", panelType.getName(),
					" does not have a public constructor that takes an",
					" argument of type ", model.getClass().getName());
			throw new RuntimeException(msg);
		}		
		P panel = ReflectionUtils.newInstance(constructor, model);		
		return panel;
	}
	
	/**
	 * Gets the model class of the specified panel class.
	 * 
	 * @param <T>
	 * @param <P>
	 * @param panelClass
	 * 
	 * @return Returns the model class of the specified panel type.
	 */
	@SuppressWarnings("unchecked")
	public static <T,P extends BPanel<T>>
	Class<T> getModelClass(Class<P> panelClass) {
		return (Class<T>) GenericsUtils.findTypeParameter
			(panelClass, BPanel.class, Object.class);
	}
	

	

}
