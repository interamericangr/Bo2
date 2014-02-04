package gr.interamerican.bo2.utils.beans;

import java.util.Set;

import gr.interamerican.bo2.utils.StringUtils;


/**
 * {@link SegmentedArea} models an orthogonal area that is divided
 * into orthogonal segments by rows and columns.
 * 
 * 
 * @param <X>
 *        Type of X dimension. 
 * @param <Y>
 *        Type of Y dimension. 
 * @param <V> 
 *        Type of values. 
 */
public class SegmentedArea
<X extends Comparable<? super X>, Y extends Comparable<? super Y>, V> {
	
	/**
	 * Segments of X.
	 */
	SegmentedDistance<X, SegmentedDistance<Y, V>> xSegments = 
			new SegmentedDistance<X, SegmentedDistance<Y,V>>();
	
	/**
	 * Gets the value associated with the area that contains
	 * the specified x and y dimensions.
	 * 
	 * @param xDimension
	 * @param yDimension
	 * 
	 * @return Returns the value associated with the area that contains
	 *         the specified x and y dimensions, or null if their is no
	 *         area containing the specified dimensions.
	 */
	public V getValue(X xDimension, Y yDimension) {
		SegmentedDistance<Y, V> ySegment = xSegments.getValue(xDimension);
		if (ySegment==null) {
			return null;
		}
		return ySegment.getValue(yDimension);
		
		
	}
	
	/**
	 * Defines a new segment of the area, that is defined by the specified
	 * limits in the x and y dimensions.
	 * 
	 * @param xLeft
	 *        Left limit in the x axis.
	 * @param xRight
	 *        Right limit in the x axis.
	 * @param yLeft
	 *        Left limit in the y axis.
	 * @param yRight
	 *        Right limit in the y axis. 
	 * @param value
	 *        Value associated with the area defined by the specified limits.
	 *        
	 */
	public void setValue(X xLeft, X xRight, Y yLeft, Y yRight, V value) {
		SegmentedDistance<Y, V> ySegment = xSegments.getValue(xLeft);
		if (ySegment==null) {
			ySegment = new SegmentedDistance<Y, V>();
			xSegments.setValue(xLeft, xRight, ySegment);					
		} else {
			Range<X> range = xSegments.getRange(xLeft);
			Range<X> newRange = new Range<X>(xLeft, xRight);
			if (!newRange.equals(range)) {
				@SuppressWarnings("nls")
				String msg = StringUtils.concat
					("Range ", range.toString()," overlaps with other ranges");
				throw new RuntimeException(msg);
			}
			
		}
		ySegment.setValue(yLeft, yRight, value);					
	}
	
	/**
	 * Gets the ranges that segment the X distance.
	 * 
	 * @return Returns a sorted set that contains the ranges that segment the
	 *         X {@link SegmentedDistance}.
	 */	
	public Set<Range<X>> getSegmentsX() {
		return xSegments.getSegments();
	}
	
	/**
	 * Gets the ranges that segment the X distance.
	 * 
	 * @return Returns a sorted set that contains the ranges that segment the
	 *         X {@link SegmentedDistance}.
	 */	
	public Set<Range<X>> getSegmentsY() {
		
		
		return null;
	}

}
