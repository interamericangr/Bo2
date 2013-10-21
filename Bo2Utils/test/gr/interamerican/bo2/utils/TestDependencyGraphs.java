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
package gr.interamerican.bo2.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link DependencyGraphs}
 */
public class TestDependencyGraphs {
	
	Integer[][] unsolvable1 = new Integer[][]
			{{1},
			 {2},
			 {3},
			 {4},
			 {0}};
	
	Integer[][] unsolvable2 = new Integer[][]
			{{},
			 {4, 2},
			 {3},
			 {4, 2},
			 {0}};
	
	Integer[][] solvable1 = new Integer[][]
			{{1},
			 {2},
			 {3},
			 {4},
			 {}};
	
	Integer[][] solvable2 = new Integer[][]
			{{},
			 {0},
			 {1},
			 {2},
			 {3}};
	
	Integer[][] solvable3 = new Integer[][]
			{{1,2,3,4},
			 {5},
			 {1,4},
			 {},
			 {},
			 {}};
	
	@Test
	public void testIsSolvable() {
		Assert.assertFalse(DependencyGraphs.get().isSolvable(getInput(unsolvable1)));
		Assert.assertFalse(DependencyGraphs.get().isSolvable(getInput(unsolvable2)));
		Assert.assertTrue(DependencyGraphs.get().isSolvable(getInput(solvable1)));
		Assert.assertTrue(DependencyGraphs.get().isSolvable(getInput(solvable2)));
		Assert.assertTrue(DependencyGraphs.get().isSolvable(getInput(solvable3)));
	}
	
	@Test
	public void testOderedConsideringDependencies() {
		List<Integer> list = DependencyGraphs.get().getNodesOrderWithDependencies(getInput(solvable1));
		Assert.assertTrue(new HashSet<Integer>(list).size()==5);
		Assert.assertEquals(list.get(0), new Integer(4));
		list = DependencyGraphs.get().getNodesOrderWithDependencies(getInput(solvable2));
		Assert.assertTrue(new HashSet<Integer>(list).size()==5);
		Assert.assertEquals(list.get(0), new Integer(0));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testOderedConsideringDependencies_ex() {
		DependencyGraphs.get().getNodesOrderWithDependencies(getInput(unsolvable1));
	}
	
	static Map<Integer, Set<Integer>> getInput(Integer[][] input) {
		Map<Integer, Set<Integer>> result = new HashMap<Integer, Set<Integer>>();
		for(int row=0; row<input.length; row++) {
			Set<Integer> rowSet = new HashSet<Integer>();
			for(Integer element : input[row]) {
				rowSet.add(element);
			}
			result.put(row, rowSet);
		}
		return result;
	}
	
}
