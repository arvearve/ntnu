package oving3;

import java.util.ArrayList;

import acm.program.ConsoleProgram;

public class Kongefamilien extends ConsoleProgram {
	public ArrayList<Person> createRoyalFamily() {

		Person harald = new Person("Harald");
		Person sonja = new Person("Sonja");
		Person haakon = new Person("Haakon");
		haakon.setMother(sonja); // Uncomment line 29 and 38 in Person.java to
									// automatically set children.
		haakon.setFather(harald);

		Person metteMarit = new Person("Mette-Marit");

		Person ingridAlexandra = new Person("Ingrid Alexandra");
		ingridAlexandra.setMother(metteMarit);
		ingridAlexandra.setFather(haakon);

		Person sigurdMagnus = new Person("Sigurd Magnus");
		sigurdMagnus.setMother(metteMarit);
		sigurdMagnus.setFather(haakon);

		Person martha = new Person("MŠrtha");
		martha.setMother(sonja);
		martha.setFather(harald);

		Person ari = new Person("Ari");
		Person maudAngelica = new Person("Maud Angelica");
		maudAngelica.setMother(martha);
		maudAngelica.setFather(ari);

		Person leahIsadora = new Person("Leah Isadora");
		leahIsadora.setMother(martha);
		leahIsadora.setFather(ari);
		Person emmaTaluah = new Person("Emma Taluah");
		emmaTaluah.setMother(martha);
		emmaTaluah.setFather(ari);

		ArrayList<Person> Familie = new ArrayList<Person>();
		Familie.add(harald);
		Familie.add(sonja);
		Familie.add(haakon);
		Familie.add(metteMarit);
		Familie.add(ingridAlexandra);
		Familie.add(sigurdMagnus);
		Familie.add(martha);
		Familie.add(ari);
		Familie.add(maudAngelica);
		Familie.add(leahIsadora);
		Familie.add(emmaTaluah);

		return Familie;
	}

	public void run() {

		println(createRoyalFamily());

	}
}
