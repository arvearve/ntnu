package testing;

public class Person {

	private Person partner = null;

	public Person getPartner() {
		return partner;
	}

	public void setPartner(Person partner) {
		this.partner = partner;
		partner.partner = this;
	}
}
