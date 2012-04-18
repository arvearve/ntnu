package oving9;
 
/*
 * @startuml
 * interface "Comparable<T>" as Comparable
 * Comparable <|.. SokobanResult: T=SokobanResult 
 * Comparable: int compareTo(T)
 * 
 * SokobanResult: int getMoveCount()
 * SokobanResult: int compareTo(SokobanResult)
 * @enduml
 */
 
public class SokobanResult implements Comparable<SokobanResult> {
	
	private final String name;
	private final int moveCount;
 
	public SokobanResult(String name, int moveCount) {
		this.name = name;
		this.moveCount = moveCount;
	}
 
	public String toString() {
		return name + ": " + moveCount;
	}
	
	public String getName() {
		return name;
	}
	public int getMoveCount() {
		return moveCount;
	}
 
	public int compareTo(SokobanResult other) {
		return this.moveCount - other.moveCount;
	}
}