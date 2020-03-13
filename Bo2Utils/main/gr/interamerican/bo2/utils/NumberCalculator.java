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

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Calculator for {@link Number}s that is agnostic of the actual type.
 * 
 * @param <N>
 *            Type of Number
 */
public interface NumberCalculator<N extends Number> extends Serializable {

	/**
	 * Adds two numbers and returns their result
	 * 
	 * @param n1
	 *            First Number
	 * @param n2
	 *            Second Number
	 * @return Result
	 */
	N add(N n1, N n2);

	/**
	 * Returns an instance of N equals to the input integer.
	 * 
	 * @param n
	 *            Input
	 * @return Instance of N
	 */
	N get(int n);

	/**
	 * Implementation for {@link Integer}
	 */
	public static final NumberCalculator<Integer> INTEGER = new NumberCalculator<Integer>() {

		/**
		 * Version UID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Integer add(Integer n1, Integer n2) {
			return n1 + n2;
		}

		@Override
		public Integer get(int n) {
			return n;
		}
	};

	/**
	 * Implementation for {@link Long}
	 */
	public static final NumberCalculator<Long> LONG = new NumberCalculator<Long>() {

		/**
		 * Version UID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Long add(Long n1, Long n2) {
			return n1 + n2;
		}

		@Override
		public Long get(int n) {
			return (long) n;
		}
	};

	/**
	 * Implementation for {@link Short}
	 */
	public static final NumberCalculator<Short> SHORT = new NumberCalculator<Short>() {

		/**
		 * Version UID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Short add(Short n1, Short n2) {
			return (short) (n1 + n2);
		}

		@Override
		public Short get(int n) {
			return (short) n;
		}
	};

	/**
	 * Implementation for {@link Double}
	 */
	public static final NumberCalculator<Double> DOUBLE = new NumberCalculator<Double>() {

		/**
		 * Version UID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Double add(Double n1, Double n2) {
			return n1 + n2;
		}

		@Override
		public Double get(int n) {
			return (double) n;
		}
	};

	/**
	 * Implementation for {@link BigDecimal}
	 */
	public static final NumberCalculator<BigDecimal> BIGDECIMAL = new NumberCalculator<BigDecimal>() {

		/**
		 * Version UID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public BigDecimal add(BigDecimal n1, BigDecimal n2) {
			return n1.add(n2);
		}

		@Override
		public BigDecimal get(int n) {
			return new BigDecimal(n);
		}
	};
}