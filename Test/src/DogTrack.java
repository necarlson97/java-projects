//Nils Carlson

public class DogTrack {
	
	// int values for the track size, as well as the dogs position on it
	private int trackSize;
	private int roverPos = 0; 
	private int spotPos = 0; 
	private int fidoPos = 0;
	
	// String varables for printing a track
	private String track = "";
	private String tempTrack = "";
	
	
	public DogTrack(int trackSize){
		this.trackSize = trackSize;
		for( int i = 0; i< trackSize; i++) track += "o";
		
		System.out.println();
		showTrack();
	}
	
	public void showTrack(){
		tempTrack = track.substring(0,roverPos) + 'R' + track.substring(roverPos + 1, trackSize);
		System.out.println(tempTrack);
		
		tempTrack = track.substring(0,spotPos) + 'S' + track.substring(spotPos + 1, trackSize);
		System.out.println(tempTrack);
		
		tempTrack = track.substring(0,fidoPos) + 'F' + track.substring(fidoPos + 1, trackSize);
		System.out.println(tempTrack);
		
		tempTrack = track;
		System.out.println(tempTrack.replaceAll("o", "-"));
	}
	
	public int spin(){
		return (int) (1 + Math.random()*5);
	}
	
	public boolean isOver(){
		if(roverPos == trackSize-1) return true;
		if(spotPos == trackSize-1) return true;
		if(fidoPos == trackSize-1) return true;
		else return false;
	}
	
	public void showWinners(){
		if(roverPos == trackSize-1) System.out.println("Rover Wins!");
		if(spotPos == trackSize-1) System.out.println("Spot Wins!");
		if(fidoPos == trackSize-1) System.out.println("Fido Wins!");
	}
	
	public void playGame(){
		
		
		System.out.println();
		
		int add = 0;
		while (roverPos < trackSize-1 && spotPos < trackSize-1 && fidoPos < trackSize-1){
			add = spin();
			roverPos += add;
			if(roverPos == (int) trackSize / 3 || roverPos == (int) (2*trackSize)/3) roverPos -= add-1;
			if(roverPos > trackSize-1) roverPos = trackSize-1;
			if(roverPos < 0) roverPos = 0;
			
			add = spin();
			spotPos += add;
			if(spotPos == (int) trackSize / 3 || spotPos == (int) (2*trackSize)/3) spotPos -= add-1;
			if(spotPos > trackSize-1) spotPos = trackSize-1;
			if(spotPos < 0) spotPos = 0;
			
			add = spin();
			fidoPos += add;
			if(fidoPos == (int) trackSize / 3 || fidoPos == (int) (2*trackSize)/3) fidoPos -= add-1;
			if(fidoPos > trackSize-1) fidoPos = trackSize-1;
			if(fidoPos < 0) fidoPos = 0;
			
			showTrack();
			
			if( isOver() == true){
				showWinners();
			}
			
			
			//System.out.println("R: "+roverPos+" S: "+spotPos+" F: "+fidoPos);
			//System.out.println(add);
			
		}
	}
	
}
