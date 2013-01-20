package grensesnitt;

import java.io.PrintStream;
import java.util.List;

public class HtmlListPrinter {
	
	public void printHtml(List<Person1> elements, PrintStream out) {
		out.println("<ul>");
		for (int i = 0; i < elements.size(); i++) {
			out.println("	<li>");
			Object element = elements.get(i);
			out.print(element.toString());
			out.println("	</li>");
		}
		out.println("</ul>");
	}
}
