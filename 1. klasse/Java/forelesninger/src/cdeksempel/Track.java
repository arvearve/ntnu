package cdeksempel;

public class Track {

	final private CD cd;
	private String title;
	private int length, pause;
	
	public Track(CD cd) {
		this.cd = cd;
	}
	
	@Override
	public String toString() {
		return String.format("Track [title=%s, length=%s]", title, length);
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		if (cd.isValidTitle(title)) {
			this.title = title;
		}
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		if (length > 0) {
			int diff = length - this.length;
			if (cd.getLength() + diff <= cd.getMaxLength()) {
				this.length = length;
			}
		}
	}

	public int getPause() {
		return pause;
	}
	public void setPause(int pause) {
		if (length >= 0) {
			this.pause = pause;
		}
	}
	
	public int getPlayLength() {
		return length + pause;
	}
}
