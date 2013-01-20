package oving9;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HighscoreList extends ObservableList implements Iterable<Comparable>{
	private int maxSize = 0;
	private ArrayList<Comparable> resultList;
	
	public HighscoreList(int maxSize){
		this.maxSize = maxSize;
		this.resultList = new ArrayList<Comparable>();
	}
	
	@Override
	protected List getList() {
		if(resultList.size() > maxSize){
			return resultList.subList(0, maxSize);
		}
		else{
			return resultList;
		}
	}
	
	public void addResult(Comparable result){
		if(resultList.contains(result)){
			return;
		}
		int index = 0;
		for(Comparable r:resultList){
			if(r.toString() == result.toString()){
				return; 
			}
			if (result.compareTo(r) >= 0){
				index++;
			}
		}
		if(index < maxSize){
		addElement(index, result);
		}
		
	}
	
	@Override
	public Iterator<Comparable> iterator(){
		if(resultList.size()==0){
			return null;
		}
		return resultList.iterator();
	}
}
