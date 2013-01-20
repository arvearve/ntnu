package iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import acm.program.ConsoleProgram;

class StringLoopData {
	String s;
	int i;
	StringLoopData(String s, int start) {
		this.s = s;
		i = start;
	}
	boolean anotherLoop() {
		return 
	}
}

public class IteratorExample extends ConsoleProgram {

	public void run() {
		StringLoopData ld = new StringLoopData("Iterator", 0);
		for (; ld.i < ld.s.length(); ld.i++) {
			char c = ld.s.charAt(ld.i);
			print(c);
		}
		println();

		List<Character> charList = new ArrayList<Character>(Arrays.asList('I', 't', 'e', 'r', 'a', 't', 'o', 'r'));
		for (int i = 0; i < charList.size(); i++) {
			char c = charList.get(i);
			print(c);
		}
		println();
		
		char[] charArray = "Iterator".toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			print(c);
		}
		println();
	}
}
