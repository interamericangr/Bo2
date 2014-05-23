package gr.interamerican.bo2.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilities for encryption, hashing etc.
 */
public class CryptUtils {
	
	/**
	 * MD5 MessageDigest
	 */
	private static MessageDigest MD5;
	
	/**
	 * Gets the md5 sum of an array (16 bytes, hex representation).
	 * The string is 32 chars length.
	 * 
	 * @param data
	 * @return md5 sum of array.
	 */
	public static String getMd5(byte[] data) {
		byte[] md5 = md5().digest(data);
		
		StringBuilder sb = new StringBuilder();
		for(byte b : md5) {
			sb.append(String.format("%02X", b)); //$NON-NLS-1$
		}
		
		return sb.toString();
	}
	
	/**
	 * @return Return MD5 {@link MessageDigest}.
	 */
	static synchronized MessageDigest md5() {
		if(MD5 != null) {
			return MD5;
		}
		try {
			MD5 = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return MD5;
	}
	/**
	 * Private empty constructor of a utility class. 
	 */
	private CryptUtils() { /* empty */ }

}
