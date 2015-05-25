package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import java.io.ByteArrayOutputStream;

/**
 * {@link ByteArrayOutputStream} with a constructor that takes
 * the byte array as an argument.
 */
class InitializedByteArrayOutputStream extends ByteArrayOutputStream {

	/**
	 * Creates a new InitializedByteArrayOutputStream.
	 * 
	 * @param bytes
	 *        Byte array that is set as the outputstream's buffer.
	 */
	public InitializedByteArrayOutputStream(byte[] bytes) {
		super(bytes.length);
		this.buf = bytes;
	}
	
	/**
	 * Creates a new InitializedByteArrayOutputStream.
	 * 
	 */
	public InitializedByteArrayOutputStream() {
		super();		
	}

}
