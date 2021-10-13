import java.util.Comparator;

public class PathPointComparitor implements Comparator {
	

	@Override
	public int compare(Object o1, Object o2) {
		if(!(o1 instanceof PathPoint) || !(o2 instanceof PathPoint)) return 0;
		PathPoint pp1 = (PathPoint) o1;
		PathPoint pp2 = (PathPoint) o2;
		return (pp1.myG+pp1.prevG + pp1.myH) - (pp2.myG+pp2.prevG + pp2.myH);
	}

}
