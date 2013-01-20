package grensesnitt;

import java.io.PrintStream;
import java.util.List;

public class HtmlTablePrinter {
	
	public void printHtml(List<Person1> elements, PrintStream out) {
		out.println("<table>");
		out.println("	<tr><th>Element</th></tr>");
		for (int i = 0; i < elements.size(); i++) {
			out.println("	<tr><td>");
			Object element = elements.get(i);
			out.print(element.toString());
			out.println("	</td></tr>");
		}
		out.println("</table>");
	}
}
