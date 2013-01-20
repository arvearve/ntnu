package cdeksempel;

import java.util.ArrayList;
import java.util.List;

public class CD {
	
	private String title;
	private List<Track> tracks = new ArrayList<Track>();
	
	@Override
	public String toString() {
		return String.format("CD [title=%s length=%s]", title, getLength());
	}

	public int getMaxLength() {
		return 72 * 60;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		if (isValidTitle(title)) {
			this.title = title;
		}
	}

	boolean isValidTitle(String title) {
		for (int i = 0; i < title.length(); i++) {
			char c = title.charAt(i);
			if (! (Character.isLetter(c) || Character.isDigit(c) || " -,".indexOf(c) >= 0)) {
				return false;
			}
		}
		return true;
	}
	
	// tracks methods
	
	public int getTrackCount() {
		return tracks.size();
	}
	
	public Track getTrack(int i) {
		return tracks.get(i);
	}
	
	public Track createTrack(String title) {
		Track track = new Track(this);
		addTrack(track);
		return track;
	}
	
	private void addTrack(Track track) {
		if (! tracks.contains(track)) {
			if (getLength() + track.getLength() <= getMaxLength()) {
				tracks.add(track);
			}
		}
	}
		
	public void removeTrack(Track track) {
		tracks.remove(track);
	}
	
	public int getLength() {
		int sum = 0;
		for (int i = 0; i < tracks.size(); i++) {
			Track track = tracks.get(i);
			sum = sum + track.getLength();
		}
		return sum;
	}
	
	public int getPlayLength() {
		int sum = 0;
		for (int i = 0; i < tracks.size(); i++) {
			Track track = tracks.get(i);
			sum = sum + track.getPlayLength();
		}
		return sum;
	}
}
