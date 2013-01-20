package oving9;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservableList {
	ArrayList<ListListener> Listeners = new ArrayList<ListListener>();

	public void addListListener(ListListener newListener) {
		if (!this.Listeners.contains(newListener)) {
			Listeners.add(newListener);
		}
	}

	public void removeListListener(ListListener item) {
		Listeners.remove(item);
	}

	protected void fireListChanged(int lowestIndex, int highestIndex) {
		for (ListListener l : Listeners) {
			l.listChanged(this, lowestIndex, highestIndex);
		}
	}

	protected abstract List getList();

	public int size() {
		return getList().size();
	}

	protected void addElement(int index, Object element) {
		if (index >= getList().size()) {
			getList().add(element);
		} else {
			getList().add(index, element);
		}
		fireListChanged(index, getList().size() - 1);
	}

	protected void removeElement(int index) {
		getList().remove(index);
		fireListChanged(index, getList().size());
	}

}
