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
package gr.interamerican.bo2.odftoolkit.utils;

import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Properties;

import org.odftoolkit.simple.TextDocument;


/**
 * 
 */
@SuppressWarnings("nls")
public class ResourceUtils {
	/**
	 * When the application is run through EclEmma, then the build directory is
	 * different. The same happens when the project is part of the Bo2TestSuite.
	 * So we can't define the resources directory, relevant to the build path.
	 */
	private static String sourcePath;
	
	static {
		try {
			String pathToProperties = "/gr/interamerican/bo2/deployparms/deployment.properties";
			Properties properties = CollectionUtils.readProperties(pathToProperties);
			sourcePath = properties.getProperty("odfWorkDirectory");
			sourcePath=sourcePath.trim();
			if (!sourcePath.endsWith("/")) {
				sourcePath = sourcePath+"/";
			}
		} catch (RuntimeException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	//odfWorkDirectory
	
	/**
	 * ResourceUtilities is never instantiated. 
	 *
	 */
	private ResourceUtils() {
		/* empty */
	}
	
	/**
	 * Moves the path one level up.
	 * 
	 * @param path
	 * @return Returns the specified path moved one level up.
	 */
	static String oneLevelUp(String path) {		
		int i = path.length() - 1;			
		while (--i>=0 && path.charAt(i)!='/') {	/* empty */	}		
		if (i<0) {
			return ""; 
		}
		return path.substring(0,i+1);
	}
	
	/**
	 * Gets a File in the input resources folder with the specified name.
	 * 
	 * @param fileName
	 *        Filename.
	 *        
	 * @return Returns the File.
	 */
	public static String inputPath(String fileName) {					
		return sourcePath+"in/"+fileName;		
	}
	
	/**
	 * Gets a File in the output resources folder with the specified name.
	 * 
	 * @param fileName
	 *        Filename.
	 *        
	 * @return Returns the File.
	 */
	public static String outputPath(String fileName) {
		return sourcePath+"out/"+fileName;		
	}
	
	/**
	 * Copies the specified file from the input folder to the output
	 * and saves it as XML.
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public static void in2outAsXml(String filename) throws Exception {
		String inpath = ResourceUtils.inputPath(filename);
		String outpath = ResourceUtils.outputPath(filename);
		TextDocument template = TextDocument.loadDocument(inpath);
		template.save(outpath);
		OdfUtils.saveContentAsXml(template);
	}
	
	

}
