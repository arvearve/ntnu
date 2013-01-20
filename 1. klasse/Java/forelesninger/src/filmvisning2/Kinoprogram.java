package filmvisning2;

import acm.program.ConsoleProgram;

public class Kinoprogram extends ConsoleProgram {

	public void run() {
		Film film1 = new Film("Alexander");
		film1.setAlder(15);
		film1.setNasjonalitet("USA");
		
        Filmvisning fv1 = new Filmvisning(film1);
        fv1.setSal("Nova3");

        Filmvisning fv2 = new Filmvisning(film1);
        fv2.setSal("Prinsen4");

        Film film2 = new Film("Bad Santa");
        film1.setAlder(11);
        film1.setNasjonalitet("USA");
        
        Filmvisning fv3 = new Filmvisning(film2);
        fv3.setSal("Prinsen2");
		
		println("Filmvisning 1: " + fv1);
		println("Filmvisning 2: " + fv2);
		println("Filmvisning 3: " + fv3);
	}
}
