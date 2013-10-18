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
package gr.interamerican.bo2.samples.bean;

import gr.interamerican.bo2.samples.enums.Sex;
import gr.interamerican.bo2.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Samples {
	
	/**
	 * Sample family.
	 * 
	 * @return Returns a family with three children.
	 */
	@SuppressWarnings("nls")
	public static Family theAstaireFamily() {
		try {
			Person father = new Person();
			father.setFirstName("Fred");
			father.setLastName("Astaire");
			father.setSex(Sex.MALE);
			father.setBirthDay(DateUtils.getDate("15/06/1910"));
			String [] phones = {"bla","bla"};
			father.setPhones(phones);
			Person mother = new Person();
			mother.setFirstName("Ginger");
			mother.setLastName("Rodgers");
			mother.setBirthDay(DateUtils.getDate("11/03/1914"));
			mother.setSex(Sex.FEMALE);
			Person son1 = new Person();
			son1.setFirstName("Buck");
			son1.setLastName("Rodgers");		 
			son1.setBirthDay(DateUtils.getDate("11/03/1944"));
			son1.setSex(Sex.MALE);
			Person son2 = new Person();
			son2.setFirstName("Duck");
			son2.setLastName("Dodgers");
			son2.setBirthDay(DateUtils.getDate("11/03/1948"));
			son2.setSex(Sex.MALE);
			Person daughter = new Person();
			daughter.setFirstName("Jane");
			daughter.setLastName("Star");
			daughter.setBirthDay(DateUtils.getDate("11/03/1951"));
			daughter.setSex(Sex.FEMALE);
			List<Person> children = new ArrayList<Person>();
			children.add(son1);
			children.add(son2);
			children.add(daughter);		 
			Family family = new Family();
			family.setFather(father);
			family.setMother(mother);
			family.setChildren(children);
			family.setMariageDate(DateUtils.getDate("11/03/1941"));
			return family;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
