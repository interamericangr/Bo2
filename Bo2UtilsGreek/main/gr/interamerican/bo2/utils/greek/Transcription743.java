package gr.interamerican.bo2.utils.greek;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.beans.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Transcripts Greek names to Latin according to the
 * ELOT 743 standard.
 */
@SuppressWarnings("nls")
public class Transcription743 {
	
	/**
	 * Singleton instance.
	 */
	private static final Transcription743 INSTANCE = new Transcription743();	
	
	/**
	 * Gets the instance.
	 *
	 * @return Returns the instance
	 */
	public static Transcription743 getInstance() {
		return INSTANCE;
	}


	/**
	 * Pairs for words written entirely with capital letters only. 
	 */
	private List<Pair<String, String>> onlyCaps; 

	/**
	 * Pairs for words not written entirely with capital letters only.
	 */
	private List<Pair<String, String>> notOnlyCaps; 

	
	/**
	 * Creates a new Transcription743 object. 
	 *
	 */
	private Transcription743() {
		super();
		onlyCaps = new ArrayList<Pair<String,String>>();
		addPairsForFirstB(onlyCaps);
		addAfAndAv(onlyCaps, true);
		addDiphthongsForCaps(onlyCaps);
		addRestLetters(onlyCaps, true);
		addForChiPsiThitaCaps(onlyCaps);
		
		
		notOnlyCaps = new ArrayList<Pair<String,String>>();
		notOnlyCaps = new ArrayList<Pair<String,String>>();
		addPairsForFirstB(notOnlyCaps);
		addAfAndAv(notOnlyCaps, false);
		addDiphthongsForNonCaps(notOnlyCaps);
		addRestLetters(notOnlyCaps, false);
		addForChiPsiThitaSmall(notOnlyCaps);
	}
	
	
	
	
	/**
	 * Adds replacement pairs for changing 'ìð' in the start
	 * of the word with Â.
	 * 
	 * @param list
	 */
	void addPairsForFirstB (List<Pair<String, String>> list) {		
		String[] greekB = {" ìð", " Ìð", " ÌÐ"};
		String[] latinB = {" b", " B", " B"};
		for (int i = 0; i < greekB.length; i++) {
			list.add(new Pair<String, String>(greekB[i], latinB[i]));
		}
	}
	
	/**
	 * Adds replacement pairs for changing 'áõ' and 'åõ'.
	 * 
	 * @param list
	 * @param caps 
	 */
	void addAfAndAv(List<Pair<String, String>> list, boolean caps) {
		String[] greeksNonCaps = {"áõ", "Áõ", "åõ", "Åõ"};
		String[] withEfNonCaps = {"af", "Af", "ef", "Ef"};
		String[] withVeNonCaps = {"av", "Av", "ev", "Ev"};	
		
		String[] greeksCaps = {"ÁÕ", "ÅÕ"};
		String[] withEfCaps = {"AF", "EF"};
		String[] withVeCaps = {"AV", "EV"};
		
		String[] greeks = caps ? greeksCaps : greeksNonCaps;
		String[] withF  = caps ? withEfCaps : withEfNonCaps;
		String[] withV  = caps ? withVeCaps : withVeNonCaps;
		
		GreekLetters gr = GreekLetters.getInstance();		
		Set<Character> toF = new HashSet<Character>();
		toF.addAll(gr.getSoundlessConsonantsSet());	
		toF.add(' ');
		Set<Character> toV = new HashSet<Character>();		
		toV.addAll(gr.getVowelsSet());
		toV.addAll(gr.getSoundfulConsonantsSet());		
		
		String find, replace;
		String tz = "ôæ";
		for (int i = 0; i < greeks.length; i++) {
			find = greeks[i] + tz;
			replace = withV[i] + tz;
			list.add(new Pair<String, String>(find, replace));
			for (Character c : toV) {
				find = greeks[i] + c;
				replace = withV[i] + c;
				list.add(new Pair<String, String>(find, replace));
			}
			for (Character c : toF) {
				find = greeks[i] + c;
				replace = withF[i] + c;
				list.add(new Pair<String, String>(find, replace));
			}
		}
	}
	
	
	
	/**
	 * Adds replacement pairs for diphthongs.
	 * 
	 * @param list
	 */
	void addDiphthongsForNonCaps (List<Pair<String, String>> list) {
		String[] greek = {"ãã", "ãê", "Ãê", "ãî", "ã÷",  "ïõ", "Ïõ"};
		String[] latin = {"ng", "gk", "Gk", "nx", "nch", "ou", "Ou"};
		for (int i = 0; i < greek.length; i++) {
			list.add(new Pair<String, String>(greek[i], latin[i]));
		}
	}
	
	/**
	 * Adds replacement pairs for diphthongs.
	 * 
	 * @param list
	 */
	void addDiphthongsForCaps (List<Pair<String, String>> list) {
		String[] greek = {"ÃÃ", "ÃÊ", "ÃÎ", "Ã×",  "ÏÕ"};
		String[] latin = {"NG", "GK", "NX", "NCH", "OU"};
		for (int i = 0; i < greek.length; i++) {
			list.add(new Pair<String, String>(greek[i], latin[i]));
		}
	}
	
	/**
	 * Adds replacement pairs for changing 'ìð' in the start
	 * of the word with Â.
	 * 
	 * @param list
	 * @param includeOnlyCaps 
	 */
	void addRestLetters (List<Pair<String, String>> list, boolean includeOnlyCaps) {		
		String[] greek = 
			{"á", "â", "ã", "ä", "å", "æ", "ç", "é", "ú", "ê", "ë", "ì", "í", "î", "ï", "ð", "ñ", "ó", "ò", "ô", "õ", "û", "ö", "ù"};
		String[] latin = 
			{"a", "v", "g", "d", "e", "z", "i", "i", "i", "k", "l", "m", "n", "x", "o", "p", "r", "s", "s", "t", "y", "y", "f", "o"};
		for (int i = 0; i < greek.length; i++) {
			if (!includeOnlyCaps) {
				list.add(new Pair<String, String>(greek[i], latin[i]));
			}
			list.add(new Pair<String, String>(greek[i].toUpperCase(), latin[i].toUpperCase()));
		}
	}
	
	/**
	 * Adds replacement pairs for changing ×,Ø,È.
	 * @param list 
	 */
	void addForChiPsiThitaCaps(List<Pair<String, String>> list) {		
		list.add(new Pair<String, String>("×", "CH"));
		list.add(new Pair<String, String>("Ø", "PS"));
		list.add(new Pair<String, String>("È", "TH"));
	}
	
	/**
	 * Adds replacement pairs for changing ×,Ø,È.
	 * @param list 
	 */
	void addForChiPsiThitaSmall(List<Pair<String, String>> list) {
		list.add(new Pair<String, String>("÷", "ch"));
		list.add(new Pair<String, String>("ø", "ps"));
		list.add(new Pair<String, String>("è", "th"));
		list.add(new Pair<String, String>("×", "Ch"));
		list.add(new Pair<String, String>("Ø", "Ps"));
		list.add(new Pair<String, String>("È", "Th"));		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Writes the specified Greek name with Latin characters. 
	 * 
	 * @param greek
	 * 
	 * @return Returns the Latin transcript of the name.
	 */
	String transcriptWord(String greek) {
		boolean isUcase = greek.toUpperCase().equals(greek);		
		RemoveToneSigns notone = RemoveToneSigns.getInstance();
		List<Pair<String, String>> pairs = isUcase ? onlyCaps : notOnlyCaps;
		String string = " " + notone.removeToneSigns(greek) + " ";
		for (Pair<String, String> pair : pairs) {
			string = string.replace(pair.getLeft(), pair.getRight());
		}
		return string.trim();		
	}
	
	/**
	 * Writes the specified Greek name with Latin characters. 
	 * 
	 * @param greek
	 * 
	 * @return Returns the Latin transcript of the name.
	 */
	public String transcript(String greek) {
		String[] greeks = TokenUtils.splitTrim(greek, ' ');
		String[] latins = new String[greeks.length];
		for (int i = 0; i < greeks.length; i++) {
			latins[i] = transcriptWord(greeks[i]);			
		}
		return StringUtils.concatSeparated(" ", latins);			
	}
	
	

}
