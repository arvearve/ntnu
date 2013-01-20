package oving9;

import junit.framework.TestCase;

public class HighscoreListTest extends TestCase {

	private static final int MAX_SIZE = 3;

	private HighscoreList highscoreList;
	
	private int start, end;
	private ObservableList observedList;

	private SokobanResult[] sokobanResults;
	
	protected void setUp() throws Exception {
		super.setUp();
		highscoreList = new HighscoreList(MAX_SIZE);
		sokobanResults = new SokobanResult[]{
			new SokobanResult("Hallvard", 1),
			new SokobanResult("Hallvard", 3),	
			new SokobanResult("Hallvard", 5),	
			new SokobanResult("Hallvard", 7),	
			new SokobanResult("Hallvard", 9),	
		};
	}

	private ListListener listListener = null;

	private void addListListener() {
		if (listListener == null) {
			listListener = new ListListener() {
				public void listChanged(ObservableList list, int start, int end) {
					HighscoreListTest.this.observedList = list;
					HighscoreListTest.this.start += start;
					HighscoreListTest.this.end += end;
				}
			};			
		}
		highscoreList.addListListener(listListener);
	}

	private void testListListener(ObservableList observableList, int start, int end) {
		assertEquals(observableList, observedList);
		assertEquals(start, this.start);
		assertEquals(end, this.end);
		observedList = null;
	}

	private void testAddResultOrdered(int count) {
		addListListener();
		int start = 0;
		for (int i = 0; i < count; i++) {
			highscoreList.addResult(sokobanResults[i]);
			if (i < MAX_SIZE) {
				start += i;
			}
			testListListener(i < MAX_SIZE ? highscoreList : null, start, start);
		}
	}

	private void testAddResultReversed(int count) {
		addListListener();
		int end = 0;
		for (int i = count - 1; i >= 0; i--) {
			int j = count - i - 1;
			highscoreList.addResult(sokobanResults[i]);
			end += (j < MAX_SIZE ? j : 2);
			testListListener(highscoreList, 0, end);
		}
	}
	
	public void testAddResultOrderedLessThanMaxSize() {
		testAddResultOrdered(MAX_SIZE);
	}
	public void testAddResultReversedLessThanMaxSize() {
		testAddResultReversed(MAX_SIZE);
	}

	public void testAddResultOrderedMoreThanMaxSize() {
		testAddResultOrdered(sokobanResults.length);
	}
	public void testAddResultReversedMoreThanMaxSize() {
		testAddResultReversed(sokobanResults.length);
	}
	
	public void testIterator() {
		for (int i = 0; i < sokobanResults.length - 1; i++) {
			highscoreList.addResult(sokobanResults[i]);
		}
		int i = 0;
		for (Comparable result : highscoreList) {
			assertEquals(sokobanResults[i], result);
			i++;
		}
	}
}
