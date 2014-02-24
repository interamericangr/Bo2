package gr.interamerican.bo2.utils.greek;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility for Greek letters.
 */
public class GreekLetters {
	
	/**
	 * Singleton instance.
	 */
	private static final GreekLetters INSTANCE = new GreekLetters();
	
	/**
	 * Gets the instance.
	 *
	 * @return Returns the instance
	 */
	public static GreekLetters getInstance() {
		return INSTANCE;
	}

	
	
	/**
	 * vowels.
	 */
	private final char[] vowels = {'á', 'å', 'ç', 'é', 'ï', 'õ', 'ù'};
	/**
	 * vowelsWithTone.
	 */
	private final char[] vowelsWithTone = {'Ü', 'Ý', 'Þ', 'ß', 'ü', 'ý', 'þ'};
	/**
	 * vowelsWithDialytika.
	 */
	private final char[] vowelsWithDialytika = {'ú', 'û'};
	/**
	 * vowelsWithToneAndDialytika.
	 */
	private final char[] vowelsWithToneAndDialytika = {'À', 'à'};
	/**
	 * short consonants.
	 */
	private final char[] soundlessConsonants = {'è', 'ê', 'ð', 'ó', 'ô', 'ö', '÷', 'ò', 'î' };	
	
	
	/**
	 * long consonants.
	 */
	private final char[] soundfulConsonants = {'â', 'ã', 'ä', 'æ', 'ë', 'ì', 'í', 'ñ', 'ø'};
	
	
	/**
	 * vowels.
	 */
	private final Set<Character> vowelsSet = new HashSet<Character>();
	/**
	 * consonants.
	 */
	private final Set<Character> consonantsSet = new HashSet<Character>();
	/**
	 * consonants.
	 */
	private final Set<Character> soundlessConsonantsSet = new HashSet<Character>();
	/**
	 * consonants.
	 */
	private final Set<Character> soundfulConsonantsSet = new HashSet<Character>();
	
	/**
	 * Creates a new GreekLetters object. 
	 *
	 */
	private GreekLetters() {
		super();
		for (char c : vowels) {
			vowelsSet.add(c);
			vowelsSet.add(Character.toUpperCase(c));
		}
		for (char c : vowelsWithTone) {
			vowelsSet.add(c);
			vowelsSet.add(Character.toUpperCase(c));
		}
		for (char c : vowelsWithDialytika) {
			vowelsSet.add(c);
			vowelsSet.add(Character.toUpperCase(c));
		}
		for (char c : vowelsWithToneAndDialytika) {
			vowelsSet.add(c);			
		}
		
		for (char c : soundlessConsonants) {
			soundlessConsonantsSet.add(c);
			soundlessConsonantsSet.add(Character.toUpperCase(c));			
		}
		for (char c : soundfulConsonants) {
			soundfulConsonantsSet.add(c);
			soundfulConsonantsSet.add(Character.toUpperCase(c));			
		}
		consonantsSet.addAll(soundfulConsonantsSet);
		consonantsSet.addAll(soundlessConsonantsSet);
	}
	
	
	
	/**
	 * Gets the vowelsSet.
	 *
	 * @return Returns the vowelsSet
	 */
	public Set<Character> getVowelsSet() {
		return Collections.unmodifiableSet(vowelsSet);
	}

	/**
	 * Gets the consonantsSet.
	 *
	 * @return Returns the consonantsSet
	 */
	public Set<Character> getConsonantsSet() {
		return Collections.unmodifiableSet(consonantsSet);
	}

	/**
	 * Gets the soundlessConsonantsSet.
	 *
	 * @return Returns the soundlessConsonantsSet
	 */
	public Set<Character> getSoundlessConsonantsSet() {
		return Collections.unmodifiableSet(soundlessConsonantsSet);
	}
	
	/**
	 * Gets the soundlessConsonantsSet.
	 *
	 * @return Returns the soundlessConsonantsSet
	 */
	public Set<Character> getSoundfulConsonantsSet() {
		return Collections.unmodifiableSet(soundfulConsonantsSet);
	}

	
	
	
	
	
	

}
