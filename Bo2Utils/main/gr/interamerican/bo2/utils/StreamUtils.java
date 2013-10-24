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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Utilities about streams.
 */
public class StreamUtils {
	
	
	
	/**
	 * Reads a text file from a resource file in the local classpath
	 * and returns an array with the lines of the file.
	 * 
	 * @see #readResourceFile(String, boolean, boolean)
	 * 
	 * @param path Path to the file. 
	 * 
	 * @return Returns an array with names of mappings files.
	 *         If the resource file is not found, then returns null. 
	 * @throws IOException 
	 * @throws NullPointerException if the arg is null
	 */
	public static String[] readResourceFile(String path) 
	throws IOException {		
		return readResourceFile(path, false, false);
	}
	
	/**
	 * Reads a text file from a resource file in the local classpath
	 * and returns an array with the lines of the file.
	 * <br/>
	 * If the file cannot be found, returns null.
	 * <br/>
	 * The file is assumed to be encoded with the default Bo2 deployment
	 * <code>resourceFileEncoding</code> property.
	 * 
	 * @param path 
	 *        Path to the file. 
	 * @param excludeEmptyLines
	 *        If set to true, empty lines will be excluded. 
	 * @param excludeSharps 
	 *        If true, anything that follows a '#' in a line is ignored
	 * 
	 * @return Returns an array with names of mappings files.
	 *         If the resource file is not found, then returns null.
	 *          
	 * @throws IOException 
	 * @throws NullPointerException if the arg is null
	 */
	public static String[] readResourceFile(String path, boolean excludeEmptyLines, boolean excludeSharps) 
	throws IOException {		
		InputStream stream = StreamUtils.class.getResourceAsStream(path);
		if(stream==null){
			return null;
		}
		InputStreamReader insr = new InputStreamReader(stream, Bo2UtilsEnvironment.getDefaultResourceFileCharset());
		BufferedReader reader = new BufferedReader(insr);
		return StreamUtils.consumeBufferedReader(reader, excludeEmptyLines, excludeSharps);
	}
	
	/**
	 * Reads a text file from the filesystem and returns an array with the lines of the file.
	 * 
	 * @see #readFile(String, boolean, boolean)
	 * 
	 * @param path Path to the file. 
	 * 
	 * @return Returns an array with names of mappings files.
	 *         If the resource file is not found, then returns null. 
	 * @throws IOException 
	 */
	public static String[] readFile(String path) 
	throws IOException {
		return readFile(path, false, false);
	}
	
	/**
	 * Reads a text file from the filesystem and returns an array with the lines of the file.
	 * <br/>
	 * The file is assumed to be encoded with the default Bo2 deployment <code>textEncoding</code>
	 * 
	 * @see Bo2UtilsEnvironment
	 * 
	 * @param path 
	 *        Path to the file. 
	 * @param excludeEmptyLines 
	 *        If set to true, empty lines will be excluded. 
	 * @param excludeSharps 
	 *        If true, anything that follows a '#' in a line is ignored
	 *        
	 * @return Returns an array with names of mappings files.
	 *         If the resource file is not found, then returns null. 
	 * @throws IOException 
	 */
	public static String[] readFile(String path, boolean excludeEmptyLines, boolean excludeSharps) 
	throws IOException {
		try {
			File file = new File(path);
			InputStream stream = new FileInputStream(file);
			InputStreamReader insr = new InputStreamReader(stream, Bo2UtilsEnvironment.getDefaultTextCharset());
			BufferedReader reader = new BufferedReader(insr);
			return StreamUtils.consumeBufferedReader(reader, excludeEmptyLines, excludeSharps);
		} catch (FileNotFoundException fnfe) {
			return null;
		}
	}
	
	/**
	 * Gets a String from a resource file.
	 * 
	 * New line characters are replaced by spaces.
	 * 
	 * If the file is not found, a RuntimeException is thrown.
	 * 
	 * @param path
	 *        Path to the resource file.
	 *        
	 * @return Returns the string in the resource file.
	 */
	public static String getStringFromResourceFile(String path) {				
		String[] rows;
		try {
			rows = readResourceFile(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (rows==null) {
			@SuppressWarnings("nls")
			String msg = "Resource " + path + " not found";
			throw new RuntimeException(msg);
		}
		return StringUtils.array2String(rows, StringConstants.SPACE);
	}
	
	/**
	 * Gets the raw contents of a file as bytes.
	 * 
	 * @param file
	 * @return Returns a byte array that contains the file contents. 
	 */
	public static byte[] getFileContents(File file) {
		FileInputStream stream = null;
		try {
			long size = file.length();
			if (size>Integer.MAX_VALUE) {
				String msg = "The file is too large"; //$NON-NLS-1$
				throw new RuntimeException(msg);
			}
			int iSize = Long.valueOf(size).intValue();		
			stream = new FileInputStream(file);
			byte[] buffer = new byte[iSize];
			stream.read(buffer);
			return buffer;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Gets the contents of a file.
	 * 
	 * @param path
	 *        Path.
	 * @param inClassPath
	 *        If this is true, then the path must point to a resource file
	 *        in the class path, otherwise the path must point to a file.
	 * 
	 * @return Returns a byte array that contains the file contents. 
	 */
	public static byte[] getFileContents(String path, boolean inClassPath) {	
		return getFileContents(getFile(path, inClassPath));
	}
	
	/**
	 * Creates a File object for a path.
	 * 
	 * @param path
	 *        Path.
	 * @param inClassPath
	 *        If this is true, then the path must point to a resource file
	 *        in the class path, otherwise the path must point to a file.
	 * 
	 * @return Returns a File for the specified path.
	 */
	public static File getFile(String path, boolean inClassPath) {	
		try {
			if (inClassPath) {
				URL url = StreamUtils.class.getResource(path);
				URI uri;
				uri = url.toURI();
				return new File(uri);	
			} else {
				return new File(path);
			}
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * File to byte[]
	 * @param in 
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] inputStreamToByteArray(InputStream in) 
	throws IOException {		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[65536];
		int read = 0;
		while ( (read = in.read(buffer)) != -1 ) {
			out.write(buffer, 0, read);
		}
		byte[] bytes = out.toByteArray();
		in.close();
		out.close();
		return bytes;
	}
	
	/**
	 * �������� ��� ��� file ��� ������� ��� byte[].
	 * @param arr 
	 * @param file �� file ��� �� �����������.
	 * @throws IOException 
	 * 
	 */
	public static void saveToFile(byte[] arr, File file) throws IOException{
	  	FileOutputStream fos = new FileOutputStream(file);
	  	fos.write(arr);
        fos.flush();
        fos.close();		
	}
	
	/**
	 * Opens a resource file input stream. <br/>
	 * 
	 * @param path
	 *        path to the resource stream.
	 *        
	 * @return Returns the stream.
	 * 
	 * @throws IOException
	 *         If the resource path is not found.
	 *   
	 */
	public static InputStream getResourceStream(String path) 
	throws IOException {
		InputStream stream = StreamUtils.class.getResourceAsStream(path);
		if (stream == null) {			
			@SuppressWarnings("nls")
			String msg = "Resource " + path + " not found";
			throw new IOException(msg);			
		}
		return stream;
	}
	
	/**
	 * Opens an input stream from a filesystem file. <br/>
	 * 
	 * @param path
	 *        path to the resource stream.
	 *        
	 * @return Returns the stream.
	 * @throws IOException 
	 */
	public static InputStream getFileStream(String path) 
	throws IOException {
		FileInputStream stream = new FileInputStream(path);
		return stream;
	}
	
	/**
	 * Reads a text file from a path.
	 * 
	 * @param path path. 
	 * 
	 * @return Returns a string with the contents of the file.
	 * @throws IOException 
	 */
	public static String readTextFile(String path) 
	throws IOException {
		InputStream is = getFileStream(path);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		String text=StringConstants.EMPTY;
		String line;
		do {
			line = reader.readLine();
			if (line!=null) {
				text = text + line;
			}
		} while (line!=null);
		return text;
	}
	
	/**
	 * Reads a text file from a {@link BufferedReader}
	 * and returns an array with the lines of the file.
	 * The reader is close after use.
	 * 
	 * @param reader BufferedReader. 
	 * @param excludeEmptyLines If true, empty lines will be excluded.
	 * @param excludeSharps If true, anything that follows a '#' in a line is ignored
	 * 
	 * @return Returns an array with names of mappings files.
	 * @throws IOException 
	 */
	static String[] consumeBufferedReader(BufferedReader reader, boolean excludeEmptyLines, boolean excludeSharps) 
	throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		boolean eof = false;
		while (!eof) {
			String line = reader.readLine();
			if (line != null) {
				line = line.trim();
				if(excludeSharps) {
					int index = line.indexOf(StringConstants.SHARP);
					if(index>=0) {
						line = line.substring(0, index);
					}
				}
				
				if (excludeEmptyLines) {
					if (line.length()>0) {
						lines.add(line);
					}
				} 
				else {
					lines.add(line);
				}
				
			} else {
				eof = true;
			}
		}
		reader.close();
		return lines.toArray(new String[0]);
	}

}
