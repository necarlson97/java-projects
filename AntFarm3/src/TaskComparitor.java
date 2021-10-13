import java.util.Comparator;

public class TaskComparitor implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		if(!(o1 instanceof Task) || !(o2 instanceof Task)) return 0;
		Task t1 = (Task) o1;
		Task t2 = (Task) o2;
		return (t1.priority - t2.priority);
	}

}
