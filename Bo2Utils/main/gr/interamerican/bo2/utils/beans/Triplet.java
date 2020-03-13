package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.io.Serializable;

/**
 * A triplet of three objects, the left, the middle and the right object.
 * 
 * @param <L> Type of the left   object.
 * @param <M> Type of the middle object.
 * @param <R> Type of the right  object.
 */
public class Triplet<L, M, R> extends Pair<L,R> implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * creates a new triplet from the first three elements of an array.
	 *
	 * @param <T> Type of elements.
	 * @param array Array.
	 * @return Returns a triplet.
	 */
	public static <T> Triplet<T, T, T> triplet(T[] array){
		return new Triplet<T,T,T>(array[0], array[1], array[2]);
	}	
	
	/**
	 * middle Object.
	 */
	private M middle;
	
	/**
	 * Creates a new Triplet object.
	 */
	public Triplet(){
		super();
	}	
	
	/**
	 * Creates a new Triplet object.
	 *
	 * @param left the left
	 * @param middle the middle
	 * @param right the right
	 */
	public Triplet(L left,M middle, R right){
		super(left,right);
		this.middle = middle;
	}

	/**
	 * Gets the middle.
	 * 
	 * @return Returns the middle.
	 */
	public M getMiddle() {
		return middle;
	}

	/**
	 * Assigns a new value to the middle.
	 * 
	 * @param middle the middle to set
	 */
	public void setMiddle(M middle) {
		this.middle = middle;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (obj instanceof Triplet) {
			@SuppressWarnings("rawtypes") 
			Triplet that = (Triplet) obj;
			return Utils.equals(this.getLeft()  , that.getLeft())
				&& Utils.equals(this.getMiddle(), that.getMiddle())
				&& Utils.equals(this.getRight() , that.getRight());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		Object[] fields = {getLeft(),middle,getRight()};
		return Utils.generateHashCode(fields);
	}
	
	@Override @SuppressWarnings("nls")
	public String toString(){
		String l = StringUtils.toString(getLeft());
		String m = StringUtils.toString(middle);
		String r = StringUtils.toString(getRight());
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(l);
		sb.append(" , ");
		sb.append(m);
		sb.append(" , ");		
		sb.append(r);
		sb.append("]");
    	return  sb.toString(); 		
	}
	
}
