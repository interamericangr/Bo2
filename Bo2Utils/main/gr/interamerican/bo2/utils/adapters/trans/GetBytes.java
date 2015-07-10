package gr.interamerican.bo2.utils.adapters.trans;

import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Transforms an object to a byte array.
 * 
 * The transformation is based on the string representation
 * of the object.
 */
public class GetBytes 
implements Transformation<Object, byte[]> {
		
	@Override
	public byte[] execute(Object a) {
		if (a==null) {
			return new byte[0];
		}
		String string = a.toString();
		return string.getBytes();
	}

}
