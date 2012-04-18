package sokoban2;

// This enum can be used, but doesn't have to.
public enum Direction {
	NORTH(0, -1),
	SOUTH(0, 1),
	WEST(-1, 0),
	EAST(1, 0);
	
	private final int dx, dy;
	
	Direction(int dx, int dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public int dx(){
		return dx;
	}
	
	public int dy(){
		return dy;
	}
	
	public Direction opposite(){
		switch (this){
		case NORTH: return SOUTH;
		case SOUTH: return NORTH;
		case WEST: return EAST;
		case EAST: return WEST;
		
		default: return null; // Should/will never happen
		}
	}
}
