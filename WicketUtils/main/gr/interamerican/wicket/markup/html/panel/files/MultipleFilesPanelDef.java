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
package gr.interamerican.wicket.markup.html.panel.files;

import java.util.List;

import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

/**
 * {@link ServicePanelDef} of {@link MultipleFilesPanel}.
 */
public interface MultipleFilesPanelDef extends ServicePanelDef {

	/**
	 * [REQUIRED]<br>
	 * Gets the fileDefinitions.
	 *
	 * @return Returns the fileDefinitions
	 */
	List<FileDefinition> getFileDefinitions();

	/**
	 * Assigns a new value to the fileDefinitions. For each file definition a
	 * file input component is created.
	 *
	 * @param fileDefinitions
	 *            the fileDefinitions to set
	 */
	void setFileDefinitions(List<FileDefinition> fileDefinitions);
}