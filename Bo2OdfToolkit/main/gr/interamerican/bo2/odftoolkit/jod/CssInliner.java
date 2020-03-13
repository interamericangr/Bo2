package gr.interamerican.bo2.odftoolkit.jod;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * The Class CssInliner.
 */
public class CssInliner {
	
	/** The Constant CSS_STYLE. */
	public static final String CSS_STYLE = "style"; //$NON-NLS-1$
	
	/** The Constant CSS_CLASS. */
	public static final String CSS_CLASS = "class"; //$NON-NLS-1$
	
	/** The style by tag. */
	Map<String, String> styleByTag = new HashMap<String, String>();
	
	/** The style by class. */
	Map<String, String> styleByClass = new HashMap<String, String>();
	
	/**
	 * Inline css.
	 *
	 * @param xhtml the xhtml
	 * @return the string
	 */
	public String inlineCss(String xhtml) {
		Document doc = Jsoup.parse(xhtml);
		Element style = doc.select(CSS_STYLE).first();
		extractStyles(style.html());
        applyStyles(doc);
        style.remove();
        return doc.html();
	}
	
	/**
	 * Extract styles.
	 *
	 * @param stylesSection the styles section
	 */
	@SuppressWarnings("nls")
	void extractStyles(String stylesSection) {
		String[] tokens = TokenUtils.splitTrim(stylesSection, "{}");
		
		int index = 0;
		
		while(index < tokens.length-1) {
			String classesOrTags = tokens[index++];
			String style = tokens[index++];
			
			//omit xml comments
			classesOrTags = classesOrTags.replaceAll("<!--.*--&gt;", "");
			style = style.replaceAll("<!--.*--&gt;", "");
			
			//omit line changes
			classesOrTags = classesOrTags.replaceAll("\n", "").trim();
			style = style.replaceAll("\n", "").trim();
			
			if(isClass(classesOrTags)) {
				String[] classes = classesOrTags.split(" \\.");
				for(String c : classes) {
					if(!StringUtils.isNullOrBlank(c)) {
						styleByClass.put(c.substring(1), style);
					}
				}
			} else {
				String[] tags = classesOrTags.split(",");
				for(String t : tags) {
					if(!StringUtils.isNullOrBlank(t)) {
						styleByTag.put(t, style);
					}
				}
			}
		}
	}
	
	/**
	 * Checks if is class.
	 *
	 * @param classOrTag the class or tag
	 * @return true, if is class
	 */
	boolean isClass(String classOrTag) {
		if(classOrTag.startsWith(StringConstants.DOT)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Apply styles.
	 *
	 * @param doc the doc
	 */
	void applyStyles(Document doc) {
		for(Map.Entry<String, String> entry : styleByTag.entrySet()) {
			for(Element e : doc.getElementsByTag(entry.getKey())) {
				e.attr(CSS_STYLE, entry.getValue());
			}
		}
		
		for(Map.Entry<String, String> entry : styleByClass.entrySet()) {
			for(Element e : doc.getElementsByClass(entry.getKey())) {
				e.attr(CSS_STYLE, entry.getValue());
				e.removeAttr(CSS_CLASS);
			}
		}
	}
}