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
package gr.interamerican.bo2.utils.comparators;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.samples.bean.BeanWithNestedBean;

import java.io.Serializable;

import org.junit.Test;

/**
 * 
 */
public class TestPropertyBasedComparator {

   
	/**
     * tests Compare fields that are integers
     */
    @Test
	public void testCompare_Integers(){
    	
    	PropertyBasedComparator<SampleBean> intComparator = new PropertyBasedComparator<SampleBean>(SampleBean.class, "field2"); //$NON-NLS-1$
    	SampleBean bean1 = new SampleBean();
    	bean1.setField2(2);
		SampleBean bean2 = new SampleBean();
		bean2.setField2(3);
		assertEquals(-1,intComparator.compare(bean1, bean2));
	}
    
	/**
     * tests Compare fields that are strings
     */
    @Test
	public void testCompare_Strings(){
    	
    	PropertyBasedComparator<SampleBean> stringComparator = new PropertyBasedComparator<SampleBean>(SampleBean.class, "field1"); //$NON-NLS-1$
    	SampleBean bean1 = new SampleBean();
    	bean1.setField1("b"); //$NON-NLS-1$
		SampleBean bean2 = new SampleBean();
		bean2.setField1("a"); //$NON-NLS-1$
		assertEquals(1,stringComparator.compare(bean1, bean2));
	}
    
    
    /**
     * field that doesn't exist
     */
    @Test(expected=RuntimeException.class)
    public void testConstructor_throwningException(){
    	 new PropertyBasedComparator<SampleBean>(SampleBean.class, "field4"); //$NON-NLS-1$
    }
    
    /**
     * field that is not comparable
     */
    @Test (expected=RuntimeException.class)
    public void testConstructor_withException(){
    	 new PropertyBasedComparator<SampleBean>(SampleBean.class, "field3"); //$NON-NLS-1$
    }
    
    /**
     * Unit test for comparing based on a composite property.
     */
    @SuppressWarnings("nls")
    @Test
    public void testConstructorAndCompare_WithCompositeProperty() {
    	BeanWithNestedBean bean1 = new BeanWithNestedBean("a", 2);
    	BeanWithNestedBean bean2 = new BeanWithNestedBean("b", 1);
    	PropertyBasedComparator<BeanWithNestedBean> cmpField1 = 
    		new PropertyBasedComparator<BeanWithNestedBean>(BeanWithNestedBean.class, "nested.field1");
    	PropertyBasedComparator<BeanWithNestedBean> cmpField2 = 
    		new PropertyBasedComparator<BeanWithNestedBean>(BeanWithNestedBean.class, "nested.field2");
    	assertEquals(-1,cmpField1.compare(bean1, bean2));
    	assertEquals(1,cmpField2.compare(bean1, bean2));
    }
	
	/**
	 * sample bean to test
	 */
    @SuppressWarnings("unused")
	private class SampleBean implements Serializable {
    	
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * field1
		 */
		String field1;

		/**
		 * field 2
		 */
		Integer field2;

		/**
		 * field 3 
		 */
		Object field3;

		/**
		 * Gets the field1.
		 * 
		 * @return Returns the field1
		 */
		public String getField1() {
			return field1;
		}

		/**
		 * Assigns a new value to the field1.
		 * 
		 * @param field1
		 *            the field1 to set
		 */
		public void setField1(String field1) {
			this.field1 = field1;
		}

		/**
		 * Gets the field2.
		 * 
		 * @return Returns the field2
		 */
		
		public Integer getField2() {
			return field2;
		}

		/**
		 * Assigns a new value to the field2.
		 * 
		 * @param field2
		 *            the field2 to set
		 */
		public void setField2(Integer field2) {
			this.field2 = field2;
		}
		
		/**
		 * ���������� field3.
		 *
		 * @return field3
		 */
		public Object getField3() {
			return field3;
		}

		/**
		 * ��������� field3.
		 *
		 * @param field3 
		 */
		public void setField3(Object field3) {
			this.field3 = field3;
		}
	}
    
}
