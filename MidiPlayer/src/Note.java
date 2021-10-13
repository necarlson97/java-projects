import java.awt.Color;
import java.awt.Graphics;

public class Note {

    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    String name;
    int key;
    int octave;
    int velocity;
    int channel;
    
    int x;
    int y = 100;
    int width;
    int height;
    
    Color keyColor;
    Color channelColor;

    public Note(int key, int velocity, int channel) {
        this.key = key;
        this.octave = (key / 12)-1;
        int note = key % 12;
        this.name = NOTE_NAMES[note];
        this.velocity = velocity;
        this.channel = channel;
        
        keyColor = floatToHue((float) key/128);
        channelColor = floatToHue((float) channel/32);
        
        width = Game.windowWidth/128;
        x = key*width;
        height = width;
    }
    
    private Color floatToHue(float f) {
		return new Color(Color.HSBtoRGB(f, (float).7, (float).7));
	}

	public void render(Graphics g) {
		
		g.setColor(keyColor);
		g.fillRect(x, y-height, width, height);
		
		g.setColor(channelColor);
		g.fillRect(x, y-5, width, 5);
		
		y++;
		if(velocity > 0) height++;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Note && this.key == ((Note) obj).key;
    }

    @Override
    public String toString() {
        return "Note -> " + this.name + this.octave + " key=" + this.key + "Velocity= "+velocity;
    }
}