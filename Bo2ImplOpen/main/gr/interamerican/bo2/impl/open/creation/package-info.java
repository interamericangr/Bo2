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
 * This package provides an approach to object creation
 * for Bo2 projects. <br/>
 *
 * According to Bo2 standards, all types of a system's bo layer
 * should be declared as interfaces. Objects should be created
 * by factories. The implementation of each interface should be 
 * mentioned only on the factory that creates it, there shouldn't 
 * be any other reference to the implementation class. As a principle
 * bo layer objects are known only as interfaces. They are created by
 * object factories. <br/>
 * The correlation between interface and implementing class is 
 * defined in a configuration file. The framework creates dynamically 
 * the factories according to the information defined in the configuration
 * files.
 * 
 * TODO: Check generics on persistence worker creation.
 *    
 */
 package gr.interamerican.bo2.impl.open.creation;

