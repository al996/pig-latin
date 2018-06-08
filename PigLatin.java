// PigLatin.java			Amy Liu

import java.util.Scanner;

public class PigLatin
{
	static String originalWord;			// The original word
	static String wordNoPunct;	//The original word without punctuation
	static String word;			// The original word lower cased and without punctuation
	static String pigWord;		// The original word translated into pig latin
	static String reversedPigWord;	// The original word translated into pig latin and reversed
	static Scanner input;	
	static boolean isValid; 	// Determines if the set of characters can be a valid word to translate
	static boolean isTranslated;	// Determines if word has been translated into pig latin
	static int index;			// Location of a specific character in the string
	static String vowels = "aeiou";		//Vowels defaulted to a, e, i, o, and u, excluding y
	static String qu = "qu";	//Special case qu
	static String ay = "ay";	//Appended ay
	
	public static void main (String[] args)
	{
		input = new Scanner(System.in);
		
		prompt();
		
		while (!originalWord.toLowerCase().equals("q"))
		{
			process();
			output();
			outputReversed();
			
			System.out.println();
			prompt();
		}
	
	}
	
	/**
	* Prompt the user to enter a word to turn into Pig Latin.
	*/
	public static void prompt()
	{
		System.out.print("Enter a word to translate into Pig Latin or \"q\" to quit: ");
		originalWord = input.next();
	}
	
	/**
	* Checks the word and determines if it contains a vowel
	* and translates the word into PigLatin
	* and finds the word in pig latin reversed.
	*/	
	public static void process()
	{
		wordNoPunct = removePunct(originalWord);
		word = wordNoPunct.toLowerCase();	// The original word lower cased and without punctuation
		
		isTranslated = false;	// The word has not been translated into pig latin yet
		
		if(word.length() == 0)	// Checks if word without punctuation has any letters
		{
			isValid = false;	// Word has no vowels if has no letters
		}
		else
		{
			whatIsY();
			
			isValid();
			if(isValid)
			{
				quTranslate();
				if(!isTranslated)
				{
					vowelTranslate();
					if(!isTranslated)
					{
						consonantTranslate();
					}
				}
				
				String reversedHolder = "";
				for(int x = 1; x <= pigWord.length(); x++)
				{
					reversedHolder += pigWord.charAt(pigWord.length() - x);
				}
				reversedPigWord = reversedHolder;
				
				
				reversedPigWord = punctuationTranslate(reversedPigWord);
				pigWord = punctuationTranslate(pigWord);
				
			}
		}
	}
	
	/**
	* Print out the original word and the new word in Pig Latin.
	* If the word does not have a vowel, prints out "INVALID".
	*/
	public static void output()
	{
		
		// Print the word in Pig Latin
		if(!isValid)
		{
			System.out.println("INVALID");
		}
		else if(isValid)
		{
			System.out.print(originalWord + "  --->  " + pigWord);
			System.out.println();
		} 
	}
	
	/**
	* Reverse the output of the word.
	* Example:  At-whay? becomes Yahw-ta?
	*/
	public static void outputReversed()
	{
		// Print the word in Pig Latin reversed
				if(!isValid)
				{
					System.out.println("INVALID");
				}
				else if(isValid)
				{
					System.out.print(originalWord + "  --->  " + reversedPigWord);
					System.out.println();
				} 
	}
	
	/**
	 * Determines if the word can be translated into pig latin.
	 * No vowels --> cannot be translated into pig latin.
	 * @return true if the word can be translated into pig latin
	 */
	private static boolean isValid()
	{
		int index = 0;   		// The index begins at the first character in the word		
		isValid = true;  	// Assumes the word will have a vowel unless none is found
		char charCheck;
		
		do	//Check if the word contains a vowel
		{
			charCheck = word.charAt(index);
			//reached the end of word without hitting a vowel
				//not a valid word
			if(index == word.length() - 1 && !isVowel(charCheck))
			{
				isValid = false;
				pigWord = "INVALID";
			}
			index++;
			
			
		}while(!isVowel(charCheck) && isValid);
		
		return isValid;
	}
	
	/**
	 * Translates the word into pig latin.
	 * Used when the first letter of the word is a consonant.
	 * Move the leading consonants to the end of the word
	 * and append "ay".
	 */
	private static void consonantTranslate()
	{
		int index = 0;   		// The index begins at the first character in the word		
		char charCheck;
		String movedLetters = "";	// Letters that are moved back
		
		do	//finds the first vowel in the word
		{
			charCheck = word.charAt(index);
			if(!isVowel(charCheck))
			{
				index++;
			}
		}while(!isVowel(charCheck));
		
		movedLetters = word.substring(0, index);	//move the letters before the first vowel
		pigWord = word.substring(index) + movedLetters + ay;
		
		isTranslated = true;
	}
	
	/**
	 * Translates the word into pig latin
	 * Used when the first letter of the word is a vowel.
	 * If the first letter is a vowel
	 * then just append "way".
	 */
	private static void vowelTranslate()
	{
			if(isVowel(word.charAt(0))) //finds if the first letter in the word is a vowel
			{
				pigWord = word + "w" + ay;	//adds "way" at the end
				
				isTranslated = true;
			}
	}
	
	/**
	 * Determine if the word begins with a 'qu' and
	 * translates the word into pig latin.
	 * Used when the first vowel of the word is a 'u'
	 * and the letter before it is a 'q'.
	 * Also moves the 'u' to the end.
	 */
	private static void quTranslate()
	{
		//Only runs if word at least two characters
		if(!isVowel(word.charAt(0)) && word.length() >= 2)
		{
			int index = 1;   		// The index begins at the second character in the word		
			char charCheck;
			String movedLetters = "";	//Letters that are moved back
			
			do
			{
				charCheck = word.charAt(index);
				if(charCheck == 'u')	//Checks if the first vowel is a 'u'
				{
					if(word.charAt(index - 1) == 'q')	//Checks if a 'q' is before the 'u'
					{
						movedLetters = word.substring(0, index - 1) + qu;	//adds "qu" to the moved letters
						pigWord = word.substring(index + 1) + movedLetters + ay;
						
						isTranslated = true;
					}
				}
					
				index++;
			}while(!isVowel(charCheck));
		}
		
	}
	
	/**
	 * Determines whether 'y' counts as a vowel.
	 * Treats 'y' as a consonant if it's the first letter of the word.
	 * Treats 'y' as a vowel if it appears earlier than any other vowel.
	 */
	private static void whatIsY()
	{
		int index = 1;   		// The index begins at the first character in the word		
		char charCheck;
		vowels = "aeiou";	// Resets vowels default to a, e, i, o, and u, excluding y
		
		if(word.length() >= 2 && word.charAt(0) != 'y')
		{
			do
			{
				charCheck = word.charAt(index);
				if(charCheck == 'y')
				{
					// Counts y as a vowel along with a, e, i, o, and u
					vowels += "y";
				}
				index++;
			}while(!isVowel(charCheck) && index != word.length());
		}
	}
	
	
	/**
	 * Determines whether the word is capitalized
	 * and capitalizes the pigWord accordingly.
	 * @param word the word
	 * @return the word capitalized or lowercased accordingly
	 */
	private static String capitalTranslate(String word)
	{
		if(isCapital(wordNoPunct))	//ignoring punctuation, is the word capitalized
		{
			if(word.length() == 1)	//Only one letter --> capitalize that letter
			{
				word = word.toUpperCase();
			}
			else	//More than one letter --> only capitalize the first letter
			{
				word = word.substring(0, 1).toUpperCase()
						+ word.substring(1);
			}
		}
		return word;
	}
	
	/**
	 * Determines whether the word has punctuation
	 * and punctuates the pigWord accordingly.
	 * Also runs capitalTranslate() -- > capitalizes the pigWord accordingly.
	 * @param pigWord the word
	 * @return the word punctuated and capitalized or lowercased accordingly
	 */
	private static String punctuationTranslate(String pigWord)
	{
		int forwardIndex = 0;
		int backwardIndex = originalWord.length();
		String frontPunct = "";
		String backPunct = "";
		
		while(frontPunct(originalWord.substring(forwardIndex))
				&& originalWord.length() > forwardIndex)
		{
			frontPunct += originalWord.charAt(forwardIndex);
			forwardIndex++;
		}
		while(endPunct(originalWord.substring(0, backwardIndex))
				&& 0 < backwardIndex)
		{
			backPunct = originalWord.substring(backwardIndex - 1);
			backwardIndex--;
		}
		
		pigWord = capitalTranslate(pigWord);
		pigWord = frontPunct + pigWord + backPunct;
		return pigWord;
	}
	
	/**
	 * Determines whether the word has punctuation
	 * and removes punctuation accordingly.
	 * @param originalWord the word
	 * @return the word without punctuation
	 */
	private static String removePunct(String originalWord)
	{
		int forwardIndex = 0;
		int backwardIndex = originalWord.length();
		String wordNoPunct;
		
		while(originalWord.length() > forwardIndex
				&& frontPunct(originalWord.substring(forwardIndex)))
		{
			forwardIndex++;
		}
		while(0 < backwardIndex
				&& endPunct(originalWord.substring(0, backwardIndex)))
		{
			backwardIndex--;
		}

		if(forwardIndex > backwardIndex)
		{
			wordNoPunct = "";
		}
		else
		{
			wordNoPunct = originalWord.substring(forwardIndex, backwardIndex);
		}
		
		return wordNoPunct;
	}
	
	
	/**
	* Determines if the character is a vowel.
	* @param letter the letter
	* @return true if letter is a vowel
	*/
	private static boolean isVowel(char letter)
	{
		//not a, e, i, o, or u, and sometimes not y
		return vowels.indexOf(letter) != -1;
	}

	
	/**
	* Determine if the first letter is capitalized
	* @param str the word
	* @return true if the first letter is capitalized
	*/
	private static boolean isCapital(String str)
	{
		char firstLetter = str.charAt(0);
		//not A-Z
		return firstLetter >= 'A' && firstLetter <= 'Z';
	}
	

	/**
	* Determine if there is punctuation at the beginning of the word
	* @param str the word
	* @return true if there is punctuation at the beginning of the word
	*/
	private static boolean frontPunct(String str)
	{
		//starting from beginning
		char firstChar = str.charAt(0);
		//not a letter
			//a-z and not A-Z
		return ((!(firstChar >= 'A' && firstChar <= 'Z'))
			&& (!(firstChar >= 'a' && firstChar <= 'z')));
	}

	
	/**
	* Determine if there is punctuation at the end of the word
	* @param str the word
	* @return true if there is punctuation at the end of the word
	*/
	private static boolean endPunct(String str)
	{
		//starting from end
		char lastChar = str.charAt(str.length() - 1);
		//not a letter
			//a-z and not A-Z
		return ((!(lastChar >= 'A' && lastChar <= 'Z'))
			&&(!(lastChar >= 'a' && lastChar <= 'z')));
	}
}