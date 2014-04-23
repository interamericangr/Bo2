package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.utils.StringUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


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
<X extends Comparable<? super X>, Y extends Comparable<? super Y>, V>
implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
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
	 * Indicates if this area contains the point that is defined by the specified 
	 * x and y dimensions. 
	 * 
	 * @param xDimension
	 * @param yDimension
	 * 
	 * @return Returns true if the point belongs to this area, otherwise 
	 *         returns false.
	 */
	public boolean contains(X xDimension, Y yDimension) {
		SegmentedDistance<Y, V> ySegment = xSegments.getValue(xDimension);
		if (ySegment==null) {
			return false;
		}
		return ySegment.contains(yDimension);		
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
	public SortedSet<Range<X>> getSegmentsX() {
		return xSegments.getSegments();
	}
	
	/**
	 * Gets the ranges that segment the X distance.
	 * 
	 * @return Returns a sorted set that contains the ranges that segment the
	 *         X {@link SegmentedDistance}.
	 */	
	public SortedSet<Range<Y>> getSegmentsY() {
		SortedSet<Range<Y>> yRanges = new TreeSet<Range<Y>>();
		Set<SegmentedDistance<Y, V>> columns = xSegments.getValues();
		for (SegmentedDistance<Y, V> column : columns) {
			Set<Range<Y>> ranges = column.getSegments();
			yRanges.addAll(ranges);			
		}
		return yRanges;
	}
	
	/**
	 * Gets all values associated with segments in this area.
	 * 
	 * If a segment is associated with null, then null will be included 
	 * in the set of values.
	 * 
	 * @return Returns a set containing the values.
	 */	
	public Set<V> getValues() {
		Set<V> values = new HashSet<V>();
		Set<SegmentedDistance<Y, V>> columns = xSegments.getValues();
		for (SegmentedDistance<Y, V> column : columns) {
			Set<V> colValues = column.getValues();
			values.addAll(colValues);			
		}
		return values;
	}

}
