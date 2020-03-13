package gr.interamerican.bo2.odftoolkit.utils;

import gr.interamerican.bo2.odftoolkit.OdfToolkitEngine;
import gr.interamerican.bo2.odftoolkit.OdfToolkitTextDocument;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.simple.table.Table;


/**
 * Unit test for {@link TableUtils}.
 */
public class TestTableUtils {

	
	/**
	 * Unit test for getTables().
	 *
	 * @throws Exception the exception
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetTables() throws Exception {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String path = ResourceUtils.inputPath("DocumentWithBarcodeTables.odt");
		OdfToolkitTextDocument template = (OdfToolkitTextDocument) engine.openDocument(path);
		List<Table> tables = TableUtils.getTables(template.document);
		Assert.assertEquals(4, tables.size());
	}
	
	


}
