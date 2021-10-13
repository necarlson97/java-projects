
public class Fall extends Action{

	public Fall(String givenName, Fighter fighter) {
		super(givenName, fighter);
		frameStates[frameStates.length-1].actionName += " repeat frame";
	}

}
