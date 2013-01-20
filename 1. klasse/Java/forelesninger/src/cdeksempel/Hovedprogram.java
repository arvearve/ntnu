package cdeksempel;

import acm.program.ConsoleProgram;

public class Hovedprogram extends ConsoleProgram {

	public void run() {
		CD cd1 = new CD();
		cd1.setTitle("cd1");
		println(cd1);
		Track t1 = cd1.createTrack("Track 1");
		t1.setLength(120);
		println(t1);
		println(cd1);
		
		Track t2 = cd1.createTrack("Track 2");
		t2.setLength(70 * 60);
		println(t2);
		println(cd1);

		Track t3 = cd1.createTrack("Track 3");
		t3.setLength(1); // length will not be set, since maximum length of CD will be exceeded
		println(t3);
		println(cd1);
	}
}
