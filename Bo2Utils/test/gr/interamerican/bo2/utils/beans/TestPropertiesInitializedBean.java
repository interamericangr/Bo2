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
package gr.interamerican.bo2.utils.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;

/**
 * Unit test for {@link PropertiesInitializedBean}.
 */
public class TestPropertiesInitializedBean {
	
	/**
	 * Tests most cases.
	 */
	@Test
	public void test1() {
		String path = "/gr/interamerican/bo2/utils/beans/bean1.properties"; //$NON-NLS-1$		
		Properties prop = CollectionUtils.readProperties(path);
		Bean1 bean = new Bean1(prop);
		assertEquals(0, bean.getIntField());
		assertEquals("string", bean.getStringField()); //$NON-NLS-1$
		assertEquals(new Double(3.0), bean.getDoubleField1());
		assertTrue(bean.getBooleanField2());
		assertFalse(bean.isBooleanField());
		Date expected = DateUtils.getDate(2010, Calendar.DECEMBER, 12);
		assertEquals(expected, bean.getDateField1());
		assertEquals(new Integer(2), bean.getIntegerField());
		assertEquals(new Long(2), bean.getLongField());
		assertEquals(new Character('S'), bean.getCharacterField());
		assertEquals('\0', bean.getCharField());
		assertNull(bean.getDateFieldNull());
		assertNull(bean.getStringFieldNull());
		assertEquals(new Float(3.0), bean.getFloatField1());
		assertEquals(new Short((short) 1), bean.getShortField1());
		assertEquals(new Byte ((byte) 13), bean.getByteField1());
		assertNull(bean.getCharacterFieldNull());
		assertTrue(bean.getBooleanField3());
	}
	
	/**
	 * 
	 */
	@Test(expected = RuntimeException.class)
	public void testGetDate_WithException() {
		String path = "/gr/interamerican/bo2/utils/beans/bean2.properties"; //$NON-NLS-1$		
		Properties prop = CollectionUtils.readProperties(path);
		Bean2 bean = new Bean2(prop);
		bean.getDateField2();
	}
	
	
	
	/**
	 * Sample class for test.
	 */
	@SuppressWarnings("unused")
	private static class Bean1 
	extends PropertiesInitializedBean {
		/**
		 * int.
		 */
		private int intField;
		/**
		 * string.
		 */
		private String stringField;
		/**
		 * double.
		 */
		private Double doubleField1;
		/**
		 * float.
		 */
		private Float floatField1;

		/**
		 * boolean.
		 */
		private boolean booleanField;
		/**
		 * date.
		 */
		private Date dateField1;



		/**
		 * Integer.
		 */
		private Integer integerField;
		/**
		 * Long.
		 */
		private Long longField;
		/**
		 * Character.
		 */
		private Character characterField;
		/**
		 * Character.
		 */
		private Character characterFieldNull;
		/**
		 * char.
		 */
		private char charField;
		/**
		 * Boolean.
		 */
		private Boolean booleanField2;
		/**
		 * Boolean.
		 */
		private Boolean booleanField3;
		/**
		 * Boolean.
		 */
		private Boolean booleanField4;

		/**
		 * Date.
		 */
		private Date dateFieldNull;
		/**
		 * String.
		 */
		private String stringFieldNull;
		/**
		 * String.
		 */
		private Short shortField1;
		/**
		 * String.
		 */
		private Byte byteField1;
		
		/**
		 * Creates a new Bean1 object. 
		 *
		 * @param properties
		 */
		public Bean1(Properties properties) {
			super(properties);
		}

		/**
		 * Gets the intField.
		 *
		 * @return Returns the intField
		 */
		public int getIntField() {
			return intField;
		}

		/**
		 * Assigns a new value to the intField.
		 *
		 * @param intField the intField to set
		 */
		public void setIntField(int intField) {
			this.intField = intField;
		}

		/**
		 * Gets the stringField.
		 *
		 * @return Returns the stringField
		 */
		public String getStringField() {
			return stringField;
		}

		/**
		 * Assigns a new value to the stringField.
		 *
		 * @param stringField the stringField to set
		 */
		public void setStringField(String stringField) {
			this.stringField = stringField;
		}

		/**
		 * Gets the doubleField1.
		 *
		 * @return Returns the doubleField1
		 */
		public Double getDoubleField1() {
			return doubleField1;
		}

		/**
		 * Assigns a new value to the doubleField1.
		 *
		 * @param doubleField1 the doubleField1 to set
		 */
		public void setDoubleField1(Double doubleField1) {
			this.doubleField1 = doubleField1;
		}

		/**
		 * Gets the booleanField.
		 *
		 * @return Returns the booleanField
		 */
		public boolean isBooleanField() {
			return booleanField;
		}

		/**
		 * Assigns a new value to the booleanField.
		 *
		 * @param booleanField the booleanField to set
		 */
		public void setBooleanField(boolean booleanField) {
			this.booleanField = booleanField;
		}

		/**
		 * Gets the dateField1.
		 *
		 * @return Returns the dateField1
		 */
		public Date getDateField1() {
			return dateField1;
		}

		/**
		 * Assigns a new value to the dateField1.
		 *
		 * @param dateField1 the dateField1 to set
		 */
		public void setDateField1(Date dateField1) {
			this.dateField1 = dateField1;
		}

		/**
		 * Gets the integerField.
		 *
		 * @return Returns the integerField
		 */
		public Integer getIntegerField() {
			return integerField;
		}

		/**
		 * Assigns a new value to the integerField.
		 *
		 * @param integerField the integerField to set
		 */
		public void setIntegerField(Integer integerField) {
			this.integerField = integerField;
		}

		/**
		 * Gets the longField.
		 *
		 * @return Returns the longField
		 */
		public Long getLongField() {
			return longField;
		}

		/**
		 * Assigns a new value to the longField.
		 *
		 * @param longField the longField to set
		 */
		public void setLongField(Long longField) {
			this.longField = longField;
		}

		/**
		 * Gets the characterField.
		 *
		 * @return Returns the characterField
		 */
		public Character getCharacterField() {
			return characterField;
		}

		/**
		 * Assigns a new value to the characterField.
		 *
		 * @param characterField the characterField to set
		 */		
		public void setCharacterField(Character characterField) {
			this.characterField = characterField;
		}

		/**
		 * Gets the charField.
		 *
		 * @return Returns the charField
		 */
		public char getCharField() {
			return charField;
		}

		/**
		 * Assigns a new value to the charField.
		 *
		 * @param charField the charField to set
		 */
		public void setCharField(char charField) {
			this.charField = charField;
		}

		/**
		 * Gets the booleanField2.
		 *
		 * @return Returns the booleanField2
		 */
		public Boolean getBooleanField2() {
			return booleanField2;
		}

		/**
		 * Assigns a new value to the booleanField2.
		 *
		 * @param booleanField2 the booleanField2 to set
		 */
		public void setBooleanField2(Boolean booleanField2) {
			this.booleanField2 = booleanField2;
		}

		/**
		 * Gets the dateFieldNull.
		 *
		 * @return Returns the dateFieldNull
		 */
		public Date getDateFieldNull() {
			return dateFieldNull;
		}

		/**
		 * Assigns a new value to the dateFieldNull.
		 *
		 * @param dateFieldNull the dateFieldNull to set
		 */
		public void setDateFieldNull(Date dateFieldNull) {
			this.dateFieldNull = dateFieldNull;
		}

		/**
		 * Gets the stringFieldNull.
		 *
		 * @return Returns the stringFieldNull
		 */
		public String getStringFieldNull() {
			return stringFieldNull;
		}

		/**
		 * Assigns a new value to the stringFieldNull.
		 *
		 * @param stringFieldNull the stringFieldNull to set
		 */
		public void setStringFieldNull(String stringFieldNull) {
			this.stringFieldNull = stringFieldNull;
		}
		/**
		 * ���������� floatField1.
		 *
		 * @return floatField1
		 */
		
		public Float getFloatField1() {
			return floatField1;
		}

		/**
		 * ��������� floatField1.
		 *
		 * @param floatField1 
		 */
		public void setFloatField1(Float floatField1) {
			this.floatField1 = floatField1;
		}
		
		
		/**
		 * ���������� shortField1.
		 *
		 * @return shortField1
		 */
		
		public Short getShortField1() {
			return shortField1;
		}

		/**
		 * ��������� shortField1.
		 *
		 * @param shortField1 
		 */
		public void setShortField1(Short shortField1) {
			this.shortField1 = shortField1;
		}
		
		/**
		 * ���������� byteField1.
		 *
		 * @return byteField1
		 */
		
		public Byte getByteField1() {
			return byteField1;
		}

		/**
		 * ��������� byteField1.
		 *
		 * @param byteField1 
		 */
		public void setByteField1(Byte byteField1) {
			this.byteField1 = byteField1;
		}
		
		/**
		 * ���������� characterFieldNull.
		 *
		 * @return characterFieldNull
		 */
		
		public Character getCharacterFieldNull() {
			return characterFieldNull;
		}

		/**
		 * ��������� characterFieldNull.
		 *
		 * @param characterFieldNull 
		 */
		public void setCharacterFieldNull(Character characterFieldNull) {
			this.characterFieldNull = characterFieldNull;
		}
		
		/**
		 * ���������� booleanField3.
		 *
		 * @return booleanField3
		 */
		
		public Boolean getBooleanField3() {
			return booleanField3;
		}
		/**
		 * ��������� booleanField3.
		 *
		 * @param booleanField3 
		 */
		public void setBooleanField3(Boolean booleanField3) {
			this.booleanField3 = booleanField3;
		}
		
		/**
		 * ���������� booleanField4.
		 *
		 * @return booleanField4
		 */
		
		public Boolean getBooleanField4() {
			return booleanField4;
		}

		/**
		 * ��������� booleanField4.
		 *
		 * @param booleanField4 
		 */
		public void setBooleanField4(Boolean booleanField4) {
			this.booleanField4 = booleanField4;
		}
		
	}
	
	
	/**
	 * Sample class for test.
	 */
	@SuppressWarnings("unused")
	private static class Bean2 
	extends PropertiesInitializedBean {

		/**
		 * date.
		 */
		private Date dateField2;
		
		/**
		 * ���������� dateField2.
		 *
		 * @return dateField2
		 */
		
		public Date getDateField2() {
			return dateField2;
		}

		/**
		 * ��������� dateField2.
		 *
		 * @param dateField2 
		 */
		public void setDateField2(Date dateField2) {
			this.dateField2 = dateField2;
		}
		/**
		 * Creates a new Bean2 object. 
		 *
		 * @param properties
		 */
		public Bean2(Properties properties) {
			super(properties);
		}
		
	}
	
	
	

}
