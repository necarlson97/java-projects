import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

	private class Point {
		int x;
		int y;
		String name;
		
		List<Point> path = new LinkedList<Point>();
		
		Point(int _x, int _y, String _name) {
			x =_x;
			y = _y;
			name = _name;
		}
		
		public Point plus(Point p) {
			Point newPoint = new Point(x + p.x, y + p.y, p.name);
			newPoint.path.addAll(path);
			newPoint.path.add(this);
			return newPoint;
		}
		
		public int distance(Point p) {
			return Math.abs(p.x - x) + Math.abs(p.y - y);
		}
		
		@Override
		public String toString() {
			return "("+x+","+y+") "+name; 
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		private Solution getOuterType() {
			return Solution.this;
		}
		
	}
	
	private class DistanceToEndComparator<Point> implements Comparator {

		Solution.Point end;
		DistanceToEndComparator(Point _end) {
			end = (Solution.Point) _end;
		}
		
		@Override
		public int compare(Object o1, Object o2) {
			Solution.Point p1 = (Solution.Point) o1;
			Solution.Point p2 = (Solution.Point) o2;
			int c = end.distance(p1) - end.distance(p2);
			if (c == 0) {
				String s = "UL, UR, R, LR, LL, L";
				c = s.indexOf(p2.name) - s.indexOf(p1.name);
			}
			return c;
		}
		
	}

	static Solution s = new Solution();
	static Point[] relativeMoves = {
			s.new Point(-1, -2, "UL"),
			s.new Point( 1, -2, "UR"),
			s.new Point( 2,  0, "R"),
			s.new Point( 1,  2, "LR"),
			s.new Point(-1,  2, "LL"),
			s.new Point(-2,  0, "L")
	};
	static void printShortestPath(int n, int i_start, int j_start, int i_end, int j_end) {
		
		List<Point> queue = new LinkedList<Point>();
		Set<Point> visited = new HashSet<Point>();
		
		
		Point start = s.new Point(j_start, i_start, "start");
		Point end = s.new Point(j_end, i_end, "end");
		queue.add(start);
		visited.add(start);
		Point current = start;
		
		while(!queue.isEmpty()) {	
			
			current = queue.remove(0);
			visited.add(current);
			
			if(current.equals(end)) break;
			
			List<Point> moves = new ArrayList<Point>();
			
			for(Point p : relativeMoves) {
				Point newMove = current.plus(p);
				if(newMove.x >= n || newMove.y >= n || newMove.x < 0 || newMove.y < 0 ||
						visited.contains(newMove) || queue.contains(newMove)) continue;
				moves.add(newMove);
			}
			
			Collections.sort(moves, s.new DistanceToEndComparator<Point>(end));
			
			queue.addAll(moves);
			
		}
		
		if(!current.equals(end)) System.out.println("Impossible");
		else {
			current.path.add(current);
			current.path.remove(0);
			String prnt = current.path.size()+"\n";
			for(Point p : current.path) {
				prnt += p.name+" ";
			}
			System.out.println(prnt);
		}
		
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int i_start = in.nextInt();
		int j_start = in.nextInt();
		int i_end = in.nextInt();
		int j_end = in.nextInt();
		printShortestPath(n, i_start, j_start, i_end, j_end);
		in.close();
	}
}
