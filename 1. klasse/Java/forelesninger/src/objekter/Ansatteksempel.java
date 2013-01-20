package objekter;

import acm.program.ConsoleProgram;

public class Ansatteksempel extends ConsoleProgram {
	public void run() {
		Ansatt a1 = new Ansatt("Hallvard");
		Ansatt a2 = new Ansatt("Trond", 213);
		a1.kollega = a2;
		a2.kollega = a1;
		println(a1);
		println(a2);
	}
}
