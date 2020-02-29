package gr.interamerican.bo2.odftoolkit.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.odftoolkit.odfdom.dom.element.meta.MetaUserDefinedElement;

import gr.interamerican.bo2.odftoolkit.OdfToolkitEngine;
import gr.interamerican.bo2.odftoolkit.OdfToolkitTextDocument;

/**
 * Unit tests for {@link UserDefinedPropertiesUtils}.
 */
public class TestUserDefinedPropertiesUtils {

	/**
	 * Test for getUserDefinedProperties(doc).
	 *
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("nls")
	@Test
	public void getUserDefinedProperties() throws Exception {
		List<String> names = new ArrayList<>(Arrays.asList("Barcode_a.type", "Barcode_b.type", "Barcode_c.msg"));
		List<String> content = new ArrayList<>(Arrays.asList("CODE128", "CODE39", "BOTTOM"));
		String inpath = ResourceUtils.inputPath("DocumentWithBarcodeTables.odt");
		OdfToolkitEngine engine = new OdfToolkitEngine();
		OdfToolkitTextDocument doc = (OdfToolkitTextDocument) engine.openDocument(inpath);
		List<MetaUserDefinedElement> list = UserDefinedPropertiesUtils.getUserDefinedProperties(doc.document);
		assertEquals(3, list.size());
		for (MetaUserDefinedElement element : list) {
			assertTrue(names.remove(element.getMetaNameAttribute()));
			assertTrue(content.remove(element.getTextContent()));
		}
	}
}