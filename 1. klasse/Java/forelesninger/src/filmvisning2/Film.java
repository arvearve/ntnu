package filmvisning2;

public class Film {
	
	final String tittel;
	int alder;
	String nasjonalitet;

	@Override
	public String toString() {
		return String.format("Film [tittel=%s, alder=%s, nasjonalitet=%s]",
				tittel, alder, nasjonalitet);
	}

	public Film(String tittel) {
		this.tittel = tittel;
	}

	public int getAlder() {
		return alder;
	}

	public void setAlder(int alder) {
		this.alder = alder;
	}

	public String getNasjonalitet() {
		return nasjonalitet;
	}

	public void setNasjonalitet(String nasjonalitet) {
		this.nasjonalitet = nasjonalitet;
	}

	public String getTittel() {
		return tittel;
	}
}
