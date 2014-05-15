package gr.interamerican.bo2.utils.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

/**
 * Utilities for DOM.
 */
public class DomUtils {

	/**
	 * Creates a list of {@link Node}s of a {@link Document} whose tag name
	 * matches a supplied regex. The matching is case insensitive.
	 * 
	 * @param doc
	 * @param regex
	 * 
	 * @throws ClassCastException
	 *             if the runtime {@link Document} implementation does not also
	 *             implement {@link DocumentTraversal} or if the {@link Node}
	 *             implementation does not also implement {@link Element}
	 * 
	 * @return matched nodes.
	 */
	public static List<Node> matchedNodes(Document doc, String regex) {
		List<Node> matchedNodes = new ArrayList<Node>();
		DocumentTraversal traversal = (DocumentTraversal) doc;
		NodeIterator iterator = traversal.createNodeIterator(doc.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null, true);
		for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
			String tagname = ((Element) n).getTagName();
			if (tagname.toUpperCase().matches(regex.toUpperCase())) {
				matchedNodes.add(n);
			}
		}
		return matchedNodes;
	}
	
	/**
	 * Creates a DOM {@link Document}
	 * 
	 * @param stream
	 * @return Document
	 */
	public static Document getDocument(InputStream stream) {
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(stream);
			return document;
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
