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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class with utilities for arrays.
 */
public class ArrayUtils {
	

    /**
	 * Hidden constructor.
	 * 
	 * This is a utility class having only static methods.
	 * There is no need to create any instance of this class.
	 */
    private ArrayUtils() {
    	/* empty */
    }
    
    /**
     * Ensures that an array has a specified capacity.
     * 
     * If the array's size exceeds the specified capacity, then the same array
     * is returned. If the array has less size than the specified capacity, a
     * copy of the input array with the specified capacity is returned.
     * The input array must not be null.
     *  
     * @param array Input array.
     * @param capacity Required size of array.
     * @param <T> array type.
     * 
     * @return Returns an array with at least the required capacity.
     */
    public static <T> T[] ensureCapacity(T[] array, int capacity) {    	
		if (array.length < capacity) {
			return ArrayUtils.copyOf(array, capacity);
		} 
		return array;
    }
    
    /**
     * Enforces that an array has a specified capacity.
     * 
     * If the array has exactly the specified capacity, then the same array
     * is returned. If the array has less or more size than the specified 
     * capacity, then a copy of the input array with the specified capacity 
     * is returned. 
     * The input array must not be null.
     *  
     * @param array Input array.
     * @param capacity Required size of array.
     * @param <T> array type.
     * 
     * @return Returns an array with exactly the required capacity.
     */
    public static <T> T[] enforceCapacity(T[] array, int capacity) {
		if (array.length != capacity) {
			return ArrayUtils.copyOf(array, capacity);
		}
		return array;
    }
    
    /**
     * Checks if an array contains any null.
     * 
     * @param array Array checked.
     * 
     * @return Returns true if the array contains any null.
     */
    public static boolean containsNull(Object[] array) {    	
    	return getFirstNull(array)!=null;
    }
    
    /**
     * Gets the index of the first null element of an array.
     * 
     * The array does not need to be sorted.
     * 
     * @param array Array checked.
     * 
     * @return Returns the index of the first null element of 
     *         the array. If the array does not contain any null 
     *         element, then returns null.
     */
    public static Integer getFirstNull(Object[] array) {    	
    	for (int i = 0; i < array.length; i++) {
			if (array[i]==null) {
				return i;
			}
		} 
    	return null;
    }
    
    /**
     * Checks to make sure that the array contains a not null element
     * in a specified index.
     * 
     * @param array array checked
     * @param index Index searched.
     * 
     * @return Returns true if the array contains a not null element
     *         in the specified position. If the specified index contains
     *         a null, or if the array has less elements than the index,
     *         then returns false.
     */
    public static boolean containsNotNull(Object[] array, int index) {
		try {
			return array[index] != null;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
    }
    
    /**
     * Checks whether an array contains a given object.
     * 
     * @param array
     * @param sample
     * 
     * @return true if the sample is contained in the array.
     */
    public static boolean contains(Object[] array, Object sample) {
    	for (Object element : array) {
    		if(element != null && element.equals(sample)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Copies the specified array, truncating or padding with nulls (if necessary)
     * so the copy has the specified length.  For all indices that are
     * valid in both the original array and the copy, the two arrays will
     * contain identical values.  For any indices that are valid in the
     * copy but not the original, the copy will contain <tt>null</tt>.
     * Such indices will exist if and only if the specified length
     * is greater than that of the original array.
     * The resulting array is of exactly the same class as the original array.
     * <br/>
     * This method is a copy from the class java.util.Arrays from JDK 1.6.
     * 
     * @param <T> 
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with nulls
     *     to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     * 
     */
    @SuppressWarnings("unchecked")
	public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    }
    
    /**
     * Copies the specified array, truncating or padding with nulls (if necessary)
     * so the copy has the specified length.
     * 
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with nulls
     *         to obtain the specified length
     */
    public static byte[] copyOf(byte[] original, int newLength) {
    	int length = Math.min(original.length, newLength);
    	byte[] newArray = new byte[length];
    	System.arraycopy(original, 0, newArray, 0, length);
    	return newArray;
    }

    /**
     * Copies the specified array, truncating or padding with nulls (if necessary)
     * so the copy has the specified length.  For all indices that are
     * valid in both the original array and the copy, the two arrays will
     * contain identical values.  For any indices that are valid in the
     * copy but not the original, the copy will contain <tt>null</tt>.
     * Such indices will exist if and only if the specified length
     * is greater than that of the original array.
     * The resulting array is of the class <tt>newType</tt>.
     * <br/>
     * This method is a copy from the class java.util.Arrays from JDK 1.6.
     * 
     * @param <T> 
     * @param <U> 
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @param newType the class of the copy to be returned
     * @return a copy of the original array, truncated or padded with nulls
     *     to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     * @throws ArrayStoreException if an element copied from
     *     <tt>original</tt> is not of a runtime type that can be stored in
     *     an array of class <tt>newType</tt>
     * 
     */
    private static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        @SuppressWarnings("unchecked")
		T[] copy = ((Object)newType == (Object)Object[].class)
            ? (T[]) new Object[newLength]
            : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        return copy;
    }
    
	
    /**
     * Transforms an array of objects to a list of object arrays,
     * with each array containing one element of the input array.
     * 
     * This method is useful for testing with multiple parmaters.
     * 
     * @param array Array.
     *  
     * @return Returns a list of one element arrays.
     */
	public static List<Object[]> arrayAsListOfArrays(Object[] array) {
		List<Object[]> list = new ArrayList<Object[]>();
		for (int i = 0; i < array.length; i++) {
			Object[] row = new Object[]{array[i]};
			list.add(row);
		}
		return list;
	 }
	
	/**
	 * Removes nulls from an array.
	 * 
	 * @param array Array from which the nulls are removed.
	 * @param <T> Type of objects in the array.
	 * 
	 * @return Returns an array with the same elements as the input
	 *         array, but without nulls. If the initial array contained
	 *         any null then the new array is packed, eliminating nulls.
	 */
	public static <T> T[] removeNulls(T[] array) {
		List<T> list = removeNulls(Arrays.asList(array));
		T[] empty = ArrayUtils.copyOf(array, 0);
		return list.toArray(empty);
	}
	
	/**
	 * Removes nulls from a list.
	 * 
	 * @param list 
	 *        List from which the nulls are removed.
	 * @param <T> 
	 *        Type of objects in the list.
	 * 
	 * @return Returns a list that does not contain any null.
	 */
	public static <T> List<T> removeNulls(List<T> list) {
		ArrayList<T> out = new ArrayList<T>();
		for (T t : list) {
			if (t!=null) {
				out.add(t);
			}
		}
		return out;
	}
	
	/**
	 * Checks if the input array is null or empty.
	 * 
	 * @param <T>
	 *        Type of array element.
	 *        
	 * @param array
	 *        Input array.
	 *        
	 * @return True, if input is null or empty array.
	 */
	public static <T> boolean isNullOrEmpty(T[] array) {
		if(array==null || array.length==0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets an element from the given array that corresponds to the given
	 * index, taking into account bad input. The result is the equivalent
	 * of array[index] if the index is in bounds and the array is not null.
	 * 
	 * @param <T>
	 *        elements type.
	 *        
	 * @param array
	 * @param index
	 * 
	 * @return array[index], or null for bad input.
	 */
	public static <T> T safeGet(T[] array, int index) {
		if(isNullOrEmpty(array)) {
			return null;
		}
		if(index<0 || index>array.length-1) {
			return null;
		}
		return array[index];
	}
	

	/**
	 * Compares two arrays for equality.
	 * 
	 * @param <T>
	 * @param array1
	 * @param array2
	 * 
	 * @return Returns true if the two arrays have the same elements
	 *         one by one.
	 */
	public static <T> boolean equals(T[] array1, T[] array2) {
		if (array1==array2) {
			return true;
		}
		if (array1==null || array2==null) {
			return false;
		}
		if (array1.length!=array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (!Utils.equals(array1[i], array2[i])) {
				return false;
			}
		}
		return true;
	}
	
	

}
