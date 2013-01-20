package grensesnitt;

public class Person2 implements Person {
	
	private String fulltNavn = "";

	public String getFulltNavn() {
		return fulltNavn;
	}

	public void setFulltNavn(String fulltNavn) {
		this.fulltNavn = fulltNavn;
	}

	public String getFornavn() {
		int pos = fulltNavn.indexOf(' ');
		return (pos < 0 ? fulltNavn : fulltNavn.substring(0, pos));
	}

	public void setFornavn(String fornavn) {
		fulltNavn = fornavn + " " + getEtternavn();
	}

	public String getEtternavn() {
		int pos = fulltNavn.indexOf(' ');
		return (pos < 0 ? null : fulltNavn.substring(pos + 1));
	}

	public void setEtternavn(String etternavn) {
		fulltNavn = getFornavn() + " " + etternavn;
	}
}
