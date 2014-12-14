package pakkuman;

import java.awt.Point;

public class Node {
	private Point   _point   = null;
	private boolean _monster = false;
	private boolean _candy   = false;
	private boolean _start   = false;
	private boolean _end     = false;
	
	
	public Node( Point point ) {
		_point = point;
	}
	public Point getPoint() {
		return _point;
	}
	public boolean isMonster() {
		return _monster;
	}
	public boolean isCandy() {
		return _candy;
	}
	public boolean isStart() {
		return _start;
	}
	public boolean isEnd() {
		return _end;
	}
	public void isMonster( boolean monster) {
		_monster = monster;
	}
	public void isCandy( boolean candy) {
		_candy = candy;
	}
	public void isStart( boolean start ) {
		_start = start;
	}
	public void isEnd( boolean end ) {
		_end = end;
	}
}
