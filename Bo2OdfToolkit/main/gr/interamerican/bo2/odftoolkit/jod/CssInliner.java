package gr.interamerican.bo2.odftoolkit.jod;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CssInliner {
	
	public static final String CSS_STYLE = "style";
	public static final String CSS_CLASS = "class";
	
	Map<String, String> styleByTag = new HashMap<String, String>();
	Map<String, String> styleByClass = new HashMap<String, String>();
	
	public String inlineCss(String xhtml) {
		Document doc = Jsoup.parse(xhtml);
		Element style = doc.select(CSS_STYLE).first();
		extractStyles(style.html());
        applyStyles(doc);
        style.remove();
        return doc.html();
	}
	
	void extractStyles(String stylesSection) {
		String[] tokens = TokenUtils.splitTrim(stylesSection, "{}");
		
		int index = 0;
		
		while(index < tokens.length-1) {
			String classesOrTags = tokens[index++];
			String style = tokens[index++];
			
			//omit xml comments
			classesOrTags = classesOrTags.replaceAll("<!--.*-->", "");
			style = style.replaceAll("<!--.*-->", "");
			
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
	
	boolean isClass(String classOrTag) {
		if(classOrTag.startsWith(".")) {
			return true;
		}
		return false;
	}
	
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
