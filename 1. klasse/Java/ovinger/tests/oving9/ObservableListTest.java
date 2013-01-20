package oving9;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class ObservableListTest extends TestCase {

	private class ObservableArrayList extends ObservableList {

		public List arrayList = new ArrayList();
		
		@Override
		protected List getList() {
			return arrayList;
		}

		protected void fireListChanged(int start, int end) {
			super.fireListChanged(start, end);
		}
	}
	
	private ObservableArrayList observableList;
	
	private int start, end;
	private ObservableList observedList;
	
	protected void setUp() throws Exception {
		super.setUp();
		observableList = new ObservableArrayList();
	}

	private ListListener listListener = null;

	private void addListListener() {
		if (listListener == null) {
			listListener = new ListListener() {
				public void listChanged(ObservableList list, int start, int end) {
					ObservableListTest.this.observedList = list;
					ObservableListTest.this.start += start;
					ObservableListTest.this.end += end;
				}
			};			
		}
		observableList.addListListener(listListener);
	}
	private void removeListListener() {
		if (listListener != null) {
			observableList.removeListListener(listListener);
			listListener = null;
		}
	}
	
	public void testGetList() {
		assertTrue(observableList.getList() == observableList.arrayList);
	}

	public void testSize() {
		for (int i = 0; i < 5; i++) {
			observableList.arrayList.add(i);
			assertEquals(i + 1, observableList.size());
		}
		observableList.arrayList.clear();
		assertEquals(0, observableList.size());
	}
	
	private void testListListener(ObservableList observableList, int start, int end) {
		assertEquals(observableList, observedList);
		assertEquals(start, this.start);
		assertEquals(end, this.end);
		observedList = null;
	}
	
	public void testFireListChanged() {
		addListListener();
		observableList.fireListChanged(1, 3);
		testListListener(observableList, 1, 3);
	}

	public void testAddListListener() {
		observableList.fireListChanged(1, 3);
		testListListener(null, 0, 0);
		addListListener();
		observableList.fireListChanged(1, 3);
		testListListener(observableList, 1, 3);
		addListListener();
		observableList.fireListChanged(1, 3);
		testListListener(observableList, 2, 6);
	}
	
	public void testRemoveListListener() {
		addListListener();
		removeListListener();
		observableList.fireListChanged(1, 3);
		testListListener(null, 0, 0);
	}
	
	public void testAddElement() {
		observableList.arrayList.add(0);
		observableList.arrayList.add(2);
		addListListener();

		observableList.addElement(1, 1);
		assertEquals(3, observableList.arrayList.size());
		assertEquals(0, observableList.arrayList.get(0));
		assertEquals(1, observableList.arrayList.get(1));
		assertEquals(2, observableList.arrayList.get(2));
		testListListener(observableList, 1, 2);

		observableList.addElement(0, -1);
		assertEquals(4, observableList.arrayList.size());
		assertEquals(-1, observableList.arrayList.get(0));
		assertEquals(0, observableList.arrayList.get(1));
		assertEquals(1, observableList.arrayList.get(2));
		assertEquals(2, observableList.arrayList.get(3));
		testListListener(observableList, 1, 5);

		observableList.addElement(4, 3);
		assertEquals(5, observableList.arrayList.size());
		assertEquals(-1, observableList.arrayList.get(0));
		assertEquals(0, observableList.arrayList.get(1));
		assertEquals(1, observableList.arrayList.get(2));
		assertEquals(2, observableList.arrayList.get(3));
		assertEquals(3, observableList.arrayList.get(4));
		testListListener(observableList, 5, 9);
	}

	public void testRemoveElement() {
		observableList.arrayList.add(0);
		observableList.arrayList.add(1);
		observableList.arrayList.add(2);
		addListListener();

		observableList.removeElement(1);
		assertEquals(2, observableList.arrayList.size());
		assertEquals(0, observableList.arrayList.get(0));
		assertEquals(2, observableList.arrayList.get(1));
		testListListener(observableList, 1, 2);
		
		observableList.removeElement(1);
		assertEquals(1, observableList.arrayList.size());
		assertEquals(0, observableList.arrayList.get(0));
		testListListener(observableList, 2, 3);
		
		observableList.removeElement(0);
		assertEquals(0, observableList.arrayList.size());
		testListListener(observableList, 2, 3);
	}
}
