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
/**
 * Deployment and other resources. <br/>  
 * 
 * The following resources can be found  in this package:
 * <table>
 * <tr><td> hibernate.cfg.xml </td>
 *     <td> Hibernate mappings. </td></tr>
 * <tr><td> types.txt </td>
 *     <td> Contains a list of mappings files. Each mapping file, maps an interface
 *          to a class that implements it. This mechanism of type mappings is the 
 *          first mechanism used by object factories to find the class implementing
 *          an interface. The second mechanism used is NameResolvers. </td> </tr>
 * <tr><td> pwTypes.txt </td>
 *     <td> Contains a list of mappings files for persistence workers. Each mapping 
 *          file, maps a {@link gr.interamerican.bo2.arch.PersistentObject} interface
 *          to a class implementing the {@link gr.interamerican.bo2.arch.PersistenceWorker}
 *          interface for that specific persistent object. This is the first mechanism 
 *          used by object factories in order to create a PersistenceWorker for a 
 *          PersistentObject class. If this mechanism fails, then the factory will try
 *          to create a hibernate based persistence worker for this class. </td></tr>
 * <tr><td> jdbcPw.properties </td>
 *     <td> Maps PersistentObjects to JDBC based implementation of PersistenceWorker.  </td></tr> 
 * <tr><td> mappings.properties </td>
 *     <td> Maps interfaces to implementations  </td></tr> 
 * <tr><td> types1.properties </td>
 *     <td> Maps interfaces to implementations  </td></tr> 
 * <tr><td> types2.properties </td>
 *     <td> Maps interfaces to implementations  </td></tr> 
 * <tr><td> replacements.properties </td>
 *     <td> Maps interfaces to other interfaces. Factories can substitute interfaces 
 *          with other interfaces extending them before trying to find their 
 *          implementation class. This file supports this functionality </td></tr> 
 * <tr><td> FileForTestResourcesManager.txt </td>
 *     <td> Contains the names of two classes that are loaded during a test of
 *          the class {@link gr.interamerican.bo2.impl.open.runtime.ResourcesManagerFactory}. </td></tr>           
 * <br/>
 * The default object factories to which creation is delegated by the 
 * {@link gr.interamerican.bo2.impl.open.creation.Factory} class, are initialized by the 
 * files types.txt and pwTypes.txt that can be found at the package 
 * <code>gr.interamerican.bo2.deployparms</code>. The files in this package are used for 
 * testing alternative initializations of object factories. 
 
 */
 package gr.interamerican.rsrc;

