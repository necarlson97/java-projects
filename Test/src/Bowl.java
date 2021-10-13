
public class Bowl {
	private double weight;
	private boolean empty;
	private String origin; // country of manufacture

	public Bowl(double w, boolean e, String origin) {
		weight = w;
		empty = e;
		this.origin = origin;
	}

	public double getWeight() {
		return weight;
	}

	public boolean getEmpty() {
		return empty;
	}

	public String getOrigin() {
		return origin;
	}

	public void setEmpty(boolean emptyStatus) {
		empty = emptyStatus;
	}

	public String toString() {
		return ("from " + origin + " weight: " + weight);
	}

}