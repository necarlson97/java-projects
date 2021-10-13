
public class Task {
	public String taskName;
	public String targetName;
	public Point target;
	public int priority;
	
	Task(String taskName, int p){
		this.taskName = taskName;
		this.targetName = "";
		this.priority = p;
	}
	
	Task(String taskName, String targetName, int p){
		this.taskName = taskName;
		this.targetName = targetName;
		this.priority = p;
	}
	
	Task(String taskName, Point target, int p){
		this.taskName = taskName;
		this.targetName = "";
		this.target = target;
		this.priority = p;
	}
	
	public String toString() {
		String str = "Task: " + taskName;
		if(target != null) str += " to "+target;
		if(!targetName.equals("")) str += " at "+targetName;
		str += ", Priority: "+priority;
		return str;
	}
}
