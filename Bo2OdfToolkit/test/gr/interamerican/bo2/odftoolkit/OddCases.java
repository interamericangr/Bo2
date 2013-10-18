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
package gr.interamerican.bo2.odftoolkit;

import gr.interamerican.bo2.odftoolkit.utils.OdfUtils;
import gr.interamerican.bo2.utils.NumberUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.odftoolkit.odfdom.dom.element.draw.DrawFrameElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawImageElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;

/**
 * 
 */
public class OddCases {

	/**
	 * dpi
	 */
	static double DPI = 400.0;
	/**
	 * inche
	 */
	static double INCH = 2.54;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		/*
		 * attempt to calculate dynamically image size for 400dpi TODO: decide about the image dimensions method
		 */
		BufferedImage awt = ImageIO.read(new File("/tmp/test.jpg")); //$NON-NLS-1$
		int heightPx = awt.getHeight();
		int widthPx = awt.getWidth();
		double heightCm = INCH * heightPx / DPI;
		double widthCm = INCH * widthPx / DPI;
		@SuppressWarnings("unused")
		String cellHeight = NumberUtils.format(heightCm, 3) + "cm"; //$NON-NLS-1$
		@SuppressWarnings("unused")
		String cellWidth = NumberUtils.format(widthCm, 3) + "cm"; //$NON-NLS-1$
		/*
		 * Read from FS is just for testing, any InputStream will do.
		 */
		FileInputStream fis = new FileInputStream(new File("/tmp/test.jpg")); //$NON-NLS-1$
		TextDocument odt = TextDocument.newTextDocument();
		/*
		 * Insert the image to the odt package
		 */
		OdfPackage pkg = odt.getPackage();
		pkg.insert(fis, "Pictures/test.jpg", "image/jpeg"); //$NON-NLS-1$ //$NON-NLS-2$
		fis.close();
		/*
		 * Add a table with 2 cells, first one has text, the second will get the image.
		 */
		Table table = odt.addTable(1, 2);
		Cell cell = table.getCellByPosition(0, 0);
		cell.setStringValue("some text"); //$NON-NLS-1$
		Cell cell2 = table.getCellByPosition(1, 0);
		/*
		 * Add the image to the cell.
		 */
		@SuppressWarnings("unused")
		Paragraph p = Paragraph.newParagraph(cell2); // required
		TextPElement firstParagraph = OdfElement.findFirstChildNode(TextPElement.class, cell2.getOdfElement());
		DrawFrameElement frame = firstParagraph.newDrawFrameElement();
		frame.setSvgHeightAttribute("0.672cm"); //hardcoded //$NON-NLS-1$
		frame.setSvgWidthAttribute("3.444cm"); //hardcoded //$NON-NLS-1$
		DrawImageElement imgElement = frame.newDrawImageElement();
		imgElement.setXlinkHrefAttribute("Pictures/test.jpg"); //$NON-NLS-1$
		imgElement.setXlinkTypeAttribute("simple"); //required //$NON-NLS-1$
		odt.save("/tmp/test.odt"); //$NON-NLS-1$
		OdfUtils.saveContentAsXml(odt);
	}
}
