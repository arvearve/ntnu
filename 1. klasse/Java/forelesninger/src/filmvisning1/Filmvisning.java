package filmvisning1;

public class Filmvisning {
	
	String filmtittel;
	String sal;
	int alder;
	String nasjonalitet;
	String tid;
	int pris;
	
	@Override
	public String toString() {
		return String
				.format("Filmvisning [filmtittel=%s, sal=%s, alder=%s, nasjonalitet=%s, tid=%s]",
						filmtittel, sal, alder, nasjonalitet, tid);
	}

	public String getFilmtittel() {
		return filmtittel;
	}

	public void setFilmtittel(String filmtittel) {
		this.filmtittel = filmtittel;
	}

	public String getSal() {
		return sal;
	}

	public void setSal(String sal) {
		this.sal = sal;
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

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public int getPris() {
		return pris;
	}

	public void setPris(int pris) {
		this.pris = pris;
	}
}
