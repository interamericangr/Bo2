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
import gr.interamerican.bo2.odftoolkit.utils.ResourceUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;
import gr.interamerican.bo2.utils.doc.DocumentTable;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link OdfToolkitTable}.
 */
@SuppressWarnings("nls")
public class TestOdfToolkitTable {
	
	
	
	/**
	 * Gets a sample table with the name Table1.
	 * 
	 * @return Returns the table.
	 * 
	 * @throws DocumentEngineException
	 */
	OdfToolkitTable sampleTable() throws DocumentEngineException {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestSetCell.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		return table1;
	}
	
	
	/**
	 * Unit test for getRowCount().
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testGetRowCount() throws DocumentEngineException {		
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestGetRowAndColumnCount.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		int rowCount = table1.getRowCount();
		Assert.assertEquals(2, rowCount);
	}
	
	/**
	 * Unit test for getRowCount().
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testGetColumnCount() throws DocumentEngineException {		
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestGetRowAndColumnCount.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		int colCount = table1.getColumnCount();
		Assert.assertEquals(3, colCount);
	}
	
	/**
	 * Unit test for appendColumn()
	 * @throws Exception 
	 */
	@Test
	public void testAppendColumn() throws Exception {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestAppendColumn.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		int colCount = table1.getColumnCount();
		table1.appendColumn();
		Assert.assertEquals(colCount+1, table1.getColumnCount());
		table1.appendColumn();
		Assert.assertEquals(colCount+2, table1.getColumnCount());
		table1.setCell(0, 2, "New column: 1");
		table1.setCell(0, 3, "New column: 2");
		table1.setCell(1, 2, "Cell 1,2: must be green");				
		table1.setCell(1, 3, "Cell 1,3: must be green");
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_AppendColumn.odt");
		engine.saveDocument(template, outPath);		
	}
	
	/**
	 * Unit test for appendColumn()
	 * @throws Exception 
	 */
	@Test
	public void testAppendRow() throws Exception {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestAppendRow.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		int rowCount = table1.getRowCount();
		table1.appendRow();
		Assert.assertEquals(rowCount+1, table1.getRowCount());
		table1.appendRow();
		Assert.assertEquals(rowCount+2, table1.getRowCount());
		
		table1.setCell(1, 0, "rewrote 1:Green");
		table1.setCell(1, 1, "rewrote 1:Red");	
		
		table1.setCell(2, 0, "2:Green");
		table1.setCell(2, 1, "2:Red");	
		table1.setCell(3, 0, "3:Green");
		table1.setCell(3, 1, "3:Red");	
		
		
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_AppendRow.odt");
		engine.saveDocument(template, outPath);
	}
	
	/**
	 * Unit test for appendColumn()
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testDeleteRow() throws DocumentEngineException {		
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestDeleteRow.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		int rowCountBefore = table1.getRowCount();
		table1.deleteRow(1);
		Assert.assertEquals(rowCountBefore-1, table1.getRowCount());
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_DeleteRow.odt");
		engine.saveDocument(template, outPath);
		
	}
	
	/**
	 * Unit test for appendColumn()
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testDeleteColumn() throws DocumentEngineException {		
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestDeleteColumn.odt");
		BusinessDocument template = engine.openDocument(path);	
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		table1.deleteColumn(1);
		Assert.assertEquals(1, table1.getColumnCount());
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_DeleteColumn.odt");
		engine.saveDocument(template, outPath);		
	}
	
	/**
	 * Unit test for setCell()
	 * 
	 * @throws DocumentEngineException
	 */
	@Test(expected=DocumentEngineException.class)
	public void testSetCell_invalidRow() throws DocumentEngineException {
		OdfToolkitTable table1 = sampleTable();
		table1.setCell(12,0, "X");
	}
	
	
	/**
	 * Unit test for setCell()
	 * 
	 * @throws DocumentEngineException
	 */
	@Test(expected=DocumentEngineException.class)
	public void testSetCell_invalidColumn() throws DocumentEngineException {
		OdfToolkitTable table1 = sampleTable();
		table1.setCell(0,12 , "X");
	}
	
	/**
	 * Unit test for setCell(int,int,String)
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testSetCell_string() throws DocumentEngineException {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestSetCell.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		table1.setCell(1,0 , "This is cell(1,0)");
		table1.setCell(1,1 , "This is cell(1,1)");
		table1.setCell(2,0 , "This is cell(2,0)");
		table1.setCell(2,1 , "This is cell(2,1)");
		
		DocumentTable table2 = template.getTable("Table2");
		table2.setCell(0, 1, "15.000.000,00");			
		table2.setCell(1, 1, "23000,765");			
		
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_SetCell_String.odt");
		engine.saveDocument(template, outPath);
	}
	
	/**
	 * Unit test for setCell(int,int,String)
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testSetCell_string_onHeader() throws DocumentEngineException {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("HeaderTableWithoutLogo.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("HeaderTable");
		OdfToolkitTable table1 = (OdfToolkitTable) table;		
		table1.setCell(0,0 , "Header cell(0,0)"); 
		/*
		 * cell 0,1 is not empty, but contains an OdfTextSpan.
		 * Such cells can't be modified by setCell(i,i,string) 
		 * or setCell(i,i,doc)
		 */
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_SetCell_String_onHeader.odt");
		engine.saveDocument(template, outPath);
	}
	
	/**
	 * Unit test for setCell(int,int,BusinessDocument)
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testSetCell_BusinessDocument() throws DocumentEngineException {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestSetCell.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		
		String starLogoPath = ResourceUtils.inputPath("LogoStarInsurance.odt");
		BusinessDocument starLogo = engine.openDocument(starLogoPath);
		String otherLogoPath = ResourceUtils.inputPath("LogoOtherInsurance.odt");
		BusinessDocument otherLogo = engine.openDocument(otherLogoPath);
		String smallDocPath = ResourceUtils.inputPath("SmallText.odt");
		BusinessDocument smallDoc = engine.openDocument(smallDocPath);
		
		table1.setCell(1,0 , starLogo);
		table1.setCell(1,1 , "Cell 1,0 contains the star insurance logo");
		table1.setCell(2,0 , "Cell 2,1 contains the other insurance logo");
		table1.setCell(2,1 , otherLogo);
		
		DocumentTable table2 = template.getTable("Table2");
		table2.setCell(0, 1, smallDoc);			
		table2.setCell(1, 1, "");			
		
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_SetCell_BusinessDocument.odt");
		engine.saveDocument(template, outPath);
	}
	
	
	/**
	 * Unit test for setCell(int,int,BusinessDocument)
	 * 
	 * @throws DocumentEngineException
	 * 
	 * TODO: fix this test.
	 */
	@Test
	public void testSetCell_BusinessDocument_onHeader() throws DocumentEngineException {
//		OdfToolkitEngine engine = new OdfToolkitEngine();
//		String path = ResourceUtils.inputPath("HeaderTableWithoutLogo.odt");
//		BusinessDocument template = engine.openDocument(path);		
//		DocumentTable table = template.getTable("HeaderTable");
//		OdfToolkitTable table1 = (OdfToolkitTable) table;		
//		String starLogoPath = ResourceUtils.inputPath("LogoStarInsurance.odt");
//		BusinessDocument starLogo = engine.openDocument(starLogoPath);		
//		table1.setCell(0,0 , starLogo);		
//		String outPath = ResourceUtils.outputPath("OdfToolkitTable_SetCell_BusinessDocument_onHeader.odt");
//		engine.saveDocument(template, outPath);
	}
	
	/**
	 * Unit test for checkCellCoordinates()
	 * 
	 * @throws DocumentEngineException
	 */
	@Test(expected=DocumentEngineException.class)
	public void testCheckCellCoordinates_invalidRow() throws DocumentEngineException {
		OdfToolkitTable table1 = sampleTable();
		table1.checkCellCoordinates(12, 0);
	}
	
	/**
	 * Unit test for checkCellCoordinates()
	 * 
	 * @throws DocumentEngineException
	 */
	@Test(expected=DocumentEngineException.class)
	public void testCheckCellCoordinates_invalidCol() throws DocumentEngineException {
		OdfToolkitTable table1 = sampleTable();
		table1.checkCellCoordinates(0, 12);
	}
	
	/**
	 * Unit test for checkCellCoordinates()
	 * 
	 * @throws DocumentEngineException
	 */
	@Test()
	public void testCheckCellCoordinates() throws DocumentEngineException {
		OdfToolkitTable table1 = sampleTable();
		table1.checkCellCoordinates(0, 0);
	}
	
	/**
	 * Unit test for setCell()
	 * 
	 * @throws DocumentEngineException
	 */
	@Test()
	public void testInvalid() throws DocumentEngineException {
		OdfToolkitTable table1 = sampleTable();
		DocumentEngineException ir = table1.invalid("row", 1);
		Assert.assertNotNull(ir);
		Assert.assertNotNull(ir.getMessage());
	}
	

	
	
	/**
	 * Unit test for appendColumn()
	 * @throws Exception 
	 */
	@Test
	public void testCopyStyles() throws Exception {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestCopyStyles.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		table1.copyStyles(0, 0, 2, 0);
		table1.copyStyles(0, 1, 2, 1);		
		table1.setCell(1,0 , "This row is not a header");
		table1.setCell(1,1 , "This row is not a header");		
		table1.setCell(2,0 , "Header (2,0)");
		table1.setCell(2,1 , "Header (2,1)");
		
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_CopyStyles.odt");
		engine.saveDocument(template, outPath);
	}
	
	/**
	 * Unit test for appendColumn()
	 * @throws Exception 
	 */
	@Test
	public void testCopyStyleOfFirstColumnToOthers() throws Exception {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestCopyStyleOfFirstColumnToOthers.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		table1.copyStyleOfFirstColumnToOthers(0);
		
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_CopyStyleOfFirstColumnToOthers.odt");
		engine.saveDocument(template, outPath);
					
		OdfToolkitTextDocument doc = OdfToolkitEngine.safeCast(template);
		OdfUtils.saveContentAsXml(doc.document);
	}
	
	
	/**
	 * Unit test for delete()
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testDelete() throws DocumentEngineException {		
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestDelete.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		table1.delete();
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_Delete.odt");
		engine.saveDocument(template, outPath);
	}
	
	/**
	 * Unit test for delete()
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testGetStyleOfCellParagraph() throws DocumentEngineException {		
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestGetRowAndColumnCount.odt");
		BusinessDocument template = engine.openDocument(path);		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		String style00 = table1.getStyleOfCellParagraph(0, 0);		
		Assert.assertEquals("P7", style00); //header
		String style01 = table1.getStyleOfCellParagraph(0, 1);
		Assert.assertEquals("P7", style01);		
		String style10 = table1.getStyleOfCellParagraph(1, 0);		
		Assert.assertEquals("P5", style10); //green font
		String style11 = table1.getStyleOfCellParagraph(1, 1);
		Assert.assertEquals("P6", style11); //red font
	}
	
	/**
	 * Unit test for setCell(int,int,String)
	 * @throws Exception 
	 */
	@Test
	public void testSetCellGraphic_newPicture() throws Exception {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestGraphics.odt");
		BusinessDocument template = engine.openDocument(path);		
		String imageType = "image/png";		
		String imageName0 = "StarInsuranceLogo.png";
		String imagepath0 = ResourceUtils.inputPath(imageName0);
		byte[] image0 = StreamUtils.getFileContents(imagepath0, false);		
		String imageName1 = "ArrowsLogo.png";
		String imagepath1 = ResourceUtils.inputPath(imageName1);
		byte[] image1 = StreamUtils.getFileContents(imagepath1, false);
		
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		table1.setCellGraphic(0,0, imageName0, image0, imageType);
		table1.setCellGraphic(1,1, imageName1, image1, imageType);
		
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_SetCellGraphic_newPic.odt");
		engine.saveDocument(template, outPath);		
		OdfUtils.saveContentAsXml(OdfToolkitEngine.safeCast(template).document);
	}
	
	/**
	 * Unit test for setCell(int,int,String)
	 * @throws Exception 
	 */
	@Test
	public void testSetCellGraphic_existingPicture() throws Exception {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("TableTestGraphics.odt");
		BusinessDocument template = engine.openDocument(path);		
		String imageType = "image/png";		
		String imageName = "StarInsuranceLogo.png";
		String imagepath = ResourceUtils.inputPath(imageName);
		byte[] image = StreamUtils.getFileContents(imagepath, false);
		DocumentTable table = template.getTable("Table1");
		OdfToolkitTable table1 = (OdfToolkitTable) table;
		OdfToolkitTextDocument doc = new OdfToolkitTextDocument(table1.owner);
		doc.newPicture(imageName, image, imageType);
		table1.setCellGraphic(0,0, imageName);
		table1.setCellGraphic(1,1, imageName);		
		String outPath = ResourceUtils.outputPath("OdfToolkitTable_SetCellGraphic_existingPic.odt");
		engine.saveDocument(template, outPath);		
		OdfUtils.saveContentAsXml(OdfToolkitEngine.safeCast(template).document);
	}
	
	

	
	
}
