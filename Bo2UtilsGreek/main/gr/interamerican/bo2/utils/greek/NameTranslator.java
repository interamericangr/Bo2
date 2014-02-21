package gr.interamerican.bo2.utils.greek;

import static java.lang.Character.toUpperCase;
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
	AssociationTable<Character, Character> associations = 
		new AssociationTable<Character, Character>();

	/**
	 * Creates a new VisuallySimilarLatin object. 
	 *
	 */
	public NameTranslator() {
		super();
		AssociationTable<Character, Character> lowercases = 
				new AssociationTable<Character, Character>();
		lowercases.associate('α', 'a');
		lowercases.associate('β', 'b');
		lowercases.associate('γ', 'g');
		lowercases.associate('δ', 'd');
		lowercases.associate('ε', 'e');
		lowercases.associate('ζ', 'z');
		lowercases.associate('η', 'i');
		lowercases.associate('θ', 't'); //TODO: th
		lowercases.associate('ι', 'i');
		lowercases.associate('κ', 'k');
		lowercases.associate('λ', 'l');
		lowercases.associate('μ', 'm');
		lowercases.associate('ν', 'n');
		lowercases.associate('ξ', 'x');
		lowercases.associate('ο', 'o');
		lowercases.associate('π', 'p');
		lowercases.associate('ρ', 'r');
		lowercases.associate('σ', 's');
		lowercases.associate('ς', 's');
		lowercases.associate('τ', 't');
		lowercases.associate('υ', 'y');
		lowercases.associate('φ', 'f');
		lowercases.associate('χ', 'h'); //TODO: ch
		lowercases.associate('ψ', 'p'); //TODO: ps
		lowercases.associate('ω', 'o');		
		//TODO: ου => ou, διαλυτικά, κτλ.
		
		for (Pair<Character, Character> pair : lowercases) {
			Character l = pair.getLeft();
			Character r = pair.getRight();			
			associations.associate(l, r);
			associations.associate(toUpperCase(l), toUpperCase(r));
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
		Character c = associations.getRight(greek);
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
