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
package gr.interamerican.wicket.bo2.validation;

import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link BoPropertyDescriptorValidatorWrapper}.
 */
public class TestBoPropertyDescriptorValidatorWrapper {
	
	/**
	 * Indicates that validation failed.
	 */
	private boolean validationFailed = false;
	
	/**
	 * Test validate.
	 */
	@Test
	public void testValidate() {
		StringBoPropertyDescriptor sbpd = new StringBoPropertyDescriptor();
		sbpd.setMaxLength(2);
		sbpd.setMinLength(0);
		new BoPropertyDescriptorValidatorWrapper<String>(sbpd).validate(new SampleValidatable());
		Assert.assertTrue(validationFailed);
	}
	
	/**
	 * Sample {@link IValidatable}.
	 */
	private class SampleValidatable implements IValidatable<String> {

		public String getValue() {
			return "too long"; //$NON-NLS-1$
		}

		public void error(IValidationError error) {
			validationFailed = true;
		}

		public boolean isValid() {
			return false;
		}

		public IModel<String> getModel() {
			return new Model<String>(getValue());
		}
		
	}

}
