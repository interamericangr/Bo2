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



import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Utilities relevant with Numbers.
 */
public class NumberUtils {
	
	/**
	 * ThreadLocal for DecimalFormat thread safety.
	 */
	private static final ThreadLocal<DecimalFormat> DF_TL = new ThreadLocal<DecimalFormat>();
	
	/**
	 * @return Returns the default DecimalFormat for the current thread.
	 */
	private static DecimalFormat df() {
		if(DF_TL.get()==null) {
			DF_TL.set(getDefaultDecimalFormat());
		}
		return DF_TL.get();
	}
	
	/**
	 * @return Returns the default DecimalFormat for the default Locale.
	 *         No grouping is used on the integer part.
	 */
	private static DecimalFormat getDefaultDecimalFormat() {
		DecimalFormat df = new DecimalFormat();
		return df;
	}
	
	/**
	 * Converts a string to a double.
	 * 
	 * If the string can't be converted to double,
	 * then the function returns 0.
	 * 
	 * @param str String to convert to double
	 * 
	 * @return Returns the double value of s,
	 *         or 0 if s does not represent a double.
	 */
	public static double string2Double (String str) {
		try {
			return df().parse(str.trim()).doubleValue();
		} catch (ParseException e) {
			return 0;
		}
	}
	
    /**
     * Creates a BigDecimal from a formated number string.
     * 
     * @param s Number string
     *            
     * @return BigDecimal
     */
    public static java.math.BigDecimal string2BigDecimal(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);            
            if (c==' ' || c==df().getDecimalFormatSymbols().getGroupingSeparator()) {
            	//omit blank spaces and digit grouping separators
            } else if (c==df().getDecimalFormatSymbols().getDecimalSeparator()) {
            	sb.append('.'); //append comma
            } else {
            	sb.append(c); //append character
            }
        }
        return new java.math.BigDecimal(sb.toString());
    }
    
    /**
     * converts an string to an integer.
     * 
     * If the string does not represent a numeric
     * value, then the result will be 0.
     * 
     * @param s
     * @return returns an integer value for the string.
     */
    public static int string2Int(String s) {
        int i = 0;
        if (s == null)
            return 0;
        try {
            i = Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
        return i;
    }
    
    /**
     * converts an string to an long integer.
     * 
     * If the string does not represent a numeric
     * value, then the result will be 0.
     * 
     * @param s
     * @return returns a long value for the string.
     */
    public static long string2Long(String s) {
        long i = 0;
        if (s == null)
            return 0;
        try {
            i = Long.parseLong(s.trim());
        } catch (Exception e) {
            return 0;
        }
        return i;
    }

    /**
     * Rounds a number.
     * 
     * @param r Number to be rounded.
     * @param decimalPlaces Decimal places for rounding.
     * @param roundingMode Rounding mode.
     * 
     * @return Returns the rounded double.
     */
    private static double _round(double r, int decimalPlaces, int roundingMode) {
        BigDecimal bd = new BigDecimal(String.valueOf(r));
        bd = bd.setScale(decimalPlaces, roundingMode);
        return bd.doubleValue();
    }

    /**
     * Rounds a number to the closest value.
     * 
     * @param r Number to be rounded.
     * @param decimalPlaces Decimal places for rounding.
     * 
     * @return Returns the rounded double.
     */
    public static double round(double r, int decimalPlaces) {
        return _round(r, decimalPlaces, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * Rounds a number to the closest value.
     * 
     * @param r Number to be rounded.
     * @param decimalPlaces Decimal places for rounding.
     * 
     * @return Returns the rounded double.
     */
    public static BigDecimal round(BigDecimal r,int decimalPlaces) {
    	BigDecimal bd = r.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    /**
     * Rounds a number to the higher value.
     * 
     * @param r Number to be rounded.
     * @param decimalPlaces Decimal places for rounding.
     * 
     * @return Returns the rounded double.
     */
    public static double roundUp(double r, int decimalPlaces) {
        return _round(r, decimalPlaces, BigDecimal.ROUND_UP);
    }
    
	
    /**
	 * Δημιουργεί ένα java.lang.BigDecimal με παράμετρό ένα int αριθμό.
	 * 
	 * @param n	ο int αριθμός που θα μετατραπεί σε BigDecimal
	 * @return	ο BigDecimal που θα δημιουργηθεί  
	 */
	public static BigDecimal int2BigDecimal(int n) {
		String s = Integer.toString(n); 
		BigDecimal bd = new BigDecimal(s);
		return bd;
	}
    
    /**
     * Sums up an array of numbers.
     * 
     * The result is rounded to the count of decimal digits
     * specified. This is done in order to eliminate errors
     * caused by floating point arithmetic.
     * 
     * @param array Array of number to sum
     * @param decimalPlaces count of decimal places
     * 
     * @return Returns the sum of the array.
     */
    public static double sum(Number[] array, int decimalPlaces) {
        double sum = 0.0;
        for (int i=0; i<array.length; i++) {
            sum += array[i].doubleValue();
            sum = NumberUtils.round(sum, decimalPlaces);
        }
        return sum;
    }
    
    /**
     * Creates a formatted string from a number.
     * 
     * @param n number toformat.
     * @return Returns a formatted string for the parameter
     */
    public static String format(Number n) {
    	return df().format(n.doubleValue());
    }
    
    /**
     * Creates a formatted string from a number.
     * 
     * @param n number to format.
     * @param decimalDigits 
     * @return Returns a formatted string for the parameter
     */
    public static String format(Number n, int decimalDigits) {
    	DecimalFormat dfm = new DecimalFormat();
    	dfm.setMaximumFractionDigits(decimalDigits);    	
    	dfm.setMinimumFractionDigits(decimalDigits);
    	return dfm.format(n.doubleValue());
    }
    
    /**
     * Creates a formatted string from a number with a
     * specified number of minimum integer digits. 
     * The number is converted to a long. Precision may 
     * be lost, if the input has decimal digits.  
     * 
     * @param n number to format.
     * @param integerDigits 
     * @return Returns a formatted string for the parameter
     */
    public static String formatInt(Number n, int integerDigits) {
    	DecimalFormat dfm = new DecimalFormat();
    	dfm.setMinimumIntegerDigits(integerDigits);    	
    	return dfm.format(n.longValue());
    }
    
    /**
     * Shows if a string represents a number.
     * 
     * @param str string that is examined if it is numeric.
     * 
     *  
     * @return returns true if the parameter <code>str</code>
     *        represents a number. Otherwise returns false.true / false
     */
     public static boolean isNumeric(String str) {
    	 try {
 			df().parse(str.trim());
 			return true;
 		} catch (ParseException e) {
 			return false;
 		}  	
     } 
     
     /**
      * Parses a double.
      * 
      * @param str String to parse.
      * @return Returns the double value of the string. If the string is null, returns null.
      * @throws ParseException
      */
     public static Double parseDouble(String str) throws ParseException {
    	 if (str==null || str==StringConstants.NULL) {
    		 return null;
    	 }
    	 return df().parse(str.trim()).doubleValue();
     }
     
     /**
      * Parses a float.
      * 
      * @param str String to parse.
      * @return Returns the float value of the string. If the string is null, returns null.
      * @throws ParseException
      */
     public static Float parseFloat(String str) throws ParseException {
    	 if (str==null) {
    		 return null;
    	 }
    	 return df().parse(str.trim()).floatValue();
     }
     
    /**
     * Formats an integer adding zeroes as most significant digits if necessary
     * in order to return a string of a given length that is equal to the integer
     * when parsed. If the given integer's string representation requires more
     * chars than the given digits number it is returned without changing it.
     * 
     * @param n the integer
     * @param chars the number of chars for the wanted string representation
     *   
     * @return the integer as String
     */
    public static String formatInteger(Integer n, Integer chars) {
    	 if (n > Math.pow(10, chars)) {
    		 return n.toString();
    	 }
    	 String s = n.toString();
    	 while (s.length()<chars) {
    		s = StringConstants.ZERO + s; 
    	 }
    	 return s;
     }
    
    /**
     * Returns the largest numbers from a number of given numbers.
     * 
     * @param numbers
     * 
     * @return the largest one
     */
    public static Number max(Number...numbers){
    	if(numbers.length==0) {
    		throw new RuntimeException("Zero args supplied, cannot produce result."); //$NON-NLS-1$
    	}
    	if(numbers.length==1) {
    		return numbers[0];
    	}
    	Number maximum = numbers[0];
    	for (int i = 1; i < numbers.length; i++) {
    		if (numbers[i].doubleValue()>maximum.doubleValue()) {
    			maximum = numbers[i];
    		}
		}
    	return maximum;
    }
    
    /**
     * Creates a new BigDecimal
     * 
     * @param value
     *        Value of the big decimal.
     * @param scale
     *        Number of decimal digits.
     *        
     * @return Returns the new BigDecimal.
     */
    public static BigDecimal newBigDecimal(double value, int scale) {
    	DecimalFormat decimalFormat = new DecimalFormat();
    	if (scale==0) {
    		decimalFormat.setDecimalSeparatorAlwaysShown(false);
    	}
    	decimalFormat.setGroupingUsed(false);
    	decimalFormat.setMinimumFractionDigits(scale);
    	decimalFormat.setMaximumFractionDigits(scale);
    	
    	DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    	dfs.setDecimalSeparator('.');
    	decimalFormat.setDecimalFormatSymbols(dfs);
    	
    	String string = decimalFormat.format(value);
    	return new BigDecimal(string);
    }
    
    /**
     * Returns a random integer between a min and a max value (both inclusive).
     * 
     * @param min
     *        Minimum value
     * @param max
     *        Maximum value
     * @return Random integer a, with min&lt;=a&lt;=b
     */
    public static int randomInt(int min, int max) {
    	return min + (int)(Math.random() * ((max - min) + 1));
    }
    
    /**
     * Returns true, if the Number type can contain fractional digits.
     * This happens for Float, Double and BigDecimal.
     * 
     * @param number
     * @return True, if the Number type can contain fractional digits.
     */
    public static boolean isFractionalType(Number number) {
    	if(number==null) {
    		return false;
    	}
    	Class<?> clazz = number.getClass();
    	return clazz==Float.class || clazz==Double.class || clazz==BigDecimal.class;
	}

	/**
	 * Returns true if the argument Number is either null or zero.
	 * 
	 * @param number
	 *            Number to check
	 * @return True if it is Zero or Null
	 */
	public static boolean isNullOrZero(Number number) {
		if (number == null) {
			return true;
		}
		return number.doubleValue() == 0;
	}
}