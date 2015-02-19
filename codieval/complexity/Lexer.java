package codieval.complexity;
import java.util.ArrayList;
import java.util.Arrays;

public class Lexer extends ArrayList {
	public Lexer(String code, char[] delims) {
		String lexeme = "";
		for(char c : code.toCharArray()) {
			if(arrayContains(delims, c)) {
				this.add(lexeme);
				this.add(c+"");
				lexeme = "";
			} else {
				lexeme += c;
			}
		}
		if(lexeme.length() > 0)
			this.add(lexeme);
	}
	public boolean arrayContains(char[] array, char c) {
		for(char c2 : array) {
			if(c == c2) {
				return true;
			}
		}
		return false;
	}
	public void add(String s) {
		if(s.trim().length() > 0)
			super.add(s);
	}
}