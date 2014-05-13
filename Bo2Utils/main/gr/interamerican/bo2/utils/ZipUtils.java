package gr.interamerican.bo2.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
	 * @return A stream with the bytes of the zip
	 *            
	 * @throws RuntimeException
	 *         If the supplied <code>folderPath</code> is not a directory
	 *         If an IOException occurs
	 */
	public static ByteArrayOutputStream zipFlatDir(String folderPath) {
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream baos = null;
		ZipOutputStream zos = null;

		try {
			File folder = new File(folderPath);
			
			if (!folder.isDirectory()) {
				throw new RuntimeException("not a directory " + folderPath); //$NON-NLS-1$
			}

			baos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(baos);

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
			
			return baos;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(zos != null) {
					zos.close();
				}
			}catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
