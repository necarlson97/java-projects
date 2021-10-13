import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public abstract class ManedgerVisual {
	
	Random r = new Random();
	
	Font headerFont = new Font("press start 2p", 1, 26);
	Font bodyFont = new Font("press start 2p", 1, 8);
	
	public File file;
	
	public String name;
	boolean hidden;
	
	Color nameColor;
	
	public ManedgerVisual(File file){
		this.file = file;
		
		if(file == null) name = "exit";
		else if( file.equals(FileVisualizer.rootFile) ) name = "root";
		else name = file.getName();
		hidden = (name.startsWith("."));
		
		nameColor = new Color(name.hashCode()%576);
	}
	
	public ManedgerVisual(){
		this.file = null;
		name = file.getName();
		hidden = (name.startsWith("."));
	}

}
