package gr.interamerican.bo2.odftoolkit.jod;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class AutomaticCssInliner {
	
	public static final String CSS_STYLE = "style";
	public static final String CSS_CLASS = "class";

	@SuppressWarnings("nls")
	static String inlineCss(String xhtml) {
		
		
		
		Document doc = Jsoup.parse(xhtml);
        
		Element style = doc.select(CSS_STYLE).first();
		Map<String, String> dottedStyles = extractStyles(style.html(), "[.](\\w+)\\s*[{]([^}]+)[}]");
		

        applyStyles(doc);
        return doc.outerHtml();
	}
	
	
	static Map<String, String> extractStyles(String html, String expr){
		Matcher cssMatcher = Pattern.compile(expr).matcher(html);
        Map<String, String> extractedStyles = new HashMap<String, String>();
        while (cssMatcher.find()) {
            System.out.println("Style `" + cssMatcher.group(1) + "`: " + cssMatcher.group(2));
            extractedStyles.put(cssMatcher.group(1), cssMatcher.group(2));
        }
        return extractedStyles;
	}
	
	/**
	 * Transfers styles from the <code>cssstyle</code> attribute to the
	 * <code>style</code> attribute.
	 * 
	 * @param doc
	 *            the html document
	 */
	static void applyStyles(Document doc) {
		Elements allStyledElements = doc.getElementsByAttribute(CSS_CLASS);
		for (Element e : allStyledElements) {
			String currentClass = e.attr(CSS_CLASS);
			
			String currentStyle = e.attr(CSS_STYLE);			
			e.attr(CSS_STYLE, "font-size: 12px;");
			e.removeAttr(CSS_CLASS);
		}
	}

	
}
