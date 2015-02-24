import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class MaskOutWords {
	
	private static final String HIDE_SYMBOL = "*";
	
	public static String mask(String[] words, String text) {

		for(String word : words) {
			Pattern p = Pattern.compile("\\b(?i)" + word + "\\b");
			Matcher m = p.matcher(text);
			if(m.find()) {
				text = m.replaceAll(StringUtils.repeat(HIDE_SYMBOL, word.length()));
			}	
		}
		return text;
	}
	
	public static void main(String[] args) {

	    String[] words = {"yesterday", "Dog", "food", "walk"};
	    String text = "Yesterday, I took my dog for a walk.\n It was crazy! My dog wanted only food.";

		System.out.print(MaskOutWords.mask(words, text));
	}

}
