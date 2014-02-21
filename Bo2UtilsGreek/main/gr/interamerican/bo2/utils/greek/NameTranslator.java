package gr.interamerican.bo2.utils.greek;

import static java.lang.Character.toUpperCase;

import java.util.HashMap;
import java.util.Map;

import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.beans.AssociationTable;
import gr.interamerican.bo2.utils.beans.Pair;

/**
 * Translates a name from/to Greek/Latin.
 */
public class NameTranslator {
	
	/**
	 * Characters asssociation table.
	 * 
	 * Greek character is left, Latin character is right.
	 */
	Map<Character, Character> associations = 
		new HashMap<Character, Character>();

	/**
	 * Creates a new VisuallySimilarLatin object. 
	 *
	 */
	public NameTranslator() {
		super();
		Map<Character, Character> lowercases = 
				new HashMap<Character, Character>();
		lowercases.put('α', 'a');
		lowercases.put('β', 'b');
		lowercases.put('γ', 'g');
		lowercases.put('δ', 'd');
		lowercases.put('ε', 'e');
		lowercases.put('ζ', 'z');
		lowercases.put('η', 'i');
		lowercases.put('θ', 't'); //TODO: th
		lowercases.put('ι', 'i');
		lowercases.put('κ', 'k');
		lowercases.put('λ', 'l');
		lowercases.put('μ', 'm');
		lowercases.put('ν', 'n');
		lowercases.put('ξ', 'x');
		lowercases.put('ο', 'o');
		lowercases.put('π', 'p');
		lowercases.put('ρ', 'r');
		lowercases.put('σ', 's');
		lowercases.put('ς', 's');
		lowercases.put('τ', 't');
		lowercases.put('υ', 'y');
		lowercases.put('φ', 'f');
		lowercases.put('χ', 'h'); //TODO: ch
		lowercases.put('ψ', 'p'); //TODO: ps
		lowercases.put('ω', 'o');		
		//TODO: ου => ou, διαλυτικά, κτλ.
		
		for (Map.Entry<Character, Character> entry : lowercases.entrySet()) {
			Character l = entry.getKey();
			Character r = entry.getValue();	
			associations.put(l, r);
			associations.put(toUpperCase(l), toUpperCase(r));
		}
	}
	
	/**
	 * Gets the associated latin character.
	 * 
	 * @param greek
	 *        Greek character.
	 *        
	 * @return Returns the associated Latin character.
	 */
	Character getLatin(Character greek) {
		Character c = associations.get(greek);
		return Utils.notNull(c, greek);		
	}
	
	/**
	 * Writes the specified Greek name with Latin characters.
	 * 
	 * Only capital letters have a visually similar character. 
	 * 
	 * @param greek
	 * 
	 * @return Returns the Greek character that is visually similar
	 *         with the specified character. If the specified character 
	 *         is not a Latin capital letter, or if it has not a visually
	 *         similar Greek character, then returns null.
	 */
	public String toLatin(String greek) {
		char[] input = greek.toCharArray();
		char[] output = new char[input.length];
		for (int i = 0; i < input.length; i++) {			
			output[i] = getLatin(input[i]);			
		}
		return new String(output);		
	}
	
	
	
	

}
