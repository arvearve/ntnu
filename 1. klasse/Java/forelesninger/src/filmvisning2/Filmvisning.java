package filmvisning2;

/*
 * @startuml
	class Film {
		String tittel
		int alder
		String nasjonalitet
	}
  	class Filmvisning {
		String sal
		String tid
		int pris
	}
	Filmvisning "Film" --> Film
 * @enduml
 */
public class Filmvisning {

	Film film;
	String sal;
	String tid;
	int pris;
	
	@Override
	public String toString() {
		return String
				.format("Filmvisning [filmtittel=%s, sal=%s, alder=%s, nasjonalitet=%s, tid=%s]",
						film.tittel, sal, film.alder, film.nasjonalitet, tid);
	}

	public Filmvisning(Film film) {
		this.film = film;
	}
	
	public String getFilmtittel() {
		return film.getTittel();
	}
	
    public void setSal(String sal) {
        this.sal = sal;
    }
}
