package gr.interamerican.bo2.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Utilities for zip
 */
public class ZipUtils {
	
	/**
	 * Zips a directory that contains only files. If the dir contains
	 * folders as well, they are ignored
	 * 
	 * @param folderPath
	 *            Directory
	 * @param zipFilePath
	 *            Zip file path
	 *            
	 * @throws RuntimeException
	 *         If the supplied <code>folderPath</code> is not a directory
	 *         If an IOException occurs
	 */
	public static void zipFlatDir(String folderPath, String zipFilePath) {
		byte[] buffer = new byte[1024];
		FileOutputStream fos = null;
		ZipOutputStream zos = null;

		try {
			File folder = new File(folderPath);
			File zipFile = new File(zipFilePath);
			
			if (!folder.isDirectory()) {
				throw new RuntimeException("not a directory " + folderPath); //$NON-NLS-1$
			}

			fos = new FileOutputStream(zipFile);
			zos = new ZipOutputStream(fos);

			FileInputStream in = null;
			
			for (String fileName : folder.list()) {
				ZipEntry ze = new ZipEntry(fileName);
				zos.putNextEntry(ze);
				
				String filePath = folderPath + File.separator + fileName;
				File file = new File(filePath);
				if(file.isDirectory()) {
					continue;
				}
				
				try {
					in = new FileInputStream(file);
					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
				} finally {
					if(in != null) {
						in.close();
					}
				}
				zos.closeEntry();
			}
			
			zos.flush();
			zos.finish();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(fos!=null) {
					fos.close();
				}
				if(zos != null) {
					zos.close();
				}
			}catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
