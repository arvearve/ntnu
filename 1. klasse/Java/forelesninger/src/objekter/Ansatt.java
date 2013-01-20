package objekter;

public class Ansatt {
	
	String navn = null;
	int kontor = 0;
	Ansatt kollega = null;
	
	public Ansatt(String navn) {
		this.navn = navn;
	}
	public Ansatt(String navn, int kontor) {
		this.navn = navn;
		this.kontor = kontor;
	}

	public String toString() {
		return "Ansatt med navn " + navn + " og kontornr " + kontor;
	}
	
	public String getNavn() {
		return navn;
	}
	
	public void setNavn(String navn) {
		this.navn = navn;
	}
}
