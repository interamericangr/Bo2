package gr.interamerican.bo2.utils.xml;

import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Test {@link DomUtils}
 */
@SuppressWarnings("nls")
public class TestDomUtils {
	
	@Test
	public void testGetDocument() {
		InputStream stream = StreamUtils.class.getResourceAsStream("/gr/interamerican/bo2/samples/xml/sampleXml.xml");
		Document doc = DomUtils.getDocument(stream);
		
		Assert.assertNotNull(doc);
	}
	
	@Test
	public void testGetAttributeValue() {
		InputStream stream = StreamUtils.class.getResourceAsStream("/gr/interamerican/bo2/samples/xml/sampleXml.xml");
		Document doc = DomUtils.getDocument(stream);
		Node node = doc.getElementsByTagName("sampleNode").item(0);
		String actual = DomUtils.getAttributeValue(node, "attr1");
		Assert.assertEquals(StringConstants.EMPTY, actual);
		Assert.assertNull(DomUtils.getAttributeValue(node, "NA"));
	}
	
	@Test
	public void testMatchedNodes() {
		InputStream stream = StreamUtils.class.getResourceAsStream("/gr/interamerican/bo2/samples/xml/sampleXml.xml");
		Document doc = DomUtils.getDocument(stream);
		
		String regex = ".*paSSword";
		List<Node> nodes = DomUtils.matchedNodes(doc, regex);
		
		Assert.assertTrue(nodes.size()==1);
		Assert.assertEquals(((Element) nodes.get(0)).getTagName(), "ns2:password");
	}

}
