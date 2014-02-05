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
package gr.interamerican.bo2.arch.enums;

/**
 * {@link TargetEnvironment} refers to the environment the Bo2 application
 * is running with respect to the current application delivery stage. 
 */
public enum TargetEnvironment {
	
	/**
	 * The application is being developed and a developer has deployed
	 * some part or all of it, in order to test the changes he or she 
	 * performed on the code base.
	 */
	DEVELOPMENT,
	
	/**
	 * The application is being tested as part of a user acceptance
	 * testing phase.
	 */
	UAT,
	
	/**
	 * The application is in production.
	 */
	MIGRATION,
	
	/**
	 * The application is in production.
	 */
	PRDEV,
	
	/**
	 * The application is in production.
	 */
	PRODUCTION;

}
