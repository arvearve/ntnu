package filmvisning1;

import acm.program.ConsoleProgram;

public class Kinoprogram extends ConsoleProgram {

	public void run() {
		Filmvisning fv1 = new Filmvisning();
		fv1.setFilmtittel("Alexander");
		fv1.setSal("Nova3");
		fv1.setAlder(15);
		fv1.setNasjonalitet("USA");
		fv1.setTid("20:00");

		Filmvisning fv2 = new Filmvisning();
		fv2.setFilmtittel("Alexander");
		fv2.setSal("Prinsen4");
		fv2.setAlder(15);
		fv2.setNasjonalitet("USA");
		fv2.setTid("17:00");
		
		Filmvisning fv3 = new Filmvisning();
		fv2.setFilmtittel("Bad Santa");
		fv3.setSal("Prinsen2");
		fv3.setAlder(11);
		fv3.setNasjonalitet("USA");
		fv3.setTid("18:50");
		
		println("Filmvisning 1: " + fv1);
		println("Filmvisning 2: " + fv3);
		println("Filmvisning 3: " + fv3);
	}
}
