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
package gr.interamerican.bo2.samples.utils.meta;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.DateBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;

import java.util.Arrays;

/**
 * 
 */
public class ChildBeanDescriptor 
	extends BasicBusinessObjectDescriptor<ChildBean> {

		/**
		 * Creates a new Bean1descriptor object. 
		 *
		 */
		public ChildBeanDescriptor() {
			super();
			BoPropertyDescriptor<?>[] descriptors = {
					nameDescriptor(),
					descriptionDesccriptor(),
					birthDateDesccriptor()
			};
			this.setPropertyDescriptors(Arrays.asList(descriptors));
		}
		
		
		/**
		 * Description for field name.
		 * @return Description for the field.
		 */
		StringBoPropertyDescriptor nameDescriptor() {	
			StringBoPropertyDescriptor nameDesc = new StringBoPropertyDescriptor();
			nameDesc.setName("name"); //$NON-NLS-1$
			nameDesc.setDefaultValue(StringConstants.SPACE);
			nameDesc.setHasDefault(false);		
			nameDesc.setMinLength(3);
			nameDesc.setMaxLength(5);
			nameDesc.setNullAllowed(false);
			nameDesc.setReadOnly(false);
			nameDesc.setPackageName(ChildBean.class.getPackage().getName());
			nameDesc.setClassName(ChildBean.class.getSimpleName());
			return nameDesc;
		}
		
		/**
		 * Description for field description.
		 * @return Description for the field.
		 */
		StringBoPropertyDescriptor descriptionDesccriptor() {	
			StringBoPropertyDescriptor descriptionDesc = new StringBoPropertyDescriptor();
			descriptionDesc.setName("description"); //$NON-NLS-1$
			descriptionDesc.setDefaultValue(StringConstants.SPACE);
			descriptionDesc.setHasDefault(false);		
			descriptionDesc.setMinLength(2);
			descriptionDesc.setMaxLength(5);
			descriptionDesc.setNullAllowed(false);
			descriptionDesc.setReadOnly(false);
			descriptionDesc.setPackageName(ChildBean.class.getPackage().getName());
			descriptionDesc.setClassName(ChildBean.class.getSimpleName());
			return descriptionDesc;
		}
		

		/**
		 * Description for field birthDate.
		 * @return Description for the field.
		 */	
		DateBoPropertyDescriptor birthDateDesccriptor() {
			DateBoPropertyDescriptor birthDateDesc = new DateBoPropertyDescriptor();
			birthDateDesc.setName("birthdate"); //$NON-NLS-1$
			birthDateDesc.setHasDefault(false);
			birthDateDesc.setNullAllowed(false);
			birthDateDesc.setReadOnly(false);
			birthDateDesc.setPackageName(ChildBean.class.getPackage().getName());
			birthDateDesc.setClassName(ChildBean.class.getSimpleName());
			return birthDateDesc;
		}
}
