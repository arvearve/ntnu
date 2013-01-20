package grensesnitt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import acm.program.ConsoleProgram;

public class HtmlPage extends ConsoleProgram {
	
	private List<Person1> personer;
	
	public void init() {
		personer = new ArrayList<Person1>(Arrays.asList(
				new Person1("Ola Nordmann", 42),
				new Person1("Kari Nordmann", 39),
				new Person1("Hallvard Tr¾tteberg", 45)
		));
	}
	
	public void run() {
		System.out.println("<html>");
		System.out.println("	<head><title>Personer</title></head>");
		System.out.println("	<body>");
		HtmlTablePrinter table = new HtmlTablePrinter();
		table.printHtml(personer, System.out);
		System.out.println("	</body>");
		System.out.println("</html>");
	}
}
