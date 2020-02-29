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
import org.w3c.dom.NamedNodeMap;
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
	 * Finds the value of an attribute in an XML element. Returns nul if the
	 * attribute is not found.
	 * 
	 * @param node
	 *        XML element
	 * @param attributeName
	 *        XML attribute name
	 *        
	 * @return XML attribute value
	 */
	public static String getAttributeValue(Node node, String attributeName) {
		NamedNodeMap nnm = node.getAttributes();
		Node attribute = nnm.getNamedItem(attributeName);
		if (attribute != null) {
			return attribute.getNodeValue();
		}
		return null;
	}

	/**
	 * Creates a list of {@link Node}s of a {@link Document} whose tag name matches
	 * a supplied regex. The matching is case insensitive.
	 *
	 * @param doc the doc
	 * @param regex the regex
	 * @return matched nodes.
	 * @throws ClassCastException             if the runtime {@link Document} implementation does not also
	 *             implement {@link DocumentTraversal} or if the {@link Node}
	 *             implementation does not also implement {@link Element}
	 */
	public static List<Node> matchedNodes(Document doc, String regex) {
		List<Node> matchedNodes = new ArrayList<Node>();
		DocumentTraversal traversal = (DocumentTraversal) doc;
		NodeIterator iterator = traversal.createNodeIterator(doc.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null,
				true);
		for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
			String tagname = ((Element) n).getTagName();
			if (tagname.toUpperCase().matches(regex.toUpperCase())) {
				matchedNodes.add(n);
			}
		}
		return matchedNodes;
	}
	
	/**
	 * Creates a DOM {@link Document}.
	 *
	 * @param stream the stream
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

	/**
	 * Returns a Child Node of the input node with a given name.<br>
	 * If such child node does not exist - it returns null.
	 * 
	 * @param node
	 *            Node to search
	 * @param nodeName
	 *            Name of the child node we want
	 * @return Child Node or null
	 */
	public static Node getNode(Node node, String nodeName) {
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node childNode = node.getChildNodes().item(i);
			if (childNode.getNodeName().equals(nodeName)) {
				return childNode;
}
		}
		return null;
	}

}
